package io.whatusernameisleft.Areas.Tickets;

import io.whatusernameisleft.Customer.Customer;

public class TicketBooth {

    public TicketBooth(String name) {

    }

    public void buyTicket(Customer customer) {
        System.out.println("Customer-" + customer.getCustomerId());
    }
}
