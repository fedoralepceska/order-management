package mk.finki.ukim.emt.ordermanagement.services.impl;

import lombok.AllArgsConstructor;
import mk.finki.ukim.emt.ordermanagement.domain.exceptions.OrderDoesntExistException;
import mk.finki.ukim.emt.ordermanagement.domain.exceptions.OrderItemIdDoesntExistException;
import mk.finki.ukim.emt.ordermanagement.domain.models.Order;
import mk.finki.ukim.emt.ordermanagement.domain.models.OrderId;
import mk.finki.ukim.emt.ordermanagement.domain.models.OrderItemId;
import mk.finki.ukim.emt.ordermanagement.domain.repository.OrderRepository;
import mk.finki.ukim.emt.ordermanagement.services.OrderService;
import mk.finki.ukim.emt.ordermanagement.services.forms.OrderForm;
import mk.finki.ukim.emt.ordermanagement.services.forms.OrderItemForm;
import mk.ukim.finki.emt.sharedkernel.event.order.OrderItemCreated;
import mk.ukim.finki.emt.sharedkernel.infrastructure.DomainEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final Validator validator;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public OrderId placeOrder(OrderForm form) {
        Objects.requireNonNull(form,"order must not be null.");
        var constraintViolations = validator.validate(form);
        if (constraintViolations.size()>0) {
            throw new ConstraintViolationException("The order form is not valid", constraintViolations);
        }
        var newOrder = orderRepository.saveAndFlush(toDomainObject(form));
        newOrder.getOrderItemList().forEach(item -> domainEventPublisher.publish(new OrderItemCreated(item.getRoomId().getId(), item.getQuantity())));
        return newOrder.getId();
    }

    private Order toDomainObject(OrderForm orderForm) {
        var order = new Order(Instant.now(), orderForm.getCurrency());
        orderForm.getItems().forEach(item -> order.addItem(item.getRoom(), item.getQuantity()));
        return order;
    }

    @Override
    public List<Order> findAll() {
        return this.orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        return this.orderRepository.findById(id);
    }

    @Override
    public void addItem(OrderId orderId, OrderItemForm orderItemForm) throws OrderDoesntExistException {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderDoesntExistException::new);
        order.addItem(orderItemForm.getRoom(), orderItemForm.getQuantity());
        orderRepository.saveAndFlush(order);
        domainEventPublisher.publish(new OrderItemCreated(orderItemForm.getRoom().getRoomId().getId(), orderItemForm.getQuantity()));
    }

    @Override
    public void deleteItem(OrderId orderId, OrderItemId orderItemId) throws OrderDoesntExistException, OrderItemIdDoesntExistException {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderDoesntExistException::new);
        order.removeItem(orderItemId);
        orderRepository.saveAndFlush(order);
    }
}
