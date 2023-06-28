package io.whatusernameisleft;

import io.whatusernameisleft.Areas.Tickets.*;
import io.whatusernameisleft.Customer.Customer;
import io.whatusernameisleft.Customer.CustomerGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

    public static void main(String[] args) {
        List<String> sellerNames = new ArrayList<>(Arrays.asList("Ticket Counter", "Ticket Booth 1", "Ticket Booth 2"));
        SellerManager sellerManager = new SellerManager(sellerNames);
        sellerManager.createSellers();

        CustomerGenerator cg = new CustomerGenerator(sellerManager);
        cg.start();

    }
}
