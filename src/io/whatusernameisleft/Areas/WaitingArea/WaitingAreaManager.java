package io.whatusernameisleft.Areas.WaitingArea;

import io.whatusernameisleft.Areas.Tickets.Ticket;

import java.util.*;

public class WaitingAreaManager {
    private Map<Ticket, WaitingArea> waitingAreaMap = new HashMap<>();

    public void createWaitingAreas() {
        Arrays.stream(Ticket.values()).forEach(t -> waitingAreaMap.put(t, new WaitingArea(t)));
    }

    public WaitingArea getWaitingArea(Ticket ticket) {
        return waitingAreaMap.get(ticket);
    }
}
