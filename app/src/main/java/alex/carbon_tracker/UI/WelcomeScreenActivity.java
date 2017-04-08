package alex.carbon_tracker.UI;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.Notif;
import alex.carbon_tracker.Model.VehicleManager;
import alex.carbon_tracker.R;

/*
* Welcome screen activity which loads the CSVReader data
* in the background while welcoming the user into
* the application. The buffer time is necessary
* to allow the program to function properly.
*
* Also manages notification functionality.
*
* */
public class WelcomeScreenActivity extends AppCompatActivity {

    private CarbonTrackerModel carbonTrackerModel;
    private VehicleManager vehicleManager;

    public static final int day = 1000 * 60 * 60 * 24; //Milliseconds in a day

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        CarbonTrackerModel.getSavedModel(this);
        carbonTrackerModel = CarbonTrackerModel.getInstance();
        vehicleManager = carbonTrackerModel.getVehicleManager();
        setContentView(R.layout.activity_welcome_screen);
        vehicleManager.writeDataToList(this, R.raw.vehicles);
        ImageView welcomeImg = (ImageView) findViewById(R.id.smokeImgView);
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.welcome_anim);
        welcomeImg.startAnimation(myFadeInAnimation);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.carbontrackerlogo5);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        displayNotifications();

        myFadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                startActivity(new Intent(WelcomeScreenActivity.this, MenuActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        welcomeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeScreenActivity.this, JourneyListActivity.class));
                startActivity(new Intent(WelcomeScreenActivity.this, MenuActivity.class));
                finish();
            }
        });
    }

    private void setupNotifications() {

        Intent intent;

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();

        Notif currentNotification = carbonTrackerModel.getNotificationManager().getNotification();

        int notificationLogo = R.mipmap.carbontrackerlogo5;

        String notificationHeaderString = getString(R.string.carbonTracker);
        String notificationMessage = currentNotification.getNotificationString();

        switch (currentNotification.getNotificationType()) {

            case JOURNEY_TIPS:
                intent = new Intent(this, SelectDateActivity.class);
                makeNotification(intent, nm, notificationLogo, notificationHeaderString, notificationMessage);
                break;
            case UTILITY_TIPS:
                intent = new Intent(this, AddUtilityBillActivity.class);
                makeNotification(intent, nm, notificationLogo, notificationHeaderString, notificationMessage);
                break;
            case DEFAULT_TIPS:
                intent = new Intent(this, SelectDateActivity.class);
                makeNotification(intent, nm, notificationLogo, notificationHeaderString, notificationMessage);
                break;
        }
    }

    private void makeNotification(Intent intent, NotificationManager nm, int notificationLogo, String notificationHeaderString, String notificationMessage) {
        PendingIntent pendingIntent;
        NotificationCompat.Builder notificationBuilder;
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(notificationLogo)
                .setContentTitle(notificationHeaderString)
                .setContentText(notificationMessage)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);


        nm.notify(0, notificationBuilder.build());
    }


    private void displayNotifications() {

        Timer timer = setupNotificationTimer();
        setupJourneyCounterResetter(timer);
    }

    private void setupJourneyCounterResetter(Timer timer) {
        class JourneysTodayCounterResetter extends TimerTask {

            public void run() {
                CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
                carbonTrackerModel.getJourneyManager().setTotalJourneysToday(0);
            }
        }

        Calendar resetDate = Calendar.getInstance();

        resetDate.set(Calendar.HOUR_OF_DAY, 0);
        resetDate.set(Calendar.MINUTE, 0);
        resetDate.add(Calendar.DAY_OF_MONTH, 1);

        timer.schedule(
                new JourneysTodayCounterResetter(),
                resetDate.getTime(),
                day);
    }

    @NonNull
    private Timer setupNotificationTimer() {
        class NotificationTimer extends TimerTask {

            public void run() {
                setupNotifications();
            }

        }

        Timer timer = new Timer();
        Calendar date = Calendar.getInstance();

        date.set(Calendar.HOUR_OF_DAY, 21);
        date.set(Calendar.MINUTE, 0);

        // Schedule to run every day at 9pm
        timer.schedule(
                new NotificationTimer(),
                date.getTime(),
                day);
        return timer;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
