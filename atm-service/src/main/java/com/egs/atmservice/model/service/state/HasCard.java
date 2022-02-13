package com.egs.atmservice.model.service.state;

import com.egs.atmservice.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.egs.atmservice.common.ErrorMessages.*;

public class HasCard implements ATMState {
    ATMMachine atmMachine;
    private final Logger logger = LoggerFactory.getLogger("hasCard");

    public HasCard(ATMMachine newATMMachine) {
        atmMachine = newATMMachine;
    }

    @Override
    public void insertCard() {
        throw new BadRequestException(INSERT_ON_CARD);
    }

    @Override
    public void ejectCard() {
        atmMachine.setATMState(atmMachine.getNoCardState());
        GlobalData.setUserData(null);
        throw new BadRequestException(REJECTED_CARD);
    }

    @Override
    public void requestCash(Long cashToWithdraw) {
        throw new BadRequestException(DONT_HAVE_PIN);
    }

    @Override
    public void cashDeposit(Long depositCash) {
        throw new BadRequestException(DONT_HAVE_PIN);
    }

    @Override
    public void checkBalance() {
        throw new BadRequestException(DONT_HAVE_PIN);
    }

    @Override
    public void insertPin(String pinEntered) {
        if (pinEntered.equals(GlobalData.getUserData().getPin())) {
            logger.info(CORRECT_PIN);
            atmMachine.correctPinEntered = true;
            atmMachine.setATMState(atmMachine.getHasPin());

        } else {
            atmMachine.correctPinEntered = false;
            atmMachine.setATMState(atmMachine.getNoCardState());
            GlobalData.setUserData(null);
            throw new BadRequestException(WRONG_PIN);
        }
    }
}