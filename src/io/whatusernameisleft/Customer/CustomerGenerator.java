package io.whatusernameisleft.Customer;

import io.whatusernameisleft.Building;

public class CustomerGenerator {
    private final int CUSTOMERS;
    private volatile boolean stopped = false;
    private final Building building;

    public CustomerGenerator(Building building, int customers) {
        this.building = building;
        CUSTOMERS = customers;
    }

    public void create() {
        for (int i = 1; i <= CUSTOMERS; i++) {
            Customer customer = new Customer(i, building);
            customer.start();
        }
    }
}
