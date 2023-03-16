package entelect.training.incubator.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CaptureRewardsRequestDto {

    private String passportNumber;

    private BigDecimal amount;
}