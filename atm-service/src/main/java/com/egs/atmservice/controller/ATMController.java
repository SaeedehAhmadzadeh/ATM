package com.egs.atmservice.controller;


import com.egs.atmservice.model.dto.CheckPinReqDto;
import com.egs.atmservice.model.dto.request.CardReq;
import com.egs.atmservice.model.dto.request.CashDepositReq;
import com.egs.atmservice.model.dto.request.CashWithdrawalReq;
import com.egs.atmservice.model.dto.response.CashRes;
import com.egs.atmservice.model.dto.response.LoginRes;
import com.egs.atmservice.model.dto.response.Response;
import com.egs.atmservice.model.dto.response.CardRes;
import com.egs.atmservice.model.entity.UserData;
import com.egs.atmservice.model.service.ATMMachineService;
import com.egs.atmservice.model.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class ATMController {
    private final BankService bankService;
    private final ATMMachineService atmMachineService;

    public ATMController(BankService bankService, ATMMachineService atmMachineService) {
        this.bankService = bankService;
        this.atmMachineService = atmMachineService;
    }

    @PostMapping("/insertCard")
    public ResponseEntity<Response<CardRes>> insertCard(HttpServletRequest request,
                                                        @Valid @RequestBody CardReq cardReq) {
        CardRes cardRes = bankService.checkCardNumber(cardReq);
        atmMachineService.insertCard(cardRes);
        Response<CardRes> response =
                new Response<CardRes>("OK", request.getRequestURI()).setMessage(cardRes);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/insertPin")
    public ResponseEntity<Response<String>> insertPin(HttpServletRequest request,
                                                      @Valid @RequestBody CheckPinReqDto insertPinReqDto) {
        UserData userData = atmMachineService.getCard();
        LoginRes loginRes = bankService.authenticate(userData, insertPinReqDto);
        atmMachineService.insertPin(loginRes);
        Response<String> response =
                new Response<String>("OK", request.getRequestURI()).setMessage("OK");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/checkBalance")
    public ResponseEntity<Response<CashRes>> checkBalance(HttpServletRequest request) {
        atmMachineService.checkBalance();
        CashRes cardRes = bankService.checkBalance();
        Response<CashRes> response = new Response<CashRes>("OK", request.getRequestURI()).setMessage(cardRes);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/cashWithdrawal")
    public ResponseEntity<Response<String>> cashWithdrawal(HttpServletRequest request,

                                                           @Valid @RequestBody CashWithdrawalReq cashWithdrawalReq) {
        atmMachineService.checkBalance();
        atmMachineService.checkATMBalance(cashWithdrawalReq);
        bankService.cashWithdrawal(cashWithdrawalReq);
        atmMachineService.requestCash(cashWithdrawalReq);
        Response<String> response = new Response<String>("OK", request.getRequestURI()).setMessage("OK");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/cashDeposit")
    public ResponseEntity<Response<String>> cashDeposit(HttpServletRequest request,
                                                        @Valid @RequestBody CashDepositReq cashDepositReq) {
        atmMachineService.cashDeposit(cashDepositReq);
        bankService.cashDeposit(cashDepositReq);
        Response<String> response = new Response<String>("OK", request.getRequestURI()).setMessage("OK");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/ejected")
    public ResponseEntity<Response<String>> ejected(HttpServletRequest request) {
        atmMachineService.ejected();
        Response<String> response = new Response<String>("OK", request.getRequestURI()).setMessage("OK");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
