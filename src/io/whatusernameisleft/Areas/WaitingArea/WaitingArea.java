package io.whatusernameisleft.Areas.WaitingArea;

import io.whatusernameisleft.Customer.Customer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class WaitingArea extends Thread {
    private final String name;
    private final int MAX_QUEUE = 10;
    private final BlockingQueue<Customer> queue = new ArrayBlockingQueue<>(MAX_QUEUE, true);

    public WaitingArea(String name) {
        this.name = name;
    }


}
