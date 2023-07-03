package io.whatusernameisleft;

import io.whatusernameisleft.Areas.Tickets.SellerManager;
import io.whatusernameisleft.Areas.Waiting.Foyer.FoyerManager;
import io.whatusernameisleft.Areas.Waiting.WaitingArea.WaitingAreaManager;
import io.whatusernameisleft.Customer.Customer;
import io.whatusernameisleft.Minibus.MinibusManager;
import io.whatusernameisleft.TicketInspector.TicketInspector;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Building {
    private final AtomicInteger peopleCount = new AtomicInteger(0);
    private final int BUILDING_MAX = 45;
    private final int THRESHOLD = (int) (BUILDING_MAX * 0.8);
    private final FoyerManager foyerManager = new FoyerManager();
    private final SellerManager sellerManager = new SellerManager(Collections.singletonList("Ticket Machine"), Arrays.asList("Ticket Booth 1", "Ticket Booth 2"), foyerManager);
    private final WaitingAreaManager waitingAreaManager = new WaitingAreaManager();
    private final MinibusManager minibusManager = new MinibusManager(this);
    private final TicketInspector ticketInspector = new TicketInspector("Ticket Inspector", waitingAreaManager, minibusManager);

    public void enter(Customer customer) {
        peopleCount.incrementAndGet();
        System.out.println(Formatting.ANSI_ITALIC + customer.getName() + " has entered the building from the " + getEntrance() + " Entrance." + Formatting.ANSI_RESET);
    }

    public void leave(int count) {
        peopleCount.set(peopleCount.get() - count);
        System.out.println(Formatting.ANSI_ITALIC + Formatting.ANSI_BOLD + count + " passengers have left the building." + Formatting.ANSI_RESET);
    }

    public boolean isFull() {
        return peopleCount.get() == BUILDING_MAX;
    }

    public boolean belowThreshold() {
        return peopleCount.get() < THRESHOLD;
    }

    private String getEntrance() {
        return ThreadLocalRandom.current().nextDouble() < 0.5 ? "West" : "East";
    }

    public FoyerManager getFoyerManager() {
        return foyerManager;
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
