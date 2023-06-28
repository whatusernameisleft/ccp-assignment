package io.whatusernameisleft.Customer;

import io.whatusernameisleft.Areas.Tickets.SellerManager;
import io.whatusernameisleft.Areas.Tickets.Ticket;
import io.whatusernameisleft.Areas.Tickets.TicketSeller.TicketSeller;
import io.whatusernameisleft.Areas.Waiting.Foyer.Foyer;
import io.whatusernameisleft.Areas.Waiting.WaitingArea.WaitingArea;
import io.whatusernameisleft.Areas.Waiting.WaitingArea.WaitingAreaManager;
import io.whatusernameisleft.Areas.Waiting.WaitingZone;
import io.whatusernameisleft.TBT;

public class Customer extends Thread {
    private final int id;
    private Ticket ticket = null;
    private CustomerType customerType = CustomerType.CUSTOMER;
    private final SellerManager sellerManager;
    private TicketSeller seller;
    private final WaitingAreaManager waitingAreaManager;
    private WaitingArea waitingArea;
    private final Foyer foyer;

    public Customer(int id, SellerManager sellerManager, WaitingAreaManager waitingAreaManager, Foyer foyer) {
        this.id = id;
        this.sellerManager = sellerManager;
        this.waitingAreaManager = waitingAreaManager;
        this.foyer = foyer;
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
        System.out.println(TBT.ANSI_YELLOW + getName() + " is queueing for " + seller.getSellerName() + TBT.ANSI_RESET);
        seller.addToQueue(this);
    }

    private void goWait() {
        waitingArea = waitingAreaManager.getWaitingArea(ticket);
        if (waitingArea.offer(this)) {
            System.out.println(TBT.ANSI_BLUE + getName() + " is waiting in " + waitingArea.getName() + TBT.ANSI_RESET);
        } else {
            foyer.offer(this);
            System.out.println(TBT.ANSI_CYAN + waitingArea.getName() + " is full. " + getName() + " is waiting in " + foyer.getName() + TBT.ANSI_RESET);
        }
    }
}
