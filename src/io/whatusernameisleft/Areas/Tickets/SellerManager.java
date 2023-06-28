package io.whatusernameisleft.Areas.Tickets;

import io.whatusernameisleft.Areas.Tickets.TicketSeller.TicketBooth;
import io.whatusernameisleft.Areas.Tickets.TicketSeller.TicketMachine;
import io.whatusernameisleft.Areas.Tickets.TicketSeller.TicketSeller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SellerManager {
    private final List<String> machineNames;
    private final List<String> boothNames;
    private List<TicketSeller> sellers = new ArrayList<>();

    public SellerManager(List<String> machineNames, List<String> boothNames) {
        this.machineNames = machineNames;
        this.boothNames = boothNames;
    }

    public void createSellers() {
        for (String name : machineNames) {
            TicketSeller seller = new TicketMachine(name);
            seller.start();
            sellers.add(seller);
        }
        for (String name : boothNames) {
            TicketSeller seller = new TicketBooth(name);
            seller.start();
            sellers.add(seller);
        }
    }

    public synchronized TicketSeller getShortestQueueSeller() {
        AtomicReference<TicketSeller> shortestQueueSeller = new AtomicReference<>();
        AtomicInteger shortestQueueCount = new AtomicInteger(100);
        sellers.forEach(s -> {
            if (s.getQueueCount() < shortestQueueCount.get() && s.isOpen()) {
                shortestQueueSeller.set(s);
                shortestQueueCount.set(s.getQueueCount());
            }
        });

        return shortestQueueSeller.get();
    }
}
