package mk.finki.ukim.emt.ordermanagement.domain.models;

import lombok.Getter;
import lombok.NonNull;
import mk.finki.ukim.emt.ordermanagement.domain.valueobjects.RoomId;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "order_item")
@Getter
public class OrderItem extends AbstractEntity<OrderItemId> {
    private Money itemPrice;

    @Column(nullable = false)
    private int quantity;

    @AttributeOverride(name = "id", column = @Column(name = "room_id", nullable = false))
    private RoomId roomId;

    public OrderItem(@NonNull RoomId id, @NonNull Money itemPrice, @NonNull int quantity){
        super(DomainObjectId.randomId(OrderItemId.class));
        this.roomId = id;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
    }

    public Money subtotal(){
        return itemPrice.multiply(quantity);
    }
}
