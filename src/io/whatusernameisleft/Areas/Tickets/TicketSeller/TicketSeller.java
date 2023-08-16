package io.whatusernameisleft.Areas.Tickets.TicketSeller;

import io.whatusernameisleft.Areas.Tickets.Ticket;
import io.whatusernameisleft.Building;
import io.whatusernameisleft.Customer.Customer;
import io.whatusernameisleft.Formatting;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class TicketSeller {
    protected final String name;
    protected AtomicBoolean open = new AtomicBoolean(true);
    protected final int MAX_QUEUE = 3;
    protected final Ticket[] tickets = Ticket.values();
    protected final BlockingQueue<Customer> queue = new ArrayBlockingQueue<>(MAX_QUEUE, true);
    protected final Building building;

    public TicketSeller(String name, Building building) {
        this.name = name;
        this.building = building;
    }

    public int getQueueCount() {
        return queue.size();
    }

    public void close() {
        open.set(false);
    }

    public void open() {
        open.set(true);
    }

    public AtomicBoolean isOpen() {
        return open;
    }

    public String getName() {
        return name;
    }

    public boolean addToQueue(Customer customer) {
        try {
            return queue.offer(customer, ThreadLocalRandom.current().nextInt(3), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    protected void sellTicket() {
        if (queue.isEmpty()) return;
        Customer customer = queue.poll();
        synchronized (customer) {
            customer.buyTicket(tickets[ThreadLocalRandom.current().nextInt(tickets.length)]);
            customer.notify();
        }
    }

    protected void redirectQueue() {
        while (!open.get() && !queue.isEmpty()) {
            try {
                Customer customer = queue.take();
                synchronized (customer) {
                    System.out.println(Formatting.ANSI_YELLOW + Formatting.ANSI_ITALIC + customer.getName() + " has been redirected." + Formatting.ANSI_RESET);
                    customer.notify();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
