package com.egs.atmservice.model.repository;

import com.egs.atmservice.model.dto.request.CashDepositReq;
import com.egs.atmservice.model.dto.request.CashWithdrawalReq;
import com.egs.atmservice.model.dto.request.LoginReq;
import com.egs.atmservice.model.dto.request.CardReq;
import com.egs.atmservice.model.dto.response.CardRes;
import com.egs.atmservice.model.dto.response.CashRes;
import com.egs.atmservice.model.dto.response.LoginRes;
import com.egs.atmservice.model.dto.response.Response;
import com.egs.atmservice.utils.RestTemplateUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class BankRepository {
    private final RestTemplateUtil restTemplateUtil;
    private final Environment environment;

    public BankRepository(RestTemplateUtil restTemplateUtil, Environment environment) {
        this.restTemplateUtil = restTemplateUtil;
        this.environment = environment;
    }

    public CardRes checkCardNumber(CardReq cardReq) {

        ResponseEntity<Response<CardRes>> response = restTemplateUtil.callService(environment.getProperty("bankServerUrl") + "/checkCardNumber",
                null,
                cardReq,
                HttpMethod.POST,
                new ParameterizedTypeReference<Response<CardRes>>() {
                });

        return Objects.requireNonNull(response.getBody()).getMessage();

    }

    public LoginRes login(LoginReq loginReq) {
        ResponseEntity<Response<LoginRes>> response = restTemplateUtil.callService(environment.getProperty("bankServerUrl") + "/login",
                null,
                loginReq,
                HttpMethod.POST,
                new ParameterizedTypeReference<Response<LoginRes>>() {
                });

        return Objects.requireNonNull(response.getBody()).getMessage();
    }

    public CashRes checkBalance(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        ResponseEntity<Response<CashRes>> response = restTemplateUtil.callService(environment.getProperty("bankServerUrl") + "/checkBalance",
                headers,
                null,
                HttpMethod.GET,
                new ParameterizedTypeReference<Response<CashRes>>() {
                });

        return Objects.requireNonNull(response.getBody()).getMessage();

    }

    public void cashDeposit(String token, CashDepositReq cashDepositReq) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        restTemplateUtil.callService(environment.getProperty("bankServerUrl") + "/cashDeposit",
                headers,
                cashDepositReq,
                HttpMethod.POST,
                new ParameterizedTypeReference<Response<String>>() {
                });
    }

    public void cashWithdrawal(String token,CashWithdrawalReq cashWithdrawalReq) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        restTemplateUtil.callService(environment.getProperty("bankServerUrl") + "/cashWithdrawal",
                headers,
                cashWithdrawalReq,
                HttpMethod.POST,
                new ParameterizedTypeReference<Response<String>>() {
                });
    }
}
