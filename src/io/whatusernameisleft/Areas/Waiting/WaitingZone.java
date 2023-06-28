package io.whatusernameisleft.Areas.Waiting;

import io.whatusernameisleft.Customer.Customer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public abstract class WaitingZone {
    protected final String name;
    protected final BlockingQueue<Customer> queue;

    public WaitingZone(String name, int MAX_QUEUE) {
        this.name = name;
        queue = new ArrayBlockingQueue<>(MAX_QUEUE, true);
    }

    public BlockingQueue<Customer> getQueue() {
        return queue;
    }

    public void wait(Customer customer) {
        try {
            queue.put(customer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public boolean offer(Customer customer) {
        return queue.offer(customer);
    }

    public boolean isFull() {
        return queue.remainingCapacity() == 0;
    }
}
