package com.egs.atmservice.model.service;

import com.egs.atmservice.model.dto.request.CashDepositReq;
import com.egs.atmservice.model.dto.request.CashWithdrawalReq;
import com.egs.atmservice.model.dto.response.LoginRes;
import com.egs.atmservice.model.service.state.GlobalData;
import com.egs.atmservice.exception.BadRequestException;
import com.egs.atmservice.model.dto.response.CardRes;
import com.egs.atmservice.model.entity.UserData;
import com.egs.atmservice.model.service.state.HasCard;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

import static com.egs.atmservice.common.ErrorMessages.ATM_DONT_HAVE_ENOUGH_MONEY;

@Service
public class ATMMachineService {
    public void insertCard(CardRes cardRes) {
        UserData userData = UserData.builder().cardNumber(cardRes.getCardNumber())
                .pinType(cardRes.getPinType()).build();
        GlobalData.setUserData(userData);
        GlobalData.getAtmMachine().insertCard();
    }

    public UserData getCard() {
        validateCard();
        return GlobalData.getUserData();
    }

    private void validateCard() {
        if (GlobalData.getUserData() == null || GlobalData.getUserData().getCardNumber() == null)
            throw new BadRequestException("Pleas insert the card!");
    }

    public void insertPin(LoginRes loginRes) {
        UserData userData = GlobalData.getUserData();
        userData.setPin(loginRes.getPin());
        userData.setToken(loginRes.getToken().getAccess_token());
        GlobalData.setUserData(userData);
        GlobalData.getAtmMachine().insertPin(loginRes.getPin());
    }

    public void requestCash(CashWithdrawalReq cashWithdrawalReq) {
        GlobalData.getAtmMachine().requestCash(cashWithdrawalReq.getCash());
    }

    public void ejected() {
        GlobalData.getAtmMachine().ejectCard();
    }

    public void cashDeposit(CashDepositReq cashDepositReq) {
        GlobalData.getAtmMachine().cashDeposit(cashDepositReq.getCash());
    }

    public void checkBalance() {
        GlobalData.getAtmMachine().checkBalance();
    }
    public void checkATMBalance(CashWithdrawalReq cashWithdrawalReq) {
        if(GlobalData.getAtmMachine().getCashInMachine()<cashWithdrawalReq.getCash())
            throw new BadRequestException(ATM_DONT_HAVE_ENOUGH_MONEY);
    }
}
