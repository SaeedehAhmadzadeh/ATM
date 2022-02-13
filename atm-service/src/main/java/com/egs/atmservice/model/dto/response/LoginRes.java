package com.egs.atmservice.model.dto.response;

import com.egs.atmservice.enums.PinType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class LoginRes {
    private TokenRes token;
    private String cardNumber;
    private String pin;
    private PinType pinType;
    private Date updated;
    private Date created;
}
