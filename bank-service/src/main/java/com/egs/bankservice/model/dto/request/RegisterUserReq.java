package com.egs.bankservice.model.dto.request;

import com.egs.bankservice.enums.PinType;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegisterUserReq {
    private PinType pinType;
}
