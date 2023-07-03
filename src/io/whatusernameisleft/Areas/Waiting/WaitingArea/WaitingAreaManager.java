package io.whatusernameisleft.Areas.Waiting.WaitingArea;

import io.whatusernameisleft.Areas.Tickets.Ticket;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WaitingAreaManager {
    private Map<Ticket, WaitingArea> waitingAreaMap = new HashMap<>();

    public WaitingAreaManager() {
        createWaitingAreas();
    }

    private void createWaitingAreas() {
        Arrays.stream(Ticket.values()).forEach(t -> waitingAreaMap.put(t, new WaitingArea(t)));
    }

    public WaitingArea getWaitingArea(Ticket ticket) {
        return waitingAreaMap.get(ticket);
    }
}
