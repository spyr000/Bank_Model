package ru.netcracker.bankmodel.bankcomponents;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Queue;

public class Teller implements Runnable {
    private final int id;
    Queue<Client> servedClients;

    public Teller(int id) {
        this.id = id;
        servedClients = new ArrayDeque<>();
    }

    private void serve(Client client) throws InterruptedException {
        if (client == null) return;
        Thread.sleep(client.getTimeForOperation().toMillis());
        if (client.isWithdrawalOpType()) {
            if (!Cashbox.withdraw(client.getMoneyAmt())) {
                System.out.println("\u001B[31m[ " + LocalDateTime.now() +
                        " ] Client " + client.getId()
                        + " was denied in withdrawal of "
                        + client.getMoneyAmt()
                        + "$ (no enough money in cashbox)\u001B[0m");
                return;
            }
        } else {
            Cashbox.deposit(client.getMoneyAmt());
        }
        System.out.println("[ " + LocalDateTime.now() + " ] Client's "
                + client.getId() + (client.isWithdrawalOpType() ? " withdrawal of " : " deposit of ")
                + client.getMoneyAmt()
                + "$ was served by teller №" + id + " in "
                + client.getTimeForOperation().toSeconds() + " seconds. Cashbox balance: "
                + Cashbox.getBalance()
                + "$"
        );
    }

    public synchronized void add(Client client) {
        servedClients.add(client);
        notify();
    }

    public Queue<Client> getServedClients() {
        return servedClients;
    }

    @Override
    public void run() {
        while (true) {
            if (!servedClients.isEmpty()) {
                try {
                    Client client = servedClients.poll();
                    if (client != null) serve(client);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
