package io.whatusernameisleft.Customer;

import io.whatusernameisleft.Areas.Tickets.SellerManager;
import io.whatusernameisleft.Areas.Tickets.TicketSeller;
import io.whatusernameisleft.TBT;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Customer extends Thread {
    private final int id;
    private boolean hasTicket = false;
    private CustomerType customerType = CustomerType.CUSTOMER;
    private final SellerManager sellerManager;
    private TicketSeller seller;

    public Customer(int id, SellerManager sellerManager) {
        this.id = id;
        this.sellerManager = sellerManager;
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
        seller = sellerManager.queue(this);
        System.out.println(TBT.ANSI_YELLOW + getName() + " is queueing for " + seller.getSellerName() + TBT.ANSI_RESET);
    }
}
