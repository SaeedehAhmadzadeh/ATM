package com.egs.atmservice.model.service.state;

public interface ATMState {
	void insertCard();
	void ejectCard();
	void insertPin(String pinEntered);
	void requestCash(Long cashToWithdraw);
	void cashDeposit(Long depositCash);
	void checkBalance();
}