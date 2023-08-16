package io.whatusernameisleft.Areas.Tickets;

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

}
