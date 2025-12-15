package Streaming;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        String userInput;
        Data data = new Data ();
        TitleTree titleTree = new TitleTree();

        System.out.println("Importing CSV file...");
        data.buildAllStructures();
        ArrayList<Movie> movieList = data.getMovieList();
        LinkedList<Movie> expirationList = data.getExpirationList();
        Queue<Movie> pendingQueue = data.getPendingQueue();

        System.out.println("Complete!");

        System.out.println("\nWould you like to: (1) Access all data (2) Access all licenses (3) Review licenses that need " +
                "to be renewed soon (4) Access License IDs (5) Format Data (6) Access History (7) Exit");

        userInput = keyboard.next();

        while (!(userInput.equalsIgnoreCase("7"))) {
            switch (userInput) {
                case "1":
                    data.printMovieList(movieList);
                    break;
                case "2":
                    data.printExpirationList(expirationList);
                    break;
                case "3":
                    data.printPendingQueue();
                    break;
                case "4":
                    System.out.println("Enter License ID:");
                    String id = keyboard.next();
                    Movie m = data.findByLicenseID(id);
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
                    Movie result = titleTree.search(title);
                    if (result != null) {
                        System.out.println(result.toExpirationString());
                    } else {
                        System.out.println("Movie not found.");
                    }
                    break;
                case "6":
                    Data.printHistory();
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
