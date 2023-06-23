package io.whatusernameisleft.Customer;

import io.whatusernameisleft.Areas.Tickets.TicketSeller;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerGenerator extends Thread {
    private volatile boolean stopped = false;
    private AtomicInteger i = new AtomicInteger(1);
    private final List<TicketSeller> sellers;

    public CustomerGenerator(List<TicketSeller> sellers) {
        this.sellers = sellers;
    }

    @Override
    public void run() {
        while (!stopped) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Customer c = new Customer(i.get(), sellers);
            c.start();
            i.incrementAndGet();
        }
    }
}
