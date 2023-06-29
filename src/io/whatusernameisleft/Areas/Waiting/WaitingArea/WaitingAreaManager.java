package io.whatusernameisleft.Areas.Waiting.WaitingArea;

import io.whatusernameisleft.Areas.Tickets.Ticket;

import java.util.*;

public class WaitingAreaManager {
    public WaitingAreaManager() {
        createWaitingAreas();
    }

    private Map<Ticket, WaitingArea> waitingAreaMap = new HashMap<>();

    private void createWaitingAreas() {
        Arrays.stream(Ticket.values()).forEach(t -> waitingAreaMap.put(t, new WaitingArea(t)));
    }

    public WaitingArea getWaitingArea(Ticket ticket) {
        return waitingAreaMap.get(ticket);
    }
}
