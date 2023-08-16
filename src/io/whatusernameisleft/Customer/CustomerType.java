package io.whatusernameisleft.Customer;

public enum CustomerType {
    OUTSIDE("Customer"),
    CUSTOMER("Customer"),
    PASSENGER("Passenger"),
    WAITING("Passenger"),
    LEFT("Left");

    private String type;

    CustomerType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
