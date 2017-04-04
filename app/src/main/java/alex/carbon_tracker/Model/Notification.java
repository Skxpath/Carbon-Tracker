package alex.carbon_tracker.Model;

/**
 * Created by Aria on 4/3/2017.
 */

public class Notification implements Comparable<Notification> {


    private int counter = 0;
    private String notification;
    private NotificationEnum type;

    public Notification(String notification, NotificationEnum type) {
   this.notification = notification;
        this.type = type;
    }

    //Counter to make list in ascending order
    @Override
    public int compareTo(Notification o) {
        if (counter == o.counter) {
            return 0;
        } else if (counter > o.counter) {
            return 1;
        } else {
            return -1;
        }
    }
}
