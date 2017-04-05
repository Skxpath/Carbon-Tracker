package alex.carbon_tracker.Model;

/**
 * Created by Aria on 4/3/2017.
 */

public class Notification2 implements Comparable<Notification2> {


    private int counter = 0;
    private String notification;
    private NotificationEnum type;

    public Notification2(String notification, NotificationEnum type) {
   this.notification = notification;
        this.type = type;
    }

    //Counter to make list in ascending order
    @Override
    public int compareTo(Notification2 o) {
        if (counter == o.counter) {
            return 0;
        } else if (counter > o.counter) {
            return 1;
        } else {
            return -1;
        }
    }
}
