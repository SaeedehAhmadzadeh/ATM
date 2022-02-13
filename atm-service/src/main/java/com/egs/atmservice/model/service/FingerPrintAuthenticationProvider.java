package com.egs.atmservice.model.service;

import com.egs.atmservice.model.dto.request.LoginReq;
import com.egs.atmservice.model.dto.response.LoginRes;
import com.egs.atmservice.model.repository.BankRepository;
import org.springframework.stereotype.Service;

@Service
public class FingerPrintAuthenticationProvider implements AuthenticationProvider {
    private final BankRepository bankRepository;

    public FingerPrintAuthenticationProvider(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public LoginRes authenticate(LoginReq loginReq) {
        return bankRepository.login(loginReq);
    }
}
