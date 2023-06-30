package io.whatusernameisleft.Areas.Tickets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Ticket {
    BUKIT_BINTANG("Bukit Bintang"),
    KLCC("KLCC"),
    KL_TOWER("KL Tower");

    private String destination;

    Ticket(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

    public static List<String> getAllDestinations() {
        List<String> allDestinations = new ArrayList<>();
        Arrays.stream(values()).forEach(d -> allDestinations.add(d.getDestination()));
        return allDestinations;
    }
}
