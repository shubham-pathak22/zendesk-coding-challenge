package com.zendesk.application;

import java.util.Set;

public class Console {

    public static final String SEARCH = "1";
    public static final String LIST_FIELDS = "2";

    public static final String SEARCH_USERS = "1";
    public static final String SEARCH_TICKETS = "2";
    public static final String SEARCH_ORGANIZATIONS = "3";

    public static final String EXIT = "exit";
    public static final String QUIT = "quit";

    public static void displayWelcomeScreen() {
        StringBuilder s = new StringBuilder();
        s.append("Welcome to Zendesk Search\n")
                .append("Type 'quit' to exit at any time,Press 'Enter' to continue to Zendesk Search");
        System.out.println(s.toString());

    }

    public static void displayMainOptions() {
        StringBuilder s = new StringBuilder();
        s.append("Select an options\n")
                .append("\t\t* Press " + SEARCH + " to search Zendesk\n")
                .append("\t\t* Press " + LIST_FIELDS + " to view a list of searchable fields\n")
                .append("\t\t* Type '" + QUIT + "' to exit\n\n");
        System.out.println(s.toString());

    }

    public static void displaySearchOptions() {
        StringBuilder s = new StringBuilder();
        s.append("Select an option\n")
                .append("\t\t* Press " + SEARCH_USERS + " to search Users\n")
                .append("\t\t* Press " + SEARCH_TICKETS + " to search Tickets\n")
                .append("\t\t* Press " + SEARCH_ORGANIZATIONS + " to search Organizations\n")
                .append("\t\t* Type '" + EXIT + "' to go back to the main menu or '" + QUIT + "' to quit\n\n");
        System.out.println(s.toString());

    }

    public static void displaySearchableFields(String name, Set fields) {
        StringBuilder s = new StringBuilder();
        s.append("\n----------------------------------------\n");
        s.append("Search " + name + " with \n");
        fields.stream().forEach(u -> s.append(u).append("\n"));
        System.out.println(s.toString());
    }

    public static void displayResults(Set results) {
        if (results.size() > 0) {
            results.stream().forEach(r ->
                System.out.println(r + "\n-----------------------------------------\n"));
            System.out.println("Total records: " + results.size() + "\n");
        } else {
            System.out.println("No results found");
        }
    }

    public static void displayClosingMessage() {
        System.out.println("Thank you for using Zendesk Search ");
    }
}
