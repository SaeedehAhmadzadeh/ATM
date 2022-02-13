package com.egs.atmservice.model.service.state;

import com.egs.atmservice.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.egs.atmservice.common.ErrorMessages.*;

public class NoCard implements ATMState {
	ATMMachine atmMachine;
	private final Logger logger = LoggerFactory.getLogger("noCard");

	public NoCard(ATMMachine newATMMachine){
		atmMachine = newATMMachine;
	}

	@Override
	public void insertCard() {
		logger.info(ENTERED_PIN);
		atmMachine.setATMState(atmMachine.getYesCardState());
	}

	@Override
	public void ejectCard() {
		throw new BadRequestException(DONT_ENTERED_CARD);
	}

	@Override
	public void requestCash(Long cashToWithdraw) {
		throw new BadRequestException(DONT_ENTERED_CARD);
	}

	@Override
	public void cashDeposit(Long depositCash) {
		throw new BadRequestException(DONT_ENTERED_CARD);
	}

	@Override
	public void checkBalance() {
		throw new BadRequestException(DONT_ENTERED_CARD);
	}

	@Override
	public void insertPin(String pinEntered) {
		throw new BadRequestException(DONT_ENTERED_CARD);
	}
}