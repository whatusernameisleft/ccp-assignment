package io.whatusernameisleft.Areas.Tickets.TicketSeller;

import io.whatusernameisleft.Formatting;

import java.util.concurrent.ThreadLocalRandom;

public class TicketPersonnel extends Thread {
    private final TicketBooth seller;
    private volatile boolean onBreak = false;

    public TicketPersonnel(TicketBooth seller) {
        this.seller = seller;
    }

    private void goOnBreak() throws InterruptedException {
        onBreak = true;
        seller.close();
        System.out.println(Formatting.ANSI_BOLD + Formatting.ANSI_FRAMED + Formatting.ANSI_RED + seller.getName() + " Personnel has gone for a toilet break." + Formatting.ANSI_RESET);
        Thread.sleep(ThreadLocalRandom.current().nextInt(5, 10) * 1000);
        finishBreak();
    }

    private void finishBreak() {
        System.out.println(Formatting.ANSI_BOLD + Formatting.ANSI_FRAMED + Formatting.ANSI_GREEN + seller.getName() + " Personnel returned from the toilet break." + Formatting.ANSI_RESET);
        onBreak = false;
        seller.open();
    }

    @Override
    public void run() {
        while (!onBreak) {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(7) * 1000);
                seller.sellTicket();

                if (ThreadLocalRandom.current().nextDouble() < 0.1) goOnBreak();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
