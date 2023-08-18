package io.whatusernameisleft.Minibus;

import io.whatusernameisleft.Areas.Tickets.Ticket;
import io.whatusernameisleft.Building;
import io.whatusernameisleft.Customer.Customer;
import io.whatusernameisleft.Formatting;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Minibus extends Thread {
    private volatile boolean running = true;
    private final Ticket ticket;
    private final Building building;
    private final int MAX_SEATS = 10;
    private final BlockingQueue<Customer> seats = new ArrayBlockingQueue<>(MAX_SEATS);

    public Minibus(Ticket ticket, Building building) {
        this.ticket = ticket;
        this.building = building;
        setName(ticket.getDestination() + " Minibus");
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void board(Customer customer) {
        seats.offer(customer);
        System.out.println(Formatting.ANSI_PURPLE + customer.getName() + " has boarded the " + getName() + ". (" + seats.size() + "/" + MAX_SEATS + ")" + Formatting.ANSI_RESET);
    }

    public boolean isFull() {
        return seats.remainingCapacity() == 0;
    }

    private void clear() {
        seats.clear();
    }

    private void arrive() {
        building.getMinibusManager().park(this);
        System.out.println(Formatting.ANSI_PURPLE + Formatting.ANSI_FRAMED + Formatting.ANSI_BOLD + " " + getName() + " has arrived at the terminal. " + Formatting.ANSI_RESET);
    }

    private void leave() {
        System.out.println(Formatting.ANSI_YELLOW + Formatting.ANSI_FRAMED + Formatting.ANSI_BOLD + " " + getName() + " has left the terminal. " + Formatting.ANSI_RESET);
        building.leave(seats.size());
        seats.forEach(Customer::leave);
        clear();
        building.close();
    }

    public void close() {
        running = false;
    }

    @Override
    public void run() {
        while (running && !building.isClosed()) {
            try {
                synchronized (this) {
                    if (ThreadLocalRandom.current().nextDouble() < 0.1) {
                        System.out.println(Formatting.ANSI_BOLD + Formatting.ANSI_FRAMED + Formatting.ANSI_RED + " " + getName() + " has been delayed. " + Formatting.ANSI_RESET);
                        Thread.sleep(ThreadLocalRandom.current().nextInt(25, 36) * 1000);
                    } else {
                        Thread.sleep(ThreadLocalRandom.current().nextInt(20, 31) * 1000);
                    }
                    if (building.isClosed()) return;
                    arrive();
                    this.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            leave();
        }
    }
}
