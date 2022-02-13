package com.egs.bankservice.model.dto.response;

import com.egs.bankservice.enums.PinType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CardRes {
    PinType pinType;
    String cardNumber;
}
