package ru.netcracker.bankmodel.bankcomponents;

import java.util.ArrayDeque;
import java.util.Queue;

public class Teller implements Runnable {
    Queue<Client> servedClients;

    public Teller() {
        servedClients = new ArrayDeque<>();
    }

    private void serve(Client client) throws InterruptedException {
        if (client == null) return;
        if (client.isWithdrawalOpType()) {
            if (!Cashbox.withdraw(client.getMoneyAmt())) {
                System.out.println("Client " + client.getId() + " was denied service (no enough money in cashbox)");
                return;
            }
        } else {
            Cashbox.deposit(client.getMoneyAmt());
        }
        Thread.sleep(client.getTimeForOperation().toMillis());
    }

    public Queue<Client> getServedClients() {
        return servedClients;
    }

    @Override
    public void run() {
        if (!servedClients.isEmpty()) {
            try {
                Client client = servedClients.poll();
                if (client != null) serve(client);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
