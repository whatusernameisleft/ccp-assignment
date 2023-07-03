package io.whatusernameisleft.Areas.Waiting.WaitingArea;

import io.whatusernameisleft.Areas.Tickets.Ticket;
import io.whatusernameisleft.Areas.Waiting.WaitingZone;
import io.whatusernameisleft.Customer.Customer;

public class WaitingArea extends WaitingZone {

    public WaitingArea(Ticket ticketType) {
        super("Waiting Area (" + ticketType.getDestination() + ")", 10);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public Customer getCustomer() {
        return queue.poll();
    }
}
