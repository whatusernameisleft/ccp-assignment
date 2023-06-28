package io.whatusernameisleft.Customer;

import io.whatusernameisleft.Areas.Tickets.SellerManager;
import io.whatusernameisleft.Areas.Tickets.Ticket;
import io.whatusernameisleft.Areas.Tickets.TicketSeller.TicketSeller;
import io.whatusernameisleft.Areas.WaitingArea.WaitingArea;
import io.whatusernameisleft.Areas.WaitingArea.WaitingAreaManager;
import io.whatusernameisleft.TBT;

public class Customer extends Thread {
    private final int id;
    private Ticket ticket = null;
    private CustomerType customerType = CustomerType.CUSTOMER;
    private final SellerManager sellerManager;
    private TicketSeller seller;
    private final WaitingAreaManager waitingAreaManager;
    private WaitingArea waitingArea;

    public Customer(int id, SellerManager sellerManager, WaitingAreaManager waitingAreaManager) {
        this.id = id;
        this.sellerManager = sellerManager;
        this.waitingAreaManager = waitingAreaManager;
        setName(getCustomerName());
    }

    @Override
    public void run() {
        queue();
    }

    public int getCustomerId() {
        return id;
    }

    public void buyTicket(Ticket ticket) {
        this.ticket = ticket;
        customerType = CustomerType.PASSENGER;
        setName(getCustomerName());
        System.out.println(TBT.ANSI_GREEN + getName() + " has bought a ticket from " + seller.getSellerName() + " for " + ticket.getDestination() + TBT.ANSI_RESET);
        goWait();
    }

    public String getCustomerName() {
        return customerType.getType() + "-" + id;
    }

    public boolean isPassenger() {
        return ticket != null;
    }

    private void queue() {
        seller = sellerManager.getShortestQueueSeller();
        seller.addToQueue(this);
        System.out.println(TBT.ANSI_YELLOW + getName() + " is queueing for " + seller.getSellerName() + TBT.ANSI_RESET);
    }

    private void goWait() {
        waitingArea = waitingAreaManager.getWaitingArea(ticket);
        waitingArea.wait(this);
    }
}
