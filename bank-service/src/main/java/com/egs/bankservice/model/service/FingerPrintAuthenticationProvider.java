package com.egs.bankservice.model.service;

import com.egs.bankservice.enums.PinType;
import com.egs.bankservice.exception.BadRequestException;
import com.egs.bankservice.model.dto.request.LoginReq;
import com.egs.bankservice.model.entity.EgsUser;
import com.egs.bankservice.util.Utils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.egs.bankservice.common.ErrorMessages.INVALID_PIN;

@Service
public class FingerPrintAuthenticationProvider implements AuthenticationProvider {
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public EgsUser register() {
        String fingerPrint = Utils.getRandomNumberStringFourChar();
        String card = Utils.getRandomNumberStringSixChar();
        return EgsUser.builder()
                .password(passwordEncoder.encode(fingerPrint))
                .username(card)
                .blocked(false)
                .fingerprint(fingerPrint)
                .pinType(PinType.FINGERPRINT)
                .created(new Date())
                .cardNumber(card)
                .tryCount(0)
                .cash(0l)
                .updated(new Date())
                .build();
    }

    @Override
    public boolean pinValidate(EgsUser user, LoginReq loginReq) {
        return loginReq.getPin().equals(user.getFingerprint())
                && passwordEncoder.matches(loginReq.getPin(), user.getPassword());
    }
}
