package com.egs.atmservice.model.service;

import com.egs.atmservice.enums.PinType;
import com.egs.atmservice.model.repository.BankRepository;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFactory {
    private final PinAuthenticationProvider pinAuthenticationProvider;
    private final FingerPrintAuthenticationProvider fingerPrintAuthenticationProvider;

    public AuthenticationFactory(PinAuthenticationProvider pinAuthenticationProvider, FingerPrintAuthenticationProvider fingerPrintAuthenticationProvider) {
        this.pinAuthenticationProvider = pinAuthenticationProvider;
        this.fingerPrintAuthenticationProvider = fingerPrintAuthenticationProvider;
    }

    public AuthenticationProvider getAuthenticationProvider(PinType pinType) {
        if (pinType.equals(PinType.PIN))
            return pinAuthenticationProvider;
        else
            return fingerPrintAuthenticationProvider;
    }
}
