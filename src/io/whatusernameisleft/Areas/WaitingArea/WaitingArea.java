package io.whatusernameisleft.Areas.WaitingArea;

import io.whatusernameisleft.Areas.Tickets.Ticket;
import io.whatusernameisleft.Customer.Customer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class WaitingArea {
    private final Ticket ticketType;
    private final String name;
    private final int MAX_QUEUE = 10;
    private final BlockingQueue<Customer> queue = new ArrayBlockingQueue<>(MAX_QUEUE, true);

    public WaitingArea(Ticket ticketType) {
        this.ticketType = ticketType;
        this.name = "Waiting Area (" + ticketType.getDestination() + ")";
    }

    public BlockingQueue<Customer> getQueue() {
        return queue;
    }

    public void wait(Customer customer) {
        try {
            queue.put(customer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
