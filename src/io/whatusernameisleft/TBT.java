package io.whatusernameisleft;

import io.whatusernameisleft.Areas.Tickets.SellerManager;
import io.whatusernameisleft.Areas.Waiting.Foyer.FoyerManager;
import io.whatusernameisleft.Areas.Waiting.WaitingArea.WaitingAreaManager;
import io.whatusernameisleft.Customer.CustomerGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TBT {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BOLD = "\u001B[1m";
    public static final String ANSI_FRAMED = "\u001B[51m";

    public static void main(String[] args) {
        AtomicInteger customerCounter = new AtomicInteger(0);
        FoyerManager foyerManager = new FoyerManager();

        List<String> machineNames = new ArrayList<>(Arrays.asList("Ticket Machine"));
        List<String> boothNames = new ArrayList<>(Arrays.asList("Ticket Booth 1", "Ticket Booth 2"));
        SellerManager sellerManager = new SellerManager(machineNames, boothNames, foyerManager);

        WaitingAreaManager waitingAreaManager = new WaitingAreaManager();

        CustomerGenerator cg = new CustomerGenerator(customerCounter, sellerManager, waitingAreaManager, foyerManager);
        cg.start();

    }
}
