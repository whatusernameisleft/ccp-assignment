package io.whatusernameisleft.Areas.Waiting.Foyer;

import io.whatusernameisleft.Customer.CustomerType;

import java.util.HashMap;
import java.util.Map;

public class FoyerManager {
    private final Map<CustomerType, Foyer> foyerMap = new HashMap<>();

    public FoyerManager() {
        createFoyers();
    }

    private void createFoyers() {
        for (CustomerType customerType : CustomerType.values()) {
            Foyer foyer = new Foyer("Terminal Foyer");
            Thread foyerThread = new Thread(foyer);
            foyerMap.put(customerType, foyer);
            foyerThread.start();
        }
    }

    public Foyer getFoyer(CustomerType customerType) {
        return foyerMap.get(customerType);
    }
}
