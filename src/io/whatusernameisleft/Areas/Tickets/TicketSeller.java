package io.whatusernameisleft.Areas.Tickets;

import io.whatusernameisleft.Customer.Customer;
import io.whatusernameisleft.TBT;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TicketSeller extends Thread {
    protected final String name;
    protected volatile boolean open = true;
    protected final int MAX_QUEUE = 3;
//    private final AtomicInteger queueCount = new AtomicInteger(0);
    protected final BlockingQueue<Customer> queue = new ArrayBlockingQueue<>(MAX_QUEUE);

    public TicketSeller(String name) {
        this.name = name;
    }

    public BlockingQueue<Customer> getQueue() {
        return queue;
    }

    public int getQueueCount() {
        return queue.size();
    }

    public String getSellerName() {
        return name;
    }

    private void sellTicket() {
        Customer c = null;
        try {
            while (!queue.isEmpty()) {
                c = queue.take();
                Thread.sleep(500);
//                Thread.sleep(new Random().nextInt(3) * 1000);
                c.buyTicket();
            }
        } catch (Exception e) {
            System.out.println(TBT.ANSI_RED + c.getCustomerName() + " is null" + TBT.ANSI_RESET);
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (open) {
            sellTicket();
        }
    }

    public void close() {
        open = false;
    }
}
