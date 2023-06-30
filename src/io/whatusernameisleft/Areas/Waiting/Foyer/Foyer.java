package io.whatusernameisleft.Areas.Waiting.Foyer;

import io.whatusernameisleft.Areas.Waiting.WaitingZone;

public class Foyer extends WaitingZone implements Runnable {
    private Thread thread;

    public Foyer(String name) {
        super(name, 15);
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                queue.take().tryAgain();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
