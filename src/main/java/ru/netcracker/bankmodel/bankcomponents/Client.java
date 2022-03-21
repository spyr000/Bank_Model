package ru.netcracker.bankmodel.bankcomponents;

import java.time.Duration;
import java.util.UUID;

public class Client implements Runnable {
    private final boolean isWithdrawalOpType;
    private final float moneyAmt;
    private final Duration timeForOperation;
    private final UUID id = UUID.randomUUID();

    public Client(boolean wantsToWithdraw, float moneyAmt, Duration timeForOperation) {
        this.isWithdrawalOpType = wantsToWithdraw;
        this.moneyAmt = moneyAmt;
        this.timeForOperation = timeForOperation;
    }

    public boolean isWithdrawalOpType() {
        return isWithdrawalOpType;
    }

    public float getMoneyAmt() {
        return moneyAmt;
    }

    public Duration getTimeForOperation() {
        return timeForOperation;
    }

    @Override
    public void run() {

    }

    public UUID getId() {
        return id;
    }
}
