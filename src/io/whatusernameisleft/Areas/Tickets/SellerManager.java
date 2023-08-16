package io.whatusernameisleft.Areas.Tickets;

import io.whatusernameisleft.Areas.Tickets.TicketSeller.TicketMachine;
import io.whatusernameisleft.Areas.Tickets.TicketSeller.TicketPersonnel;
import io.whatusernameisleft.Areas.Tickets.TicketSeller.TicketSeller;
import io.whatusernameisleft.Building;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SellerManager {
    private final List<String> machineNames;
    private final List<String> boothNames;
    private final Building building;
    private List<TicketSeller> sellers = new ArrayList<>();
    private List<TicketPersonnel> personnel = new ArrayList<>();

    public SellerManager(List<String> machineNames, List<String> boothNames, Building building) {
        this.machineNames = machineNames;
        this.boothNames = boothNames;
        this.building = building;
        createSellers();
    }

    private void createSellers() {
        for (String name : machineNames) {
            TicketMachine seller = new TicketMachine(name, building);
            seller.start();
            sellers.add(seller);
        }
        for (String name : boothNames) {
            TicketSeller seller = new TicketSeller(name, building);
            TicketPersonnel personnel = new TicketPersonnel(seller, building);
            sellers.add(seller);
            personnel.start();
            this.personnel.add(personnel);
        }
    }

    public synchronized TicketSeller getShortestQueueSeller() {
        AtomicReference<TicketSeller> shortestQueueSeller = new AtomicReference<>();
        AtomicInteger shortestQueueCount = new AtomicInteger(100);
        sellers.forEach(s -> {
            if (s.isOpen().get() && s.getQueueCount() < shortestQueueCount.get()) {
                shortestQueueSeller.set(s);
                shortestQueueCount.set(s.getQueueCount());
            }
        });

        return shortestQueueSeller.get();
    }

    public void closeSellers() {
        sellers.get(0).close();
        personnel.forEach(TicketPersonnel::leave);
    }
}
