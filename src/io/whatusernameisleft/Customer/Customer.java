package io.whatusernameisleft.Customer;

import io.whatusernameisleft.Areas.Tickets.Ticket;
import io.whatusernameisleft.Areas.Tickets.TicketSeller.TicketSeller;
import io.whatusernameisleft.Areas.Waiting.WaitingArea.WaitingArea;
import io.whatusernameisleft.Building;
import io.whatusernameisleft.Formatting;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Customer extends Thread {
    private final int id;
    private Ticket ticket = null;
    private volatile CustomerType customerType = CustomerType.OUTSIDE;
    private volatile boolean waiting = false;
    private final Building building;
    private TicketSeller seller;

    public Customer(int id, Building building) {
        this.id = id;
        this.building = building;
        setName(getCustomerName());
    }

    @Override
    public void run() {
        while (customerType != CustomerType.LEFT) {
            try {
                move();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void move() throws InterruptedException {
        switch (customerType) {
            case OUTSIDE:
                Thread.sleep(ThreadLocalRandom.current().nextInt(3) * 1000);
                enterBuilding();
                break;
            case CUSTOMER:
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 4) * 1000);
                queue();
                break;
            case PASSENGER:
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 4) * 1000);
                goWait();
                break;
        }
    }

    public void becomeCustomer() {
        customerType = CustomerType.CUSTOMER;
        waiting = false;
    }

    public void leave() {
        customerType = CustomerType.LEFT;
    }

    private void enterBuilding() throws InterruptedException {
        if (!building.enter(this)) {
            if (!waiting) {
                System.out.println(Formatting.ANSI_FRAMED + Formatting.ANSI_WHITE + getName() + " is waiting outside of the building." + Formatting.ANSI_RESET);
                waiting = true;
            }
        }
    }

    private void queue() throws InterruptedException {
        synchronized (this) {
            seller = building.getSellerManager().getShortestQueueSeller();
            if (seller == null) {
                if (!waiting) {
                    System.out.println(Formatting.ANSI_BOLD + Formatting.ANSI_FRAMED + Formatting.ANSI_CYAN + "No ticket sellers are open. " + getName() + " is waiting in " + building.getFoyer().getName() + "." + Formatting.ANSI_RESET);
                    waiting = true;
                }
            } else if (seller.addToQueue(this)) {;
                System.out.println(Formatting.ANSI_YELLOW + getName() + " is queueing for " + seller.getName() + "." + Formatting.ANSI_RESET);
                waiting = false;
                wait();
            } else {
                if (!waiting) {
                    System.out.println(Formatting.ANSI_CYAN + seller.getName() + " queue is full. " + getName() + " is waiting in " + building.getFoyer().getName() + "." + Formatting.ANSI_RESET);
                    waiting = true;
                }
            }
        }
    }

    private void goWait() throws InterruptedException {
        synchronized (this) {
            WaitingArea waitingArea = building.getWaitingAreaManager().getWaitingArea(ticket);
            if (waitingArea.offer(this, ThreadLocalRandom.current().nextInt(3), TimeUnit.SECONDS)) {
                System.out.println(Formatting.ANSI_BLUE + getName() + " is waiting in " + waitingArea.getName() + "." + Formatting.ANSI_RESET);
                customerType = CustomerType.WAITING;
                waiting = false;
                wait();
            } else {
                if (!waiting) {
                    System.out.println(Formatting.ANSI_CYAN + waitingArea.getName() + " is full. " + getName() + " is waiting in " + building.getFoyer().getName() + "." + Formatting.ANSI_RESET);
                    waiting = true;
                }
            }
        }
}

    public void buyTicket(Ticket ticket) {
        this.ticket = ticket;
        customerType = CustomerType.PASSENGER;
        setName(getCustomerName());
        System.out.println(Formatting.ANSI_GREEN + getName() + " has bought a ticket from " + seller.getName() + " for " + ticket.getDestination() + "." + Formatting.ANSI_RESET);
    }

    private String getCustomerName() {
        return customerType.getType() + "-" + id;
    }

    public boolean isWaiting() {
        return customerType == CustomerType.WAITING;
    }
}
