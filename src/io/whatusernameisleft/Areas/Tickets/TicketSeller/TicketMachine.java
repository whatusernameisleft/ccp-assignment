package io.whatusernameisleft.Areas.Tickets.TicketSeller;

import io.whatusernameisleft.Building;
import io.whatusernameisleft.Formatting;

import java.util.concurrent.ThreadLocalRandom;

public class TicketMachine extends TicketSeller implements Runnable {
    private volatile boolean broken = false;

    public TicketMachine(String name, Building building) {
        super(name, building);
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.setName(name);
        thread.start();
    }

    private void breakdown() throws InterruptedException {
        broken = true;
        close();
        System.out.println(Formatting.ANSI_BOLD + Formatting.ANSI_FRAMED + Formatting.ANSI_RED + " " + getName() + " has broken down. " + Formatting.ANSI_RESET);
        redirectQueue();
        Thread.sleep(ThreadLocalRandom.current().nextInt(7, 10) * 1000);
        repair();
    }

    private void repair() {
        System.out.println(Formatting.ANSI_BOLD + Formatting.ANSI_FRAMED + Formatting.ANSI_GREEN + " " + getName() + " has been repaired. " + Formatting.ANSI_RESET);
        broken = false;
        open();
    }

    @Override
    public void run() {
        while (open.get() && !building.isClosed()) {
            while (!broken) {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(4) * 1000);
                    if (building.isClosed()) return;
                    sellTicket();

                    if (ThreadLocalRandom.current().nextDouble() < 0.4) breakdown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
