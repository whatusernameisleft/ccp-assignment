package io.whatusernameisleft.Areas.Waiting.WaitingArea;

import io.whatusernameisleft.Areas.Tickets.Ticket;
import io.whatusernameisleft.Areas.Waiting.WaitingZone;
import io.whatusernameisleft.Customer.Customer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class WaitingArea extends WaitingZone {
    private final BlockingQueue<Customer> queue = new ArrayBlockingQueue<>(10, true);

    public WaitingArea(Ticket ticketType) {
        super("Waiting Area (" + ticketType.getDestination() + ")");
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public Customer getCustomer() {
        return queue.poll();
    }

    public boolean offer(Customer customer, long timeout, TimeUnit unit) throws InterruptedException {
        return queue.offer(customer, timeout, unit);
    }
}
