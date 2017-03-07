package alex.carbon_tracker.UI;
// do not do anything with this activt
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import alex.carbon_tracker.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, MenuActivity.class);
    }
}
