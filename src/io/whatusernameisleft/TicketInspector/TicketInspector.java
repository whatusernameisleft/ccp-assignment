package io.whatusernameisleft.TicketInspector;

import io.whatusernameisleft.Areas.Waiting.WaitingArea.WaitingArea;
import io.whatusernameisleft.Areas.Waiting.WaitingArea.WaitingAreaManager;
import io.whatusernameisleft.Customer.Customer;
import io.whatusernameisleft.Formatting;
import io.whatusernameisleft.Minibus.Minibus;
import io.whatusernameisleft.Minibus.MinibusManager;

import java.util.concurrent.ThreadLocalRandom;

public class TicketInspector extends Thread {
    private final WaitingAreaManager waitingAreaManager;
    private final MinibusManager minibusManager;

    public TicketInspector(String name, WaitingAreaManager waitingAreaManager, MinibusManager minibusManager) {
        setName(name);
        this.waitingAreaManager = waitingAreaManager;
        this.minibusManager = minibusManager;
        start();
    }

    @Override
    public void run() {
        while (true) {
            Minibus minibus = minibusManager.getMinibus();
            if (minibus == null) continue;
            synchronized (minibus) {
                WaitingArea waitingArea = waitingAreaManager.getWaitingArea(minibus.getTicket());
                if (waitingArea.isEmpty()) {
                    minibusManager.park(minibus);
                    continue;
                }
                while (!minibus.isFull() && !waitingArea.isEmpty()) {
                    try {
                        Thread.sleep(ThreadLocalRandom.current().nextInt(1, 4) * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Customer customer = waitingArea.getCustomer();
                    System.out.println(Formatting.ANSI_CYAN + Formatting.ANSI_ITALIC + getName() + " is checking " + customer.getName() + "'s ticket." + Formatting.ANSI_RESET);
                    if (customer.isPassenger()) minibus.board(customer);
                }
                minibus.notify();
            }
        }
    }
}
