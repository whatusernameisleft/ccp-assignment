package io.whatusernameisleft.Minibus;

import io.whatusernameisleft.Areas.Tickets.Ticket;
import io.whatusernameisleft.Building;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MinibusManager {
    private final Building building;
    private final int MAX_BUSES = 3;
    private final List<Minibus> minibuses = new ArrayList<>();
    private final BlockingQueue<Minibus> queue = new ArrayBlockingQueue<>(MAX_BUSES, true);

    public MinibusManager(Building building) {
        this.building = building;
        createMinibuses();
    }

    private void createMinibuses() {
        for (Ticket ticket : Ticket.values()) {
            Minibus minibus = new Minibus(ticket, building);
            minibus.start();
            minibuses.add(minibus);
        }
    }

    public void park(Minibus minibus) {
        queue.offer(minibus);
    }

    public Minibus getMinibus() {
        return queue.poll();
    }

    public void closeMinibuses() {
        minibuses.forEach(m -> {
            synchronized (m) {
                m.close();
                m.notify();
            }
        });
    }
}
