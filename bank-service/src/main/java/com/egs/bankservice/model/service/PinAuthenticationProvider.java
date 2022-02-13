package com.egs.bankservice.model.service;


import com.egs.bankservice.enums.PinType;
import com.egs.bankservice.exception.BadRequestException;
import com.egs.bankservice.model.dto.request.LoginReq;
import com.egs.bankservice.model.entity.EgsUser;
import com.egs.bankservice.util.Utils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

import static com.egs.bankservice.common.ErrorMessages.INVALID_PIN;

@Service
public class PinAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public EgsUser register() {
        String pin = Utils.getRandomNumberStringFourChar();
        String card = Utils.getRandomNumberStringSixChar();
        return EgsUser.builder()
                .password(passwordEncoder.encode(pin))
                .username(card)
                .blocked(false)
                .pin(pin)
                .pinType(PinType.PIN)
                .cardNumber(card)
                .created(new Date())
                .updated(new Date())
                .tryCount(0)
                .cash(0l)
                .build();

    }

    @Override
    public boolean pinValidate(EgsUser user, LoginReq loginReq) {
        return loginReq.getPin().equals(user.getPin())
                && passwordEncoder.matches(loginReq.getPin(), user.getPassword());
    }
}
