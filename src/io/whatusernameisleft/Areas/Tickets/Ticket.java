package io.whatusernameisleft.Areas.Tickets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Ticket {
    KL_SENTRAL("KL Sentral Bus Terminal"),
    PASAR_SENI("Pasar Seni Bus Hub"),
    KOTA_RAYA("Kota Raya Bus Terminal");

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
