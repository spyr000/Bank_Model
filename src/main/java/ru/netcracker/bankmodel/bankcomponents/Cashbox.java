package ru.netcracker.bankmodel.bankcomponents;

public class Cashbox {
    private static float moneyAmt = 1000;

    public static synchronized boolean withdraw(float moneyAmt) {
        if (moneyAmt < Cashbox.moneyAmt) {
            Cashbox.moneyAmt -= moneyAmt;
            return true;
        } else {
            return false;
        }
    }

    public static synchronized void deposit(float moneyAmt) {
        Cashbox.moneyAmt += moneyAmt;
    }

    public static synchronized float getBalance() {
        return moneyAmt;
    }
}
