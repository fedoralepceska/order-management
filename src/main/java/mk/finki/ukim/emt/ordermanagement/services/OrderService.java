package mk.finki.ukim.emt.ordermanagement.services;

import mk.finki.ukim.emt.ordermanagement.domain.exceptions.OrderDoesntExistException;
import mk.finki.ukim.emt.ordermanagement.domain.exceptions.OrderItemIdDoesntExistException;
import mk.finki.ukim.emt.ordermanagement.domain.models.Order;
import mk.finki.ukim.emt.ordermanagement.domain.models.OrderId;
import mk.finki.ukim.emt.ordermanagement.domain.models.OrderItemId;
import mk.finki.ukim.emt.ordermanagement.services.forms.OrderForm;
import mk.finki.ukim.emt.ordermanagement.services.forms.OrderItemForm;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    OrderId placeOrder(OrderForm form);
    List<Order> findAll();
    Optional<Order> findById(OrderId id);
    void addItem(OrderId orderId, OrderItemForm orderItemForm) throws OrderDoesntExistException;
    void deleteItem(OrderId orderId, OrderItemId orderItemId) throws OrderDoesntExistException, OrderItemIdDoesntExistException;
}
