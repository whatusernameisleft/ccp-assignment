package io.whatusernameisleft.Customer;

import io.whatusernameisleft.Areas.Tickets.TicketSeller;
import io.whatusernameisleft.TBT;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Customer extends Thread {
    private final int id;
    private boolean hasTicket = false;
    private CustomerType customerType = CustomerType.CUSTOMER;
    private final List<TicketSeller> sellers;
    private TicketSeller seller;

    public Customer(int id, List<TicketSeller> sellers) {
        this.id = id;
        this.sellers = sellers;
        setName(getCustomerName());
    }

    @Override
    public void run() {
        queue();
    }

    public int getCustomerId() {
        return id;
    }

    public void buyTicket() {
        hasTicket = true;
        customerType = CustomerType.PASSENGER;
        setName(getCustomerName());
        System.out.println(TBT.ANSI_GREEN + getName() + " has bought a ticket from " + seller.getSellerName() + TBT.ANSI_RESET);
    }

    public String getCustomerName() {
        return customerType.getType() + "-" + id;
    }

    public boolean isPassenger() {
        return hasTicket;
    }

    private void queue() {
        AtomicReference<TicketSeller> shortestQueueSeller = new AtomicReference<>();
        AtomicInteger shortestQueueCount = new AtomicInteger(100);
        sellers.forEach(s -> {
            if (s.getQueueCount() < shortestQueueCount.get()) {
                shortestQueueSeller.set(s);
                shortestQueueCount.set(s.getQueueCount());
            }
        });
        try {
            seller = shortestQueueSeller.get();
            seller.getQueue().put(this);
            System.out.println(TBT.ANSI_YELLOW + getName() + " is queueing for " + seller.getSellerName() + TBT.ANSI_RESET);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
