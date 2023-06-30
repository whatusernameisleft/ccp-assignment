package io.whatusernameisleft.Customer;

import io.whatusernameisleft.Areas.Tickets.SellerManager;
import io.whatusernameisleft.Areas.Tickets.Ticket;
import io.whatusernameisleft.Areas.Tickets.TicketSeller.TicketSeller;
import io.whatusernameisleft.Areas.Waiting.Foyer.Foyer;
import io.whatusernameisleft.Areas.Waiting.Foyer.FoyerManager;
import io.whatusernameisleft.Areas.Waiting.WaitingArea.WaitingArea;
import io.whatusernameisleft.Areas.Waiting.WaitingArea.WaitingAreaManager;
import io.whatusernameisleft.Building;
import io.whatusernameisleft.Formatting;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Customer extends Thread {
    private final int id;
    private Ticket ticket = null;
    private CustomerType customerType = CustomerType.CUSTOMER;
    private final FoyerManager foyerManager;
    private final SellerManager sellerManager;
    private TicketSeller seller;
    private final WaitingAreaManager waitingAreaManager;

    public Customer(int id, Building building) {
        this.id = id;
        this.foyerManager = building.getFoyerManager();
        this.sellerManager = building.getSellerManager();
        this.waitingAreaManager = building.getWaitingAreaManager();
        setName(getCustomerName());
    }

    @Override
    public void run() {
        queue();
    }

    public void buyTicket(Ticket ticket) {
        this.ticket = ticket;
        customerType = CustomerType.PASSENGER;
        setName(getCustomerName());
        System.out.println(Formatting.ANSI_GREEN + getName() + " has bought a ticket from " + seller.getName() + " for " + ticket.getDestination() + Formatting.ANSI_RESET);
        goWait();
    }

    private String getCustomerName() {
        return customerType.getType() + "-" + id;
    }

    public boolean isPassenger() {
        return ticket != null;
    }

    private void queue() {
        seller = sellerManager.getShortestQueueSeller();
        if (seller == null) {
            Foyer foyer = foyerManager.getFoyer(CustomerType.CUSTOMER);
            System.out.println(Formatting.ANSI_BOLD + Formatting.ANSI_FRAMED + Formatting.ANSI_CYAN + "No ticket sellers are open. " + getName() + " is waiting in " + foyer.getName() + Formatting.ANSI_RESET);
            foyer.offer(this);
        }
        else seller.addToQueue(this);
    }

    private void goWait() {
        WaitingArea waitingArea = waitingAreaManager.getWaitingArea(ticket);
        try {
            if (waitingArea.offer(this, ThreadLocalRandom.current().nextInt(3), TimeUnit.SECONDS))
                System.out.println(Formatting.ANSI_BLUE + getName() + " is waiting in " + waitingArea.getName() + Formatting.ANSI_RESET);
            else {
                Foyer foyer = foyerManager.getFoyer(CustomerType.PASSENGER);
                if (foyer.offer(this))
                    System.out.println(Formatting.ANSI_CYAN + waitingArea.getName() + " is full. " + getName() + " is waiting in " + foyer.getName() + Formatting.ANSI_RESET);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void tryAgain() {
        if (isPassenger()) goWait();
        else queue();
    }
}
