package io.whatusernameisleft.Minibus;

import io.whatusernameisleft.Areas.Tickets.Ticket;
import io.whatusernameisleft.Building;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MinibusManager {
    private final Building building;
    private final int MAX_BUSES = 3;
    private final BlockingQueue<Minibus> queue = new ArrayBlockingQueue<>(MAX_BUSES, true);

    public MinibusManager(Building building) {
        this.building = building;
        createMinibuses();
    }

    private void createMinibuses() {
        Arrays.stream(Ticket.values()).forEach(t -> new Minibus(t, building).start());
    }

    public void park(Minibus minibus) {
        queue.offer(minibus);
    }

    public Minibus getMinibus() {
        return queue.poll();
    }
}
