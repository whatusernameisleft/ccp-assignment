package io.whatusernameisleft;

import io.whatusernameisleft.Customer.CustomerGenerator;

public class TBT {
    public static void main(String[] args) {
        Building building = new Building();
        CustomerGenerator cg = new CustomerGenerator(building);
        cg.start();

    }
}
