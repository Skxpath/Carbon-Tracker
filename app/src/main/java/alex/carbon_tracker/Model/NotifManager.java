package alex.carbon_tracker.Model;

import java.util.ArrayList;

/**
 * Created by Alex on 4/3/2017.
 * <p>
 * Notification manager class to manage the different notifications that exist.
 */

public class NotifManager {

    private ArrayList<Notif> notificationArrayList = new ArrayList<>();
    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();

    private int totalJourneysToday = 0;
    private boolean lastUtilityBillEnteredWithin45Days = false;
    private boolean lastJourneyEnteredToday = false;

    public NotifManager() {
        generateAllNotifications();

    }

    public Notif getNotification() {

        updateFlags();

        for (Notif n : notificationArrayList) {
            System.out.println(n.getNotificationString());
            System.out.println(totalJourneysToday);
        }

        if (!lastUtilityBillEnteredWithin45Days) {
            return notificationArrayList.get(0);
        } else if (totalJourneysToday == 0) {
            return notificationArrayList.get(1);
        } else {
            return notificationArrayList.get(2);
        }

    }

    private void updateFlags() {

        carbonTrackerModel = CarbonTrackerModel.getInstance();

        totalJourneysToday = carbonTrackerModel.getJourneyManager().getTotalJourneysToday();
        lastJourneyEnteredToday = totalJourneysToday != 0;
        lastUtilityBillEnteredWithin45Days = carbonTrackerModel.getUtilityBillManager().getIfUtilityBillEnteredWithin45Days();

        notificationArrayList.get(2).setNotificationString("You have entered " + totalJourneysToday + " journeys today, would you like to enter more?");

    }

    private void generateAllNotifications() {

        generateNotification("You have not entered a utility bill in the last 1.5 months, please enter a new bill!", NotifEnum.UTILITY_TIPS);
        generateNotification("You have not entered a journey today, would you like to enter one?", NotifEnum.DEFAULT_TIPS);
        generateNotification("You have entered " + totalJourneysToday + " journeys today, would you like to enter more?", NotifEnum.JOURNEY_TIPS);
    }

    private void generateNotification(String notification, NotifEnum type) {
        Notif newNotification = new Notif(notification, type);
        notificationArrayList.add(newNotification);
    }

}
