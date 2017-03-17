package alex.carbon_tracker.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private VehicleManager vehicleManager = carbonTrackerModel.getVehicleManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        vehicleManager.writeDataToList(this, R.raw.vehicles);
        ImageView welcomeImg = (ImageView) findViewById(R.id.smokeImgView);
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.welcome_anim);
        welcomeImg.startAnimation(myFadeInAnimation);

        myFadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                startActivity(new Intent(WelcomeScreenActivity.this, JourneyListActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        welcomeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ppop", "Clicked!!!!");
                startActivity(new Intent(WelcomeScreenActivity.this, JourneyListActivity.class));
                finish();
            }
        });
    }
}