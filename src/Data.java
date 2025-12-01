package Streaming;

import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

public class Data
{
    private ArrayList<Movie> movieList = new ArrayList<>();

    public static void movieList(ArrayList <Movie> movieList) {
        String fileName = "Movie Database.csv";
        PrintWriter outputStream = null;
        Scanner inputStream = null;
        Scanner keyboard = new Scanner(System.in);
        String title, licenseID, line;
        long views;
        double cost;
        LocalDate expirationDate, renewalDate;

        try {
            inputStream = new Scanner(new File(fileName));
            line = inputStream.nextLine();
            while (inputStream.hasNextLine()) {
                line = inputStream.nextLine();
                String[] ary = line.split(",");
                title = ary[0];
                licenseID = ary[1];
                cost = Double.parseDouble(ary[2]);
                expirationDate = LocalDate.parse(ary[3]);
                renewalDate = LocalDate.parse(ary[4]);
                views = Long.parseLong(ary[5]);

                movieList.add(new Movie(title, licenseID, views, cost, expirationDate, renewalDate));
            }
            inputStream.close();
            HistoryManager.currentHistory("Data", "formed movie list");
            //printMovieList(movieList); //for testing
        } catch (FileNotFoundException e) {
            System.out.println("Error opening the file " +
                    fileName);
            System.exit(0);
        }
    }

    public static void printMovieList(ArrayList<Movie> movieList) {
        System.out.println("Here is your list: ");
        System.out.println("Title | License ID | Cost | Expiration Date | Renewal Date | Views\n");
        for (int i = 0; i < movieList.size(); i++) {
            System.out.println((i+1) + " " + movieList.get(i));
        }
        HistoryManager.currentHistory("Data", "printed movie list");
    }
}
