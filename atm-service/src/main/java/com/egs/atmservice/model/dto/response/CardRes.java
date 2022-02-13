package com.egs.atmservice.model.dto.response;

import com.egs.atmservice.enums.PinType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardRes {
    PinType pinType;
    String cardNumber;
}
