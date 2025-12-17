package Streaming;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import static Streaming.Data.currentHistory;

public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        String userInput;
        Data data = new Data ();
        TitleTree titleTree = new TitleTree();
        CostTree costTree = new CostTree();
        ViewsTree viewsTree = new ViewsTree();

        System.out.println("Importing CSV file...");
        data.buildAllStructures();
        titleTree.buildFromList(data.getMovieList());
        costTree.buildFromList(data.getMovieList());
        viewsTree.buildFromList(data.getMovieList());
        ArrayList<Movie> movieList = data.getMovieList();
        LinkedList<Movie> expirationList = data.getExpirationList();
        Queue<Movie> pendingQueue = data.getPendingQueue();

        System.out.println("Complete!");

        System.out.println("\nWould you like to: (1) Access all data (2) Access all licenses (3) Review licenses that need " +
                "to be renewed soon \n(4) Access License IDs (5) Search for a Title (6) Organize Movies " +
                "(7) Access History (8) Exit");

        userInput = keyboard.next();

        while (!(userInput.equalsIgnoreCase("8"))) {
            switch (userInput) {
                case "1":
                    data.printMovieList(movieList);
                    break;
                case "2":
                    data.printExpirationList(expirationList);
                    break;
                case "3":
                    data.printPendingQueue();
                    System.out.println("Would you like to renew any licenses on this list? (1) Yes (2) No");
                    userInput = keyboard.next();
                    while (userInput.equalsIgnoreCase("1")){
                        LocalDate expirationDate, renewalDate;
                        Movie firstMovie = pendingQueue.peek();
                        System.out.println(firstMovie);
                        System.out.println("Would you like to renew this license? (1) Yes (2) No");
                        userInput = keyboard.next();
                        if (userInput.equalsIgnoreCase("1")){
                            LocalDate newRenewalDate = LocalDate.now();

                            System.out.println("What is the new expiration date? (MM/dd/yyyy)");
                            String expirationInput = keyboard.next();
                            LocalDate newExpirationDate = LocalDate.parse(expirationInput, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

                            if (firstMovie != null) {
                                String licenseID = firstMovie.getLicenseID();
                                data.renewQueue(licenseID, newRenewalDate, newExpirationDate);
                            }
                        }
                    }

                    System.out.println("Here is the new list:");
                    data.printPendingQueue();
                    break;
                case "4":
                    System.out.println("Search by: (1) License ID  (2) Mapped ID");
                    userInput = keyboard.next();

                    if (userInput.equals("1")) {
                        System.out.println("Enter License ID:");
                        String id = keyboard.next();
                        Movie m = data.findByLicenseID(id);
                        if (m != null) {
                            System.out.println("Mapped ID: " + m.getMappedID());
                        } else {
                            System.out.println("Mapped ID not found.");
                        }
                    } else if (userInput.equals("2")) {
                        System.out.println("Enter Mapped ID (number):");
                        int mappedId = keyboard.nextInt();
                        Movie m = data.getMovieByNumber(mappedId);
                        if (m != null) {
                            System.out.println("License ID: " + m.getLicenseID());
                        } else {
                            System.out.println("License ID not found.");
                        }
                    } else {
                        System.out.println("Invalid choice.");
                    }
                    break;
                case "5":
                    System.out.println("Enter movie title:");
                    keyboard.nextLine();
                    String title = keyboard.nextLine();
                    Movie result = titleTree.search(title);
                    if (result != null) {
                        System.out.println(result.toExpirationString());
                    } else {
                        System.out.println("Movie not found.");
                    }
                    break;
                case "6":
                    System.out.println("What would you like to order based on? (1) Title (2) Cost (3) Views");
                    userInput = keyboard.next();
                    if (userInput.equalsIgnoreCase("1")){
                        titleTree.printInOrder();
                        currentHistory("Title Tree", "organized list in alphabetical order");
                    }
                    else if (userInput.equalsIgnoreCase("2")){
                        costTree.printInOrder();
                        currentHistory("Cost Tree", "organized list in cost order");
                    }
                    else if (userInput.equalsIgnoreCase("3")){
                        viewsTree.printInOrder();
                        currentHistory("Views Tree", "organized list in views order");
                    }
                    else{
                        System.out.println("Invalid Choice");
                    }
                    break;
                case "7":
                    Data.printHistory();
                    break;
                default:
                    System.out.println("Please type in a valid number response.");
                    break;
            }

            System.out.println("\nWould you like to: (1) Access all data (2) Access all licenses (3) Review licenses that need " +
                    "to be renewed soon \n(4) Access License IDs (5) Search for a Title (6) Organize Movies " +
                    "(7) Access History (8) Exit");

            userInput = keyboard.next();
        }
    }
}
