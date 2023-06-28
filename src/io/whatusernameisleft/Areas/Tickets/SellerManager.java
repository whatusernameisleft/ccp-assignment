package io.whatusernameisleft.Areas.Tickets;

import io.whatusernameisleft.Customer.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SellerManager {
    private final List<String> sellerNames;
    private List<TicketSeller> sellers = new ArrayList<>();

    public SellerManager(List<String> sellerNames) {
        this.sellerNames = sellerNames;
    }

    public void createSellers() {
        for (String name : sellerNames) {
            TicketSeller seller = new TicketSeller(name);
            seller.start();
            sellers.add(seller);
        }
    }

    public synchronized TicketSeller queue(Customer customer) {
        AtomicReference<TicketSeller> shortestQueueSeller = new AtomicReference<>();
        AtomicInteger shortestQueueCount = new AtomicInteger(100);
        sellers.forEach(s -> {
            if (s.getQueueCount() < shortestQueueCount.get()) {
                shortestQueueSeller.set(s);
                shortestQueueCount.set(s.getQueueCount());
            }
        });
        try {
            shortestQueueSeller.get().getQueue().put(customer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return shortestQueueSeller.get();
    }
}
