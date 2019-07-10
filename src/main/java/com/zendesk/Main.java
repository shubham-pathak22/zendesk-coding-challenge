package com.zendesk;


import com.zendesk.application.Console;
import com.zendesk.exception.TermNotPresentException;
import com.zendesk.service.SearchService;
import com.zendesk.service.SearchServiceImpl;
import com.zendesk.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        String user = FileUtils.getProperty("config.properties","user.json.path");
        String tickets = FileUtils.getProperty("config.properties","ticket.json.path");
        String org = FileUtils.getProperty("config.properties","organization.json.path");

        File userFile = StringUtils.isBlank(user) ? new File(Main.class.getResource("/users.json").getFile()) : new File(user);
        File ticketFile = StringUtils.isBlank(tickets)? new File(Main.class.getResource("/tickets.json").getFile()) : new File(tickets);
        File orgFile = StringUtils.isBlank(org) ? new File(Main.class.getResource("/organizations.json").getFile()) : new File(org);

        SearchService zendeskSearch = new SearchServiceImpl(userFile,ticketFile,orgFile);
        Console.displayWelcomeScreen();
        String choice;
        Scanner scan = new Scanner(System.in).useDelimiter("\n");;
        do {
            Console.displayMainOptions();
            choice = scan.next();
            switch (choice){
                case Console.SEARCH:
                    do{
                        Console.displaySearchOptions();
                        choice = scan.next();
                        switch (choice){
                            case Console.SEARCH_USERS:
                                 System.out.println("Enter search term");
                                 String term = scan.next();
                                 System.out.println("Enter search value");
                                 String value = scan.next();
                                try {
                                    Console.displayResults(zendeskSearch.searchByUser(term,value));
                                } catch (TermNotPresentException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case Console.SEARCH_TICKETS:
                                 System.out.println("Enter search term");
                                  term = scan.next();
                                 System.out.println("Enter search value");
                                  value = scan.next();
                                 try {
                                    Console.displayResults(zendeskSearch.searchByTicket(term,value));
                                 } catch (TermNotPresentException e) {
                                    System.out.println(e.getMessage());
                                 }
                                break;
                            case Console.SEARCH_ORGANIZATIONS:
                                 System.out.println("Enter search term");
                                  term = scan.next();
                                 System.out.println("Enter search value");
                                  value = scan.next();
                                 try {
                                     Console.displayResults(zendeskSearch.searchByOrganization(term,value));
                                 } catch (TermNotPresentException e) {
                                    System.out.println(e.getMessage());
                                 }
                                break;
                            case Console.EXIT:
                                break;
                            case Console.QUIT:
                                Console.displayClosingMessage();
                                System.exit(1);
                            default:System.out.println("Please enter a valid option.\n");
                                break;
                        }
                    }while(!choice.equals(Console.EXIT));
                    break;
                case Console.LIST_FIELDS:
                    Console.displaySearchableFields("Users" , zendeskSearch.getUserFields());
                    Console.displaySearchableFields("Tickets" ,zendeskSearch.getTicketFields());
                    Console.displaySearchableFields("Organizations" ,zendeskSearch.getOrganizationFields());
                    break;
                case Console.QUIT:
                    Console.displayClosingMessage();
                    System.exit(1);
                default:System.out.println("Please enter a valid option.\n");
                    break;
            }
        }while(!choice.equals(Console.QUIT));

    }
}
