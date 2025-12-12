package Streaming;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.time.LocalDate;


public class Expiration {
    private LinkedList<Movie> expirationList = new LinkedList<>();

    public static void expirationList(LinkedList<Movie> expirationList){
        ArrayList<Movie> movieList = new ArrayList<>();
        Data.movieList(movieList);

        String title, licenseID;
        LocalDate expirationDate, renewalDate;
        long days;

        for (int i = 0 ; i < movieList.size(); i++){
            Movie temp = movieList.get(i);
            title = temp.getTitle();
            licenseID = temp.getLicenseID();
            expirationDate = temp.getExpirationDate();
            renewalDate = temp.getRenewalDate();

            days = ChronoUnit.DAYS.between(renewalDate, expirationDate);

            temp = new Movie(title, licenseID, expirationDate, renewalDate, days);
            expirationList.add(temp);

        }
        HistoryManager.currentHistory("Expiration", "formed expiration list");
        orgExpirationList(expirationList);
        printExpirationList(expirationList);
    }

    public static void orgExpirationList(LinkedList<Movie> expirationList) {
        int n = expirationList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Movie current = expirationList.get(j);
                Movie next = expirationList.get(j + 1);

                long x = current.getDays(), y = next.getDays();
                if (x > y) {
                    expirationList.set(j, next);
                    expirationList.set(j + 1, current);
                }
            }
        }
        HistoryManager.currentHistory("Expiration", "organized expiration list");
    }

    public static void printExpirationList(LinkedList<Movie> expirationList) {
        System.out.println("Title | License ID | Expiration Date | Renewal Date | Days");

        for (int i = 0; i < expirationList.size(); i++) {
            Movie movie = expirationList.get(i);
            System.out.println(movie.toExpirationString());
        }

        HistoryManager.currentHistory("Expiration", "printed expiration list");
    }
}

