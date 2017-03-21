package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.SaveData;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.DateYMD;
import alex.carbon_tracker.Model.Journey;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.R;

public class DateListActivity extends AppCompatActivity {

    public static final String SELECTED_YEAR = "Selected Year";
    public static final String SELECTED_MONTH = "Selected Month";
    public static final String SELECTED_DAY = "Selected Day";

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private JourneyManager journeyManager = carbonTrackerModel.getJourneyManager();

    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_list);

        setupOnItemClickListener();
        populateListView();
    }

    private void setupOnItemClickListener() {
        ListView list = (ListView) findViewById(R.id.listViewDates);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                userVehicleManager.setCurrentVehicle(userVehicleManager.getUserVehicle(i));
                // Todo: Get year, month, date of (i)


                Intent intent = DisplayCarbonFootPrintActivity.makeIntent(DateListActivity.this);
                intent.putExtra(SELECTED_YEAR, year);
                intent.putExtra(SELECTED_MONTH, month);
                intent.putExtra(SELECTED_DAY, day);
                startActivity(intent);
            }
        });
    }

    private void populateListView() {
        List<DateYMD> dateList = createList(journeyManager);
        ArrayAdapter<DateYMD> adapter = new ArrayAdapter<>(this, R.layout.jouney_list, dateList);
        ListView list = (ListView) findViewById(R.id.listViewDates);
        list.setAdapter(adapter);
    }

    private List<DateYMD> createList(JourneyManager journeyManager) {
        HashSet hashSet = new HashSet();
        List<DateYMD> dateList = new ArrayList<>();
        int journeyListSize = journeyManager.getSize();
        for (int i = 0; i < journeyListSize; i++) {
            Journey journey = journeyManager.getJourney(i);
            int year = journey.getYear();
            int month = journey.getMonth();
            int day = journey.getDay();

//            String date = month + "/" + day + "/" + year;
            DateYMD date = new DateYMD(year, month, day);

            if (hashSet.add(date)) {
                dateList.add(date);
            }
        }
        return dateList;
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, DateListActivity.class);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SaveData.storeSharePreference(this);
    }
}
