package io.whatusernameisleft;

import io.whatusernameisleft.Areas.Tickets.SellerManager;
import io.whatusernameisleft.Areas.Waiting.Foyer.FoyerManager;
import io.whatusernameisleft.Areas.Waiting.WaitingArea.WaitingAreaManager;
import io.whatusernameisleft.Customer.Customer;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Building {
    private AtomicInteger peopleCount = new AtomicInteger(0);
    private final int BUILDING_MAX = 45;
    private final int THRESHOLD = (int) (BUILDING_MAX * 0.8);
    private final FoyerManager foyerManager = new FoyerManager();
    private final SellerManager sellerManager = new SellerManager(Collections.singletonList("Ticket Machine"), Arrays.asList("Ticket Booth 1", "Ticket Booth 2"), foyerManager);;
    private final WaitingAreaManager waitingAreaManager = new WaitingAreaManager();

    public void enter(Customer customer) {
        peopleCount.incrementAndGet();
        System.out.println(Formatting.ANSI_ITALIC + customer.getName() + " has entered the building from the " + getEntrance() + " Entrance." + Formatting.ANSI_RESET);
    }

    public void leave(Customer customer) {
        peopleCount.decrementAndGet();
        System.out.println(Formatting.ANSI_ITALIC + customer.getName() + " has left the building." + Formatting.ANSI_RESET);
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
}
