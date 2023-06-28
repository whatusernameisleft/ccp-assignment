package io.whatusernameisleft.Areas.Tickets.TicketSeller;

import io.whatusernameisleft.Areas.Tickets.Ticket;
import io.whatusernameisleft.Customer.Customer;
import io.whatusernameisleft.TBT;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public abstract class TicketSeller extends Thread {
    protected final String name;
    protected volatile boolean open = true;
    protected final int MAX_QUEUE = 3;
    protected final Ticket[] tickets = Ticket.values();
    protected final BlockingQueue<Customer> queue = new ArrayBlockingQueue<>(MAX_QUEUE, true);

    public TicketSeller(String name) {
        this.name = name;
    }

    public BlockingQueue<Customer> getQueue() {
        return queue;
    }

    public int getQueueCount() {
        return queue.size();
    }

    public String getSellerName() {
        return name;
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

    public void addToQueue(Customer customer) {
        try {
            queue.put(customer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (open) {
            Customer c = null;
            try {
                while (!queue.isEmpty()) {
                    c = queue.take();
                    Thread.sleep(ThreadLocalRandom.current().nextInt(7) * 1000);
                    c.buyTicket(tickets[ThreadLocalRandom.current().nextInt(tickets.length)]);
                }
            } catch (Exception e) {
                System.out.println(TBT.ANSI_RED + c.getName() + " is null" + TBT.ANSI_RESET);
                e.printStackTrace();
            }
        }
    }
}
