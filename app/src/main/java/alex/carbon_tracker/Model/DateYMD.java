package alex.carbon_tracker.Model;

/**
 * Holds a year, month, and day as ints
 */
public class DateYMD {
    private int year;
    private int month;
    private int day;
    JourneyManager journeys = new JourneyManager();

    public JourneyManager getJourneys() {
        return journeys;
    }

    public DateYMD(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Journey getJourney(int i) {
        return journeys.getJourney(i);
    }

    public void deleteJourney(int i) {
        journeys.delete(i);
    }

    public void setJourney(Journey journey) {
        journeys.add(journey);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
