package entelect.training.incubator.model.dto;

import entelect.training.incubator.model.Booking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookingRequestDto {
    private Integer customerId;
    private Integer flightID;

    public Booking toEntity()
    {
        return new Booking(null,customerId,flightID,"RandomReference");
    }
}
