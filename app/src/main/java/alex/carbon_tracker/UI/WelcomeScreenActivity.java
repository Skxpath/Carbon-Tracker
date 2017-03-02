package alex.carbon_tracker.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import alex.carbon_tracker.R;

public class WelcomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        ImageView welcomeImg = (ImageView) findViewById(R.id.welcomeImage);
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.welcome_anim);
        welcomeImg.startAnimation(myFadeInAnimation);

        myFadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                startActivity(new Intent(WelcomeScreenActivity.this, MainActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}