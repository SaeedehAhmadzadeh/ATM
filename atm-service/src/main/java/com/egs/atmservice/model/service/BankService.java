package com.egs.atmservice.model.service;

import com.egs.atmservice.exception.BadRequestException;
import com.egs.atmservice.model.dto.CheckPinReqDto;
import com.egs.atmservice.model.dto.request.CashDepositReq;
import com.egs.atmservice.model.dto.request.CashWithdrawalReq;
import com.egs.atmservice.model.dto.request.LoginReq;
import com.egs.atmservice.model.dto.request.CardReq;
import com.egs.atmservice.model.dto.response.CardRes;
import com.egs.atmservice.model.dto.response.CashRes;
import com.egs.atmservice.model.dto.response.LoginRes;
import com.egs.atmservice.model.entity.UserData;
import com.egs.atmservice.model.repository.BankRepository;
import com.egs.atmservice.model.service.state.GlobalData;
import org.springframework.stereotype.Service;

import static com.egs.atmservice.common.ErrorMessages.BAD_REQUEST;
import static com.egs.atmservice.common.ErrorMessages.DONT_HAVE_ENOUGH_MONEY;

@Service
public class BankService {
    private final BankRepository bankRepository;
    private final AuthenticationFactory authenticationFactory;

    public BankService(AuthenticationFactory authenticationFactory, BankRepository bankRepository) {
        this.authenticationFactory = authenticationFactory;
        this.bankRepository = bankRepository;
    }

    public CardRes checkCardNumber(CardReq cardReq) {
        return bankRepository.checkCardNumber(cardReq);
    }

    public LoginRes authenticate(UserData userData, CheckPinReqDto checkPinReqDto) {
        return authenticationFactory.getAuthenticationProvider(userData.getPinType()).authenticate(new LoginReq(userData.getCardNumber(), checkPinReqDto.getPin()));
    }

    public CashRes checkBalance() {
        validate();
        return bankRepository.checkBalance(GlobalData.getUserData().getToken());
    }

    public void cashDeposit(CashDepositReq cashDepositReq) {
        validate();
        bankRepository.cashDeposit(GlobalData.getUserData().getToken(),cashDepositReq);
    }

    private void validate() {
        if (GlobalData.getUserData() == null || GlobalData.getUserData().getToken() ==null)
            throw new BadRequestException(BAD_REQUEST);
    }

    public void cashWithdrawal(CashWithdrawalReq cashWithdrawalReq) {
        validate();
        bankRepository.cashWithdrawal(GlobalData.getUserData().getToken(),cashWithdrawalReq);
    }

    public void cashValidate(CashWithdrawalReq cashWithdrawalReq) {
        CashRes cashRes = checkBalance();
        if(cashRes.getCash()< cashWithdrawalReq.getCash()) {
            throw new BadRequestException(DONT_HAVE_ENOUGH_MONEY);
        }
    }
}
