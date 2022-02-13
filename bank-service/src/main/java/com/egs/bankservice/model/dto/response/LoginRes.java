package com.egs.bankservice.model.dto.response;

import com.egs.bankservice.enums.PinType;
import com.egs.bankservice.model.entity.EgsUser;
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

    public LoginRes(EgsUser user) {
        this.cardNumber = user.getCardNumber();
        this.created = user.getCreated();
        this.pin = user.getPinType().equals(PinType.FINGERPRINT) ? user.getFingerprint() : user.getPin();
        this.updated = user.getUpdated();
        this.pinType = user.getPinType();
    }
}
