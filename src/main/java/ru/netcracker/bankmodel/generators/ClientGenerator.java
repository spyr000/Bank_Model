package ru.netcracker.bankmodel.generators;

import ru.netcracker.bankmodel.bankcomponents.Bank;
import ru.netcracker.bankmodel.bankcomponents.Client;
import ru.netcracker.bankmodel.listeners.OnClientArrivedListener;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClientGenerator implements Runnable {
    public static final int MAX_SERVE_TIME = 6;
    public static final int MIN_SPAWN_DELAY = 1000;
    public static final int MAX_SPAWN_DELAY = 5000;
    public static final int MONEY_AMOUNT_INTERVAL = 900;
    public static final int MIN_CLIENTS_CNT = 6;
    public static final int CLIENTS_GROUP_CNT = 3;
    private static final int MIN_SERVE_TIME = 1;
    private static final Random random = new Random();
    private Bank bank;
    private List<Client> randClientsList;
    private int clientsAmt;
    private OnClientArrivedListener onClientArrivedListener;

    public ClientGenerator(Bank bank) {
        this.bank = bank;
        randClientsList = new ArrayList<>();
    }

    public static Client newInstance() {
        float moneyAmt = (float) random.nextGaussian(0, MONEY_AMOUNT_INTERVAL);
        return new Client(
                moneyAmt < 0,
                moneyAmt,
                Duration.ofMillis((long) (random.nextFloat(MIN_SERVE_TIME, MAX_SERVE_TIME) * 1000))
        );
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.println(Duration.ofMillis((long) (random.nextFloat(MIN_SERVE_TIME, MAX_SERVE_TIME) * 1000)));
        }
    }

    @Override
    public void run() {
        while (true) {
            if (bank.getClients().size() < MIN_CLIENTS_CNT) {
                for (int i = 0; i < CLIENTS_GROUP_CNT; i++) {
                    try {
                        Thread.sleep(random.nextInt(MIN_SPAWN_DELAY, MAX_SPAWN_DELAY));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Client client = newInstance();
                    bank.getClients().add(client);
                    onClientArrivedListener.OnClientArrived(client);
                }
            }
        }
    }

    public void setOnClientArrivedListener(OnClientArrivedListener onClientArrivedListener) {
        this.onClientArrivedListener = onClientArrivedListener;
    }
}
