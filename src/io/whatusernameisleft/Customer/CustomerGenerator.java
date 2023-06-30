package io.whatusernameisleft.Customer;

import io.whatusernameisleft.Building;

import java.util.concurrent.ThreadLocalRandom;

public class CustomerGenerator extends Thread {
    private volatile boolean stopped = false;
    private int id = 1;
    private final Building building;

    public CustomerGenerator(Building building) {
        this.building = building;
    }

    private void pause() {
        stopped = true;
    }

    private void unpause() {
        stopped = false;
    }

    @Override
    public void run() {
        while (true) {
            if (!stopped) {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(4) * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Customer customer = new Customer(id, building);
                building.enter(customer);
                customer.start();
                id++;
                if (building.isFull()) pause();
            }
            if (building.belowThreshold()) unpause();
        }
    }
}
