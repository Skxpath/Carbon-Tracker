package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.Model.Vehicle;
import alex.carbon_tracker.R;

public class SelectTransportationMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation_modey);
        setupAddCarButton();
        carListView();
    }
    private void setupAddCarButton() {
        Button btn = (Button) findViewById(R.id.AddCarButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddCarActivity.makeIntent(SelectTransportationMode.this);
                startActivity(intent);
            }
        });
    }

    // shows a list view of all the current cars
    private void carListView() {
        //dummy data
        String[] carList = {"car 1","car 2","car 3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.jouney_list,carList);
        ListView list =(ListView) findViewById(R.id.carListView);
        list.setAdapter(adapter);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectTransportationMode.class);
    }
}
