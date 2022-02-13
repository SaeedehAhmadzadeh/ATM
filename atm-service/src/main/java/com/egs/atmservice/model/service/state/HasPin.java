package com.egs.atmservice.model.service.state;

import com.egs.atmservice.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.egs.atmservice.common.ErrorMessages.*;

public class HasPin implements ATMState {
    ATMMachine atmMachine;
    private final Logger logger = LoggerFactory.getLogger("hasPin");

    public HasPin(ATMMachine newATMMachine) {
        atmMachine = newATMMachine;
    }

    @Override
    public void insertCard() {
        throw new BadRequestException(ALREADY_ENTERED_CARD);
    }

    @Override
    public void ejectCard() {
        ejectedCardProcess();
        logger.info(REJECTED_CARD);
    }

    @Override
    public void requestCash(Long cashToWithdraw) {
        if (cashToWithdraw > atmMachine.cashInMachine) {
            GlobalData.setUserData(null);
            atmMachine.setATMState(atmMachine.getNoCardState());
            throw new BadRequestException(ATM_DONT_HAVE_ENOUGH_MONEY);
        } else {
            logger.info(cashToWithdraw + " is provided by the machine");
            atmMachine.setCashInMachine(atmMachine.cashInMachine - cashToWithdraw);
            logger.info(REJECTED_CARD);
            ejectedCardProcess();
            if (atmMachine.cashInMachine <= 0) {
                atmMachine.setATMState(atmMachine.getNoCashState());
            }
        }
    }

    private void ejectedCardProcess() {
        atmMachine.setATMState(atmMachine.getNoCardState());
        GlobalData.setUserData(null);
    }

    @Override
    public void cashDeposit(Long depositCash) {
        atmMachine.setCashInMachine(atmMachine.cashInMachine + depositCash);
    }

    @Override
    public void checkBalance() {
        logger.info(CHECK_BALANCE);
    }

    @Override
    public void insertPin(String pinEntered) {
        throw new BadRequestException(ALREADY_ENTERED_PIN);
    }
}