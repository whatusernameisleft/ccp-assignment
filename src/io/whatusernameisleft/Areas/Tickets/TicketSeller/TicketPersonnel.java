package io.whatusernameisleft.Areas.Tickets.TicketSeller;

import io.whatusernameisleft.Building;
import io.whatusernameisleft.Formatting;

import java.util.concurrent.ThreadLocalRandom;

public class TicketPersonnel extends Thread {
    private final TicketSeller seller;
    private final Building building;
    private volatile boolean onBreak = false;

    public TicketPersonnel(TicketSeller seller, Building building) {
        this.seller = seller;
        this.building = building;
        setName(seller.getName() + " Personnel");
    }

    private void goOnBreak() throws InterruptedException {
        onBreak = true;
        seller.close();
        System.out.println(Formatting.ANSI_BOLD + Formatting.ANSI_FRAMED + Formatting.ANSI_RED + " " + seller.getName() + " Personnel has gone for a toilet break. " + Formatting.ANSI_RESET);
        seller.redirectQueue();
        Thread.sleep(ThreadLocalRandom.current().nextInt(5, 10) * 1000);
        finishBreak();
    }

    private void finishBreak() {
        System.out.println(Formatting.ANSI_BOLD + Formatting.ANSI_FRAMED + Formatting.ANSI_GREEN + " " + seller.getName() + " Personnel returned from the toilet break. " + Formatting.ANSI_RESET);
        onBreak = false;
        seller.open();
    }

    public void leave() {
        onBreak = true;
    }

    @Override
    public void run() {
        while (!onBreak && !building.isClosed()) {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(2, 7) * 1000);
                if (building.isClosed()) return;
                seller.sellTicket();

                if (ThreadLocalRandom.current().nextDouble() < 0.1) goOnBreak();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
