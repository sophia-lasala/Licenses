import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Movie {
    private String title, licenseID;
    private long views;
    private double cost;
    private LocalDate expirationDate, renewalDate;

    public Movie() {
        title = "No title provided";
        licenseID = "No license provided";
        views = 0;
        cost = 0;
        expirationDate = LocalDate.of(2000, 1, 1);
        renewalDate = LocalDate.of(2000, 1, 1);
    }

    public Movie(String title, String licenseID, long views, double cost, LocalDate expirationDate, LocalDate renewalDate) {
        this.title = title;
        this.licenseID = licenseID;
        this.views = views;
        this.cost = cost;
        this.expirationDate = expirationDate;
        this.renewalDate = renewalDate;
    }

    public String toString() {
        return title + " | " + licenseID + " | $" + cost + " | " + expirationDate + " | " + renewalDate + " | " + views + " views";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLicenseID() {
        return licenseID;
    }

    public void setLicenseID(String licenseID) {
        this.licenseID = licenseID;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDate getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(LocalDate renewalDate) {
        this.renewalDate = renewalDate;
    }
}
