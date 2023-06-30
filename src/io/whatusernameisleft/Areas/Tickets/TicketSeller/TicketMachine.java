package io.whatusernameisleft.Areas.Tickets.TicketSeller;

import io.whatusernameisleft.Areas.Waiting.Foyer.FoyerManager;
import io.whatusernameisleft.TBT;

import java.util.concurrent.ThreadLocalRandom;

public class TicketMachine extends TicketSeller implements Runnable {
    private Thread thread;
    private volatile boolean broken = false;

    public TicketMachine(String name, FoyerManager foyerManager) {
        super(name, foyerManager);
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    private void breakdown() throws InterruptedException {
        broken = true;
        close();
        System.out.println(TBT.ANSI_BOLD + TBT.ANSI_FRAMED + TBT.ANSI_RED + getName() + " has broken down." + TBT.ANSI_RESET);
        Thread.sleep(ThreadLocalRandom.current().nextInt(7, 10) * 1000);
        repair();
    }

    private void repair() {
        System.out.println(TBT.ANSI_BOLD + TBT.ANSI_FRAMED + TBT.ANSI_GREEN + getName() + " has been repaired." + TBT.ANSI_RESET);
        broken = false;
        open();
    }

    @Override
    public void run() {
        while (!broken) {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(4) * 1000);
                sellTicket();

                if (ThreadLocalRandom.current().nextDouble() < 0.4) breakdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
