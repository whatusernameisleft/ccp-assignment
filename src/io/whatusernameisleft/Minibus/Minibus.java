package io.whatusernameisleft.Minibus;

import io.whatusernameisleft.Customer.Customer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Minibus extends Thread {
    private final String name;
    private volatile boolean arrived = false;
    private final int MAX_SEATS = 10;
    private final BlockingQueue<Customer> seats = new ArrayBlockingQueue<>(MAX_SEATS);

    public Minibus(String name) {
        this.name = name;
    }

    public boolean board(Customer customer) {
        return seats.offer(customer);
    }

    private void clear() {
        seats.clear();
    }

    private void arrive() {
        arrived = true;
    }

    private void leave() {
        arrived = false;
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (this) {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(10, 21) * 1000);
                    arrive();
                    this.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            leave();
            clear();
        }
    }
}
