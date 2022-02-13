package com.egs.atmservice.model.service.state;

import com.egs.atmservice.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.egs.atmservice.common.ErrorMessages.ATM_DONT_HAVE_MONEY;
import static com.egs.atmservice.common.ErrorMessages.CHECK_BALANCE;

public class NoCash implements ATMState {
	ATMMachine atmMachine;
	private final Logger logger = LoggerFactory.getLogger("noCash");
	public NoCash(ATMMachine newATMMachine){
		atmMachine = newATMMachine;
	}

	@Override
	public void insertCard() {
		ejectedCardProcess();
		throw new BadRequestException(ATM_DONT_HAVE_MONEY);
	}

	private void ejectedCardProcess() {
		atmMachine.setATMState(atmMachine.getNoCardState());
		GlobalData.setUserData(null);
	}

	@Override
	public void ejectCard() {
		ejectedCardProcess();
		throw new BadRequestException(ATM_DONT_HAVE_MONEY);
	}

	@Override
	public void requestCash(Long cashToWithdraw) {
		ejectedCardProcess();
		throw new BadRequestException(ATM_DONT_HAVE_MONEY);
	}

	@Override
	public void cashDeposit(Long depositCash) {
		atmMachine.setCashInMachine(atmMachine.cashInMachine + depositCash);
	}

	@Override
	public void checkBalance() {
		logger.info(CHECK_BALANCE);
	}

	public void insertPin(String pinEntered) {
		ejectedCardProcess();
		throw new BadRequestException(ATM_DONT_HAVE_MONEY);
	}	
}