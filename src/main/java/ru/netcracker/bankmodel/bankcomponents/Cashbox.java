package ru.netcracker.bankmodel.bankcomponents;

public class Cashbox {
    public static boolean withdraw(float moneyAmt) {
        if (moneyAmt < Cashbox.moneyAmt) {
            Cashbox.moneyAmt -= moneyAmt;
            return true;
        } else {
            return false;
        }
    }

    public static void deposit(float moneyAmt) {
        Cashbox.moneyAmt += moneyAmt;
    }

    public static float getBalance() {
        return moneyAmt;
    }

    private static float moneyAmt;
}
