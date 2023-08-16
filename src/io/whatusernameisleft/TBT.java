package io.whatusernameisleft;

import io.whatusernameisleft.Customer.CustomerGenerator;

public class TBT {
    public static void main(String[] args) {
        int CUSTOMERS = 80;
        Building building = new Building(CUSTOMERS);
        CustomerGenerator cg = new CustomerGenerator(building, CUSTOMERS);
        cg.create();
    }
}
