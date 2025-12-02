package Streaming;


import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        String userInput;

        System.out.println("Importing CSV file...");
        Licenses licenses = new Licenses();
        licenses.buildAllStructures();
        Tree movieTree = new Tree();
        movieTree.buildFromList(new ArrayList <>(licenses.getLicenseMap().values()));
        System.out.println("Complete!");

        System.out.println("\nWould you like to: (1) Access all data (2) Access all licenses (3) Review licenses that need " +
                "to be renewed soon (4) Access License IDs (5) Search for movies by Title (6) Access History (7) Exit");

        userInput = keyboard.next();

        while (!(userInput.equalsIgnoreCase("7"))) {
            switch (userInput) {
                case "1":
                    Data.printMovieList(new ArrayList<>(licenses.getLicenseMap().values()));
                    break;
                case "2":
                    Licenses.printExpirationList(licenses.getExpirationList());
                    break;
                case "3":
                    licenses.printPendingQueue();
                    break;
                case "4":
                    System.out.println("Enter License ID:");
                    String id = keyboard.next();
                    Movie m = licenses.findByLicenseID(id);
                    if (m != null){
                        System.out.println(m.toExpirationString());
                    }
                    else {
                        System.out.println("License not found.");
                    }
                    break;
                case "5":
                    System.out.println("Enter movie title:");
                    String title = keyboard.next();
                    Movie result = movieTree.search(title);
                    if (result != null) {
                        System.out.println(result.toExpirationString());
                    } else {
                        System.out.println("Movie not found.");
                    }
                    break;
                case "6":
                    HistoryManager.printHistory();
                    break;
                default:
                    System.out.println("Please type in a valid number response.");
                    break;
            }

            System.out.println("\nWould you like to: (1) Access all data (2) Access all licenses (3) Review licenses that need " +
                    "to be renewed soon (4) Access License IDs (5) Search for movies by Title (6) Access History (7) Exit");

            userInput = keyboard.next();
        }
    }
}
