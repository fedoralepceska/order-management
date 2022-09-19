package mk.finki.ukim.emt.ordermanagement.services.forms;

import lombok.Data;
import mk.finki.ukim.emt.ordermanagement.domain.valueobjects.Room;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class OrderItemForm {
    @NotNull
    private Room room;

    @Min(1)
    private int quantity = 1;
}
