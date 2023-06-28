package io.whatusernameisleft.Customer;

import io.whatusernameisleft.Areas.Tickets.SellerManager;
import io.whatusernameisleft.Areas.WaitingArea.WaitingAreaManager;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerGenerator extends Thread {
    private volatile boolean stopped = false;
    private AtomicInteger i = new AtomicInteger(1);
    private final SellerManager sellerManager;
    private final WaitingAreaManager waitingAreaManager;

    public CustomerGenerator(SellerManager sellerManager, WaitingAreaManager waitingAreaManager) {
        this.sellerManager = sellerManager;
        this.waitingAreaManager = waitingAreaManager;
    }

    @Override
    public void run() {
        while (!stopped) {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(4) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Customer c = new Customer(i.get(), sellerManager, waitingAreaManager);
            c.start();
            i.incrementAndGet();
        }
    }
}
