package mk.finki.ukim.emt.ordermanagement.domain.models;

import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

public class OrderId extends DomainObjectId {

    private OrderId() {
        super(OrderId.randomId(OrderId.class).getId());
    }

    public OrderId(@NonNull String uuid) {
        super(uuid);
    }

    public static OrderId of(String uuid) {
        OrderId o = new OrderId(uuid);
        return o;
    }
}
