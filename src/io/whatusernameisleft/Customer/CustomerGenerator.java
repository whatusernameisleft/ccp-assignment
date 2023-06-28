package io.whatusernameisleft.Customer;

import io.whatusernameisleft.Areas.Tickets.SellerManager;
import io.whatusernameisleft.Areas.Waiting.Foyer.Foyer;
import io.whatusernameisleft.Areas.Waiting.WaitingArea.WaitingAreaManager;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerGenerator extends Thread {
    private volatile boolean stopped = false;
    private AtomicInteger i = new AtomicInteger(1);
    private final SellerManager sellerManager;
    private final WaitingAreaManager waitingAreaManager;
    private final Foyer foyer;

    public CustomerGenerator(SellerManager sellerManager, WaitingAreaManager waitingAreaManager, Foyer foyer) {
        this.sellerManager = sellerManager;
        this.waitingAreaManager = waitingAreaManager;
        this.foyer = foyer;
    }

    @Override
    public void run() {
        while (!stopped) {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(4) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Customer c = new Customer(i.get(), sellerManager, waitingAreaManager, foyer);
            c.start();
            i.incrementAndGet();
        }
    }
}
