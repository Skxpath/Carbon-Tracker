package alex.carbon_tracker.UI;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.VehicleManager;
import alex.carbon_tracker.R;

/*
* Welcome screen activity which loads the CSVReader data
* in the background while welcoming the user into
* the application. The buffer time is necessary
* to allow the program to function properly.
* */
public class WelcomeScreenActivity extends AppCompatActivity {

    private CarbonTrackerModel carbonTrackerModel;
    private VehicleManager vehicleManager;

    public static final int day = 1000 * 60 * 60 * 24; //Milliseconds in a day
    public boolean setNotificationTimerFlag = false;

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

        setupNotifications();

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

    //https://www.tutorialspoint.com/java/util/timer_schedule_period.htm
    //https://www.tutorialspoint.com/android/android_notifications.htm
    private void setupNotifications() {

        Intent intent = new Intent();
        PendingIntent pIntent = PendingIntent.getActivity(WelcomeScreenActivity.this, 0, intent, 0);
      Notification noti = new Notification.Builder(WelcomeScreenActivity.this)
                        .setSmallIcon(R.mipmap.carbontrackerlogo5)
                        .setContentTitle("Notifications Example")
                        .setContentText("This is a test notification")
              .setTicker("TickerTitle")
              .setContentIntent(pIntent).getNotification();

        noti.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(0,noti);

  /*      Intent notificationIntent = new Intent(this, MenuActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        ("ServiceCast") NotificationManager2 manager = (NotificationManager2) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());*/
    }



/*    private void NotificationTimer() {

        class NotificationTimer extends TimerTask {

            public void run() {
                CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
                carbonTrackerModel.getNotificationManager().getNotification();
            }

        }

        Timer timer = new Timer();
        Calendar date = Calendar.getInstance();

        date.set(Calendar.HOUR, 21);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        // Schedule to run every day at 9pm
        timer.schedule(
                new NotificationTimer(),
                date.getTime(),
                day);
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
