package com.egs.bankservice.controller;


import com.egs.bankservice.model.dto.CashRes;
import com.egs.bankservice.model.dto.request.*;
import com.egs.bankservice.model.dto.response.CardRes;
import com.egs.bankservice.model.dto.response.LoginRes;
import com.egs.bankservice.model.dto.response.Response;
import com.egs.bankservice.model.dto.response.UserResDto;
import com.egs.bankservice.model.entity.EgsUser;
import com.egs.bankservice.model.service.UserService;
import com.egs.bankservice.model.service.security.Oauth2Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class UserController {
    private final UserService userService;
    private final Oauth2Service oauth2Service;

    public UserController(UserService userService, Oauth2Service oauth2Service) {
        this.userService = userService;
        this.oauth2Service = oauth2Service;
    }

    @PostMapping("/register")
    public ResponseEntity<Response<UserResDto>> registerUser(HttpServletRequest request,
                                                             @Valid @RequestBody RegisterUserReq userDto) {

        EgsUser user = userService.registerUser(userDto);
        UserResDto UserResDto = new UserResDto(user);
        Response<UserResDto> response =
                new Response<UserResDto>("OK", request.getRequestURI()).setMessage(UserResDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Response<LoginRes>> login(HttpServletRequest request,
                                                    @Valid @RequestBody LoginReq loginReq) {
        EgsUser user = userService.login(loginReq);
        LoginRes loginRes = new LoginRes(user);
        loginRes.setToken(oauth2Service.getToken(user, loginReq.getCardNumber(), loginReq.getPin()));
        Response<LoginRes> response = new Response<LoginRes>("OK", request.getRequestURI()).setMessage(loginRes);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/checkCardNumber")
    public ResponseEntity<Response<CardRes>> checkCardNumber(HttpServletRequest request,
                                                             @Valid @RequestBody CardReq cardReq) {
        CardRes cardRes = userService.checkCardNumber(cardReq);
        Response<CardRes> response = new Response<CardRes>("OK", request.getRequestURI()).setMessage(cardRes);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/checkBalance")
    public ResponseEntity<Response<CashRes>> checkBalance(HttpServletRequest request) {
        CashRes cardRes = userService.balance();
        Response<CashRes> response = new Response<com.egs.bankservice.model.dto.CashRes>("OK", request.getRequestURI()).setMessage(cardRes);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/cashWithdrawal")
    public ResponseEntity<Response<String>> cashWithdrawal(HttpServletRequest request,
                                                           @Valid @RequestBody CashWithdrawalReq cashWithdrawalReq) {
        userService.cashWithdrawal(cashWithdrawalReq);
        Response<String> response = new Response<String>("OK", request.getRequestURI()).setMessage("OK");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/cashDeposit")
    public ResponseEntity<Response<String>> cashDeposit(HttpServletRequest request,
                                                        @Valid @RequestBody CashDepositReq cashDepositReq) {
        userService.cashDeposit(cashDepositReq);
        Response<String> response = new Response<String>("OK", request.getRequestURI()).setMessage("OK");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
