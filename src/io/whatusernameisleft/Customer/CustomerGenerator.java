package io.whatusernameisleft.Customer;

import io.whatusernameisleft.Areas.Tickets.SellerManager;
import io.whatusernameisleft.Areas.Tickets.TicketSeller;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerGenerator extends Thread {
    private volatile boolean stopped = false;
    private AtomicInteger i = new AtomicInteger(1);
    private final SellerManager sellerManager;

    public CustomerGenerator(SellerManager sellerManager) {
        this.sellerManager = sellerManager;
    }

    @Override
    public void run() {
        while (!stopped) {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(4) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Customer c = new Customer(i.get(), sellerManager);
            c.start();
            i.incrementAndGet();
        }
    }
}
