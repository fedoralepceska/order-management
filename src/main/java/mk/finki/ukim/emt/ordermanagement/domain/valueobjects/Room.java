package mk.finki.ukim.emt.ordermanagement.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;

@Getter
public class Room implements ValueObject {
    private final RoomId roomId;
    private final String type;
    private final Money price;
    private final int sales;

    public Room(){
        this.roomId= RoomId.randomId(RoomId.class);
        this.type="";
        this.price=Money.valueOf(Currency.MKD, 0);
        this.sales=0;

    }

    @JsonCreator
    public Room(@JsonProperty("id") RoomId id,
                   @JsonProperty("type") String type,
                   @JsonProperty("price") Money price,
                   @JsonProperty("sales") int sales) {
        this.roomId = id;
        this.type = type;
        this.price = price;
        this.sales = sales;
    }

}
