package ru.netcracker.bankmodel.bankcomponents;

import ru.netcracker.bankmodel.generators.ClientGenerator;

import java.time.LocalDateTime;
import java.util.*;

public class Bank {
    public static final int TELLERS_AMT = 4;
    private final List<Teller> tellers = new ArrayList<>();

    public Bank() {
        System.out.println("[ " + LocalDateTime.now() + " ] Bank opened!!! Waiting for customers...");
        //adding tellers to bank
        for (int i = 0; i < TELLERS_AMT; i++) {
            tellers.add(new Teller(i + 1));
        }
        //creating threads for tellers
        for (Teller teller : tellers) {
            Thread thread = new Thread(teller);
            thread.start();
        }

        ClientGenerator clientGenerator = new ClientGenerator();
        clientGenerator.setOnClientArrivedListener(client -> {
            System.out.println((client.isWithdrawalOpType() ? "\033[38;5;208m" : "\u001B[32m")
                    + "[ " + LocalDateTime.now()
                    + " ] Client " + client.getId() + " is arrived to "
                    + (client.isWithdrawalOpType() ? "withdraw " : "deposit ")
                    + client.getMoneyAmt() + "$\u001B[0m");
            //choose the least loaded teller and notifying his thread
            tellers.stream()
                    .min(Comparator.comparingInt(t -> t.getServedClients().size()))
                    .ifPresent(
                            teller -> {
                                teller.add(client);
                            }
                    );
        });
        Thread t = new Thread(clientGenerator);
        t.start();
    }

    public static void main(String[] args) {
        Bank b = new Bank();
    }
}
