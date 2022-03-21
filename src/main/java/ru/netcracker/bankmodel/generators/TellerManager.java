package ru.netcracker.bankmodel.generators;

import ru.netcracker.bankmodel.bankcomponents.Bank;
import ru.netcracker.bankmodel.bankcomponents.Client;
import ru.netcracker.bankmodel.bankcomponents.Teller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TellerManager implements Runnable {
    public static final int TELLERS_AMT = 4;
    private final List<Teller> tellers = new ArrayList<>();
    private final List<Client> availableClients = new ArrayList<>();
    Bank bank;

    public TellerManager(Bank bank) {
        this.bank = bank;

        for (int i = 0; i < TELLERS_AMT; i++) {
            tellers.add(new Teller());
        }
    }

    @Override
    public void run() {
        while (true) {
            Client client = bank.getClients().poll();
            if (client != null) {
                tellers.stream()
                        .min(Comparator.comparingInt(t -> t.getServedClients().size()))
                        .ifPresent(
                                teller -> teller
                                        .getServedClients()
                                        .add(client)
                        );
            }

        }
    }
}
