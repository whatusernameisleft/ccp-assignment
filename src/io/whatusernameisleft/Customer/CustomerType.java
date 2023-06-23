package io.whatusernameisleft.Customer;

public enum CustomerType {
    CUSTOMER("Customer"),
    PASSENGER("Passenger");

    private String type;

    CustomerType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
