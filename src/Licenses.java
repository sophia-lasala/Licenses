package Streaming;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.time.LocalDate;

public class Licenses {
    private LinkedList<Movie> expirationList = new LinkedList<>();
    private Queue<Movie> pendingQueue = new LinkedList<>();

    public void expirationList(LinkedList<Movie> expirationList){
        Data data = new Data();
        ArrayList<Movie> movieList = new ArrayList<>();
        data.movieList(movieList);

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

        orgExpirationList(expirationList);
        printExpirationList(expirationList);

        buildPendingQueue(expirationList);
        printPendingQueue();
    }

    public void orgExpirationList(LinkedList<Movie> expirationList) {
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
    }

    public static void printExpirationList(LinkedList<Movie> expirationList) {
        System.out.println("Title | License ID | Expiration Date | Renewal Date | Days");
        for (Movie movie : expirationList) {
            System.out.println(movie.toExpirationString());
        }
    }

    // build queue of licenses expiring within 30 days
    public void buildPendingQueue(LinkedList<Movie> expirationList) {
        for (Movie movie : expirationList) {
            if (movie.getDays() <= 30) {
                pendingQueue.add(movie);
            }
        }
    }

    // print pending queue
    public void printPendingQueue() {
        System.out.println("\n--- Pending Licenses (Expiring within 30 days) ---");
        System.out.println("Title | License ID | Expiration Date | Renewal Date | Days");
        for (Movie movie : pendingQueue) {
            System.out.println(movie.toExpirationString());
        }
    }
}
