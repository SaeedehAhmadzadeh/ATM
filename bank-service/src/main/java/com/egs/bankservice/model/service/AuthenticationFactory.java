package com.egs.bankservice.model.service;

import com.egs.bankservice.enums.PinType;
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
