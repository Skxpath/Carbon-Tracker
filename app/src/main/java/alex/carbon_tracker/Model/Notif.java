package alex.carbon_tracker.Model;

/**
 * Created by Alex on 4/3/2017.
 * <p>
 * Notif class to represent a notification
 */

public class Notif implements Comparable<Notif> {


    private int counter = 0;
    private String notificationString;
    private NotifEnum notificationType;

    public Notif(String notificationString, NotifEnum notificationType) {
        this.notificationString = notificationString;
        this.notificationType = notificationType;
    }

    //Counter to make list in ascending order
    @Override
    public int compareTo(Notif o) {
        if (counter == o.counter) {
            return 0;
        } else if (counter > o.counter) {
            return 1;
        } else {
            return -1;
        }
    }

    public String getNotificationString() {
        return notificationString;
    }

    public void setNotificationString(String notificationString) {
        this.notificationString = notificationString;
    }

    public NotifEnum getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotifEnum notificationType) {
        this.notificationType = notificationType;
    }
}
