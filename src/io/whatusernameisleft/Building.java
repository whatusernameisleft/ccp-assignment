package io.whatusernameisleft;

import io.whatusernameisleft.Areas.Tickets.SellerManager;
import io.whatusernameisleft.Areas.Waiting.Foyer;
import io.whatusernameisleft.Areas.Waiting.WaitingArea.WaitingAreaManager;
import io.whatusernameisleft.Customer.Customer;
import io.whatusernameisleft.Minibus.MinibusManager;
import io.whatusernameisleft.TicketInspector.TicketInspector;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Building {
    private enum State {
        NOT_FULL,
        FULL
    }

    private final AtomicInteger peopleCount = new AtomicInteger(0);
    private final AtomicInteger left = new AtomicInteger(0);
    private final int CUSTOMERS;
    private final int BUILDING_MAX = 45;
    private final int THRESHOLD = (int) (BUILDING_MAX * 0.8);
    private State state = State.NOT_FULL;
    private boolean closed = false;
    private final SellerManager sellerManager = new SellerManager(Collections.singletonList("Ticket Machine"), Arrays.asList("Ticket Booth 1", "Ticket Booth 2"), this);
    private final Foyer foyer = new Foyer("Terminal Foyer");
    private final WaitingAreaManager waitingAreaManager = new WaitingAreaManager();
    private final MinibusManager minibusManager = new MinibusManager(this);
    private final TicketInspector ticketInspector = new TicketInspector("Ticket Inspector", waitingAreaManager, minibusManager);

    public Building(int customers) {
        CUSTOMERS = customers;
    }

    public synchronized boolean enter(Customer customer) throws InterruptedException {
        if (state == State.FULL) return false;
        peopleCount.incrementAndGet();
        System.out.println(Formatting.ANSI_ITALIC + customer.getName() + " has entered the building from the " + getEntrance() + " Entrance." + Formatting.ANSI_RESET);
        customer.becomeCustomer();
        if (isFull()) state = State.FULL;
        Thread.sleep(ThreadLocalRandom.current().nextInt(3) * 1000);
        return true;
    }

    public void leave(int count) {
        peopleCount.set(peopleCount.get() - count);
        left.set(left.get() + count);
        System.out.println(Formatting.ANSI_ITALIC + Formatting.ANSI_BOLD + count + " passengers have left the building." + Formatting.ANSI_RESET);
        if (belowThreshold()) state = State.NOT_FULL;
    }

    public void close() {
        if (left.get() != CUSTOMERS) return;
        sellerManager.closeSellers();
        minibusManager.closeMinibuses();
        ticketInspector.leave();
        closed = true;
    }

    public boolean isFull() {
        return peopleCount.get() == BUILDING_MAX;
    }

    public boolean isClosed() {
        return closed;
    }

    public boolean belowThreshold() {
        return peopleCount.get() < THRESHOLD;
    }

    private String getEntrance() {
        return ThreadLocalRandom.current().nextDouble() < 0.5 ? "West" : "East";
    }

    public Foyer getFoyer() {
        return foyer;
    }

    public SellerManager getSellerManager() {
        return sellerManager;
    }

    public WaitingAreaManager getWaitingAreaManager() {
        return waitingAreaManager;
    }

    public MinibusManager getMinibusManager() {
        return minibusManager;
    }
}
