package io.whatusernameisleft.Areas.Tickets.TicketSeller;

import io.whatusernameisleft.Areas.Tickets.Ticket;
import io.whatusernameisleft.Areas.Waiting.Foyer.Foyer;
import io.whatusernameisleft.Areas.Waiting.Foyer.FoyerManager;
import io.whatusernameisleft.Customer.Customer;
import io.whatusernameisleft.Customer.CustomerType;
import io.whatusernameisleft.TBT;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public abstract class TicketSeller extends Thread {
    protected final String name;
    protected volatile boolean open = true;
    protected final int MAX_QUEUE = 3;
    protected final Ticket[] tickets = Ticket.values();
    protected final BlockingQueue<Customer> queue = new ArrayBlockingQueue<>(MAX_QUEUE, true);
    protected final FoyerManager foyerManager;

    public TicketSeller(String name, FoyerManager foyerManager) {
        this.name = name;
        this.foyerManager = foyerManager;
        setName(name);
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

    public void addToQueue(Customer customer) {
        try {
            if (queue.offer(customer, ThreadLocalRandom.current().nextInt(3), TimeUnit.SECONDS)) {
                System.out.println(TBT.ANSI_YELLOW + customer.getName() + " is queueing for " + getName() + TBT.ANSI_RESET);
            } else {
                Foyer foyer = foyerManager.getFoyer(CustomerType.CUSTOMER);
                foyer.offer(customer);
                System.out.println(TBT.ANSI_CYAN + getName() + " queue is full. " + customer.getName() + " is waiting in " + foyer.getName() + TBT.ANSI_RESET);
            }
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
                    Thread.sleep(ThreadLocalRandom.current().nextInt(15) * 1000);
                    c = queue.take();
                    c.buyTicket(tickets[ThreadLocalRandom.current().nextInt(tickets.length)]);
                }
            } catch (Exception e) {
                System.out.println(TBT.ANSI_RED + c.getName() + " is null" + TBT.ANSI_RESET);
                e.printStackTrace();
            }
        }
    }
}
