package io.whatusernameisleft.Areas.Tickets;

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
}
