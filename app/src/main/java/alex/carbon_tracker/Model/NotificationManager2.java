package alex.carbon_tracker.Model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Aria on 4/3/2017.
 */

public class NotificationManager2 {

    private ArrayList<Notification2> notificationArrayList = new ArrayList<>();
    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();

    private int totalJourneysToday = 0;
    private boolean lastUtilityBillEnteredWithin45Days = false;
    private boolean lastJourneyEnteredToday = false;

    public NotificationManager2() {
        generateAllNotifications();
        Collections.sort(notificationArrayList);
    }

    public String getNotification() {

        updateFlags();

        if (!lastUtilityBillEnteredWithin45Days) {
            return "You have not entered a utility bill in the last 1.5 months, please enter a new bill!";
        } else
        if (!lastJourneyEnteredToday) {
            return "You have not entered a journey today, would you like to enter one?";
        } else {
            return "You have entered " + totalJourneysToday + " journeys today, would you like to enter more?";
        }

    }

    private void updateFlags() {

totalJourneysToday = carbonTrackerModel.getJourneyManager().getTotalJourneysToday();

        lastJourneyEnteredToday = totalJourneysToday != 0;

        lastUtilityBillEnteredWithin45Days = carbonTrackerModel.getUtilityBillManager().getIfUtilityBillEnteredWithin45Days();

    }

    private void generateAllNotifications() {
        notificationArrayList.clear();

        generateNotification("You have not entered a journey today, would you like to enter one?", NotificationEnum.JOURNEY_TIPS);
        generateNotification("You have not entered a utility bill in the last 1.5 months, please enter a new bill!", NotificationEnum.UTILITY_TIPS);
        generateNotification("You have entered " + totalJourneysToday + " journeys today, would you like to enter more?", NotificationEnum.JOURNEY_TIPS);
    }

    private void generateNotification(String notification, NotificationEnum type) {
        Notification2 newNotification = new Notification2(notification, type);
        notificationArrayList.add(newNotification);
    }

}
