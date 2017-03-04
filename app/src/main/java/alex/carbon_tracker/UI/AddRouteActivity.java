package alex.carbon_tracker.UI;
/*
* after you get all the information on route, call finish which will take you back to the select route activity.
* read the usecases to see more detail.
*/


import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.Route;
import alex.carbon_tracker.Model.RouteManager;
import alex.carbon_tracker.R;

public class AddRouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);
        //submit button
        setupSubmitBtn();
    }

    private void setupSubmitBtn() {
        Button submitBtn = (Button) findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CarbonTrackerModel model = CarbonTrackerModel.getInstance();
                RouteManager routManager = model.getRouteManager();

                int cityDistance = 0;
                int highwayDistance = 0;

                EditText cityDistEditText = (EditText) findViewById(R.id.cityDistanceEditText);
                cityDistance = Integer.parseInt(cityDistEditText.getText().toString());

                EditText highwayDistEditText = (EditText) findViewById(R.id.highwayDistanceEditText);
                highwayDistance = Integer.parseInt(cityDistEditText.getText().toString());

                Route route = new Route(cityDistance, highwayDistance);

                routManager.addRoute(route);

                Intent intent = SelectRouteActivity.makeIntent(AddRouteActivity.this);
                startActivity(intent);
                finish();
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddRouteActivity.class);
    }
}
