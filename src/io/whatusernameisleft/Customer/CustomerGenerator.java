package io.whatusernameisleft.Customer;

import io.whatusernameisleft.Areas.Tickets.SellerManager;
import io.whatusernameisleft.Areas.Waiting.Foyer.FoyerManager;
import io.whatusernameisleft.Areas.Waiting.WaitingArea.WaitingAreaManager;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerGenerator extends Thread {
    private volatile boolean stopped = false;
    private AtomicInteger id = new AtomicInteger(1);
    private final AtomicInteger customerCounter;
    private final int BUILDING_MAX = 45;
    private final int THRESHOLD = (int) (45 * 0.8);
    private final SellerManager sellerManager;
    private final WaitingAreaManager waitingAreaManager;
    private final FoyerManager foyerManager;

    public CustomerGenerator(AtomicInteger customerCounter, SellerManager sellerManager, WaitingAreaManager waitingAreaManager, FoyerManager foyerManager) {
        this.customerCounter = customerCounter;
        this.sellerManager = sellerManager;
        this.waitingAreaManager = waitingAreaManager;
        this.foyerManager = foyerManager;
    }

    private boolean belowThreshold() {
        return customerCounter.get() < THRESHOLD;
    }

    private void pause() {
        stopped = true;
    }

    private void unpause() {
        stopped = false;
    }

    @Override
    public void run() {
        while (true) {
            if (!stopped) {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(4) * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Customer c = new Customer(id.get(), sellerManager, waitingAreaManager, foyerManager);
                c.start();
                id.incrementAndGet();
                if (customerCounter.incrementAndGet() == BUILDING_MAX) pause();
            }
            if (belowThreshold()) unpause();
        }
    }
}
