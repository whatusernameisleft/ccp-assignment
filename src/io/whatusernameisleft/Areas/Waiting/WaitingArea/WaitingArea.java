package io.whatusernameisleft.Areas.Waiting.WaitingArea;

import io.whatusernameisleft.Areas.Tickets.Ticket;
import io.whatusernameisleft.Areas.Waiting.WaitingZone;

public class WaitingArea extends WaitingZone {

    public WaitingArea(Ticket ticketType) {
        super("Waiting Area (" + ticketType.getDestination() + ")", 10);
    }
}
