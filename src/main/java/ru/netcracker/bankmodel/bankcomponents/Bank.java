package ru.netcracker.bankmodel.bankcomponents;

import ru.netcracker.bankmodel.generators.ClientGenerator;
import ru.netcracker.bankmodel.listeners.OnClientArrivedListener;

import java.util.*;

public class Bank {
    public static final int TELLERS_AMT = 4;
//    private final Queue<Client> clients = new ArrayDeque<>();
    private final List<Teller> tellers = new ArrayList<>();

//    public Queue<Client> getClients() {
//        return clients;
//    }

    public Bank() {
        for (int i = 0; i < TELLERS_AMT; i++) {
            tellers.add(new Teller());
        }
        ClientGenerator clientGenerator = new ClientGenerator(this);
        clientGenerator.setOnClientArrivedListener(client -> {
            System.out.println("Client " + client.getId() + " is arrived for "
                    + (client.isWithdrawalOpType() ? "withdraw " : "deposit ") + "money");
            tellers.stream()
                    .min(Comparator.comparingInt(t -> t.getServedClients().size()))
                    .ifPresent(
                            teller -> teller
                                    .getServedClients()
                                    .add(client)
                    );
        });
        Thread t = new Thread(clientGenerator);
        t.start();
    }

    public static void main(String[] args) {
    }
}
