package ru.netcracker.bankmodel.generators;

import ru.netcracker.bankmodel.bankcomponents.Client;
import ru.netcracker.bankmodel.listeners.OnClientArrivedListener;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

public class ClientGenerator implements Runnable {
    public static final int CLIENTS_PER_MINUTE_CNT = 40;
    public static final int MAX_SERVE_TIME = 15;
    public static final int MIN_SPAWN_DELAY = 1000;
    public static final int MONEY_AMOUNT_INTERVAL = 900;
    private static final int MIN_SERVE_TIME = 1;
    private static final Random random = new Random();
    private OnClientArrivedListener onClientArrivedListener;


    public static Client newInstance() {
        float moneyAmt = (float) random.nextGaussian(0, MONEY_AMOUNT_INTERVAL);
        return new Client(
                moneyAmt < 0,
                Math.abs(moneyAmt),
                Duration.ofMillis((long) (random.nextFloat(MIN_SERVE_TIME, MAX_SERVE_TIME) * 1000))
        );
    }

    @Override
    public void run() {
        while (true) {
            //generating random delay for clients to appear
            int totalDelayMillis = 60000;
            for (int i = 0; i < CLIENTS_PER_MINUTE_CNT; i++) {
                try {
                    int actualDelay;
                    if (2 * totalDelayMillis / (CLIENTS_PER_MINUTE_CNT - 1) > MIN_SPAWN_DELAY) {
                        actualDelay = random.nextInt(MIN_SPAWN_DELAY, 2 * totalDelayMillis / (CLIENTS_PER_MINUTE_CNT - 1));
                    } else {
                        actualDelay = totalDelayMillis;
                    }
                    if (i < CLIENTS_PER_MINUTE_CNT - 1) {
                        Thread.sleep(actualDelay);
                        totalDelayMillis -= actualDelay;
                    } else {
                        Thread.sleep(totalDelayMillis);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Client client = newInstance();
                onClientArrivedListener.OnClientArrived(client);
            }
            System.out.println("[ " + LocalDateTime.now() + " ] Minute passed");
        }
    }

    public void setOnClientArrivedListener(OnClientArrivedListener onClientArrivedListener) {
        this.onClientArrivedListener = onClientArrivedListener;
    }
}
