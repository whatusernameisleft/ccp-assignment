package io.whatusernameisleft.Areas.Tickets.TicketSeller;

import io.whatusernameisleft.Areas.Tickets.Ticket;
import io.whatusernameisleft.Areas.Waiting.Foyer.Foyer;
import io.whatusernameisleft.Areas.Waiting.Foyer.FoyerManager;
import io.whatusernameisleft.Customer.Customer;
import io.whatusernameisleft.Customer.CustomerType;
import io.whatusernameisleft.Formatting;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public abstract class TicketSeller {
    protected final String name;
    protected boolean open = true;
    protected final int MAX_QUEUE = 3;
    protected final Ticket[] tickets = Ticket.values();
    protected final BlockingQueue<Customer> queue = new ArrayBlockingQueue<>(MAX_QUEUE, true);
    protected final FoyerManager foyerManager;

    public TicketSeller(String name, FoyerManager foyerManager) {
        this.name = name;
        this.foyerManager = foyerManager;
    }

    public BlockingQueue<Customer> getQueue() {
        return queue;
    }

    public int getQueueCount() {
        return queue.size();
    }

    protected void close() {
        open = false;
    }

    protected void open() {
        open = true;
    }

    public boolean isOpen() {
        return open;
    }

    public String getName() {
        return name;
    }

    public void addToQueue(Customer customer) {
        try {
            if (queue.offer(customer, ThreadLocalRandom.current().nextInt(3), TimeUnit.SECONDS)) {
                System.out.println(Formatting.ANSI_YELLOW + customer.getName() + " is queueing for " + name + Formatting.ANSI_RESET);
            } else {
                Foyer foyer = foyerManager.getFoyer(CustomerType.CUSTOMER);
                foyer.offer(customer);
                System.out.println(Formatting.ANSI_CYAN + name + " queue is full. " + customer.getName() + " is waiting in " + foyer.getName() + Formatting.ANSI_RESET);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void sellTicket() throws InterruptedException {
        queue.take().buyTicket(tickets[ThreadLocalRandom.current().nextInt(tickets.length)]);
    }

    protected void redirectQueue() {
        while (!open && !queue.isEmpty()) {
            try {
                queue.take().tryAgain();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
