package com.egs.atmservice.model.entity;

import com.egs.atmservice.enums.PinType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserData {
    PinType pinType;
    String cardNumber;
    String pin;
    String token;
}
