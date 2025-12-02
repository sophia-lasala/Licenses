package Streaming;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main (String [] args){
        Scanner keyboard = new Scanner(System.in);
        String userInput;

        System.out.println("Importing CSV file...");
        ArrayList<Movie> movieList = new ArrayList<>();
        Data.movieList(movieList);
        System.out.println("\n Complete!");

        LinkedList<Movie> expirationList = new LinkedList<>();
        Expiration.expirationList(expirationList);

        System.out.println("\nWould you like to: (1) Access all data (2) Access all licenses (3) Review licenses that need " +
                "to be renewed soon (4) Access License IDs (5) Search for movies by Title (6) Access History (7) Exit");

        userInput = keyboard.next();

        while (!(userInput.equalsIgnoreCase("7"))){
            switch (userInput){
                case "1":
                    Data.printMovieList(movieList);
                    break;
                case "2":
                    Expiration.printExpirationList(expirationList);
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    break;
                case "6":
                    HistoryManager.printHistory();
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
