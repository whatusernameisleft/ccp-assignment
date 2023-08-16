package io.whatusernameisleft.Areas.Waiting;

public abstract class WaitingZone {
    protected final String name;

    public WaitingZone(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
