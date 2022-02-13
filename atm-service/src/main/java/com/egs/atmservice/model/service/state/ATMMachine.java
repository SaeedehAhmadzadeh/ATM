package com.egs.atmservice.model.service.state;

public class ATMMachine {
    ATMState hasCard;
    ATMState noCard;
    ATMState hasCorrectPin;
    ATMState atmOutOfMoney;
    ATMState atmState;

    public Long getCashInMachine() {
        return cashInMachine;
    }

    Long cashInMachine;
    boolean correctPinEntered = false;

    public ATMMachine(Long cashInMachine) {

        hasCard = new HasCard(this);
        noCard = new NoCard(this);
        hasCorrectPin = new HasPin(this);
        atmOutOfMoney = new NoCash(this);
        atmState = noCard;
        this.cashInMachine = cashInMachine;
        if (this.cashInMachine <= 0) {
            atmState = atmOutOfMoney;
        }
    }

    void setATMState(ATMState newATMState) {
        atmState = newATMState;
    }

    public void setCashInMachine(Long newCashInMachine) {
        cashInMachine = newCashInMachine;
    }

    public void insertCard() {
        atmState.insertCard();
    }

    public void ejectCard() {
        GlobalData.setUserData(null);
        atmState.ejectCard();
    }

    public void requestCash(Long cashToWithdraw) {
        atmState.requestCash(cashToWithdraw);
    }

    public void cashDeposit(Long cash) {
        atmState.cashDeposit(cash);
    }
    public void checkBalance() {
        atmState.checkBalance();
    }
    public void insertPin(String pinEntered) {
        atmState.insertPin(pinEntered);
    }

    public ATMState getYesCardState() {
        return hasCard;
    }

    public ATMState getNoCardState() {
        return noCard;
    }

    public ATMState getHasPin() {
        return hasCorrectPin;
    }

    public ATMState getNoCashState() {
        return atmOutOfMoney;
    }

    public ATMState getAtmState() {
        return atmState;
    }
}
