package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.SaveData;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import alex.carbon_tracker.Model.DateYMD;
import alex.carbon_tracker.Model.Journey;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.R;

public class DateListActivity extends AppCompatActivity {

    public static final String CHANGE_TO_GRAPHS = "Change to graphs from date list";
    public static final String SELECTED_YEAR = "Selected Year";
    public static final String SELECTED_MONTH = "Selected Month";
    public static final String SELECTED_DAY = "Selected Day";
    public static final String DISPLAY_DATA_YEAR = "Display Data Year";
    public static final String DISPLAY_DATA_MONTH = "Display Data Month";
    public static final String DISPLAY_DATA_DAY = "Display Data Day";

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private JourneyManager journeyManager = carbonTrackerModel.getJourneyManager();

    private int year;
    private int month;
    private int day;

    private List<DateYMD> dateList = new ArrayList<>();
    private List<String> dateListDescriptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_list);
        setupLastMonthButton();
        createDateList(journeyManager);
        setupOnItemClickListener();
        populateListView();
    }

    private void setupLastMonthButton() {
        Button button = (Button)findViewById(R.id.lastMonthGraphButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LineGraphActivity.makeIntent(DateListActivity.this);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setupOnItemClickListener() {
        final ListView list = (ListView) findViewById(R.id.listViewDates);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DateYMD date = dateList.get(i);
                year = date.getYear();
                month = date.getMonth();
                day = date.getDay();
                Intent intent = DisplayCarbonFootPrintActivity.makeIntent(DateListActivity.this);
                intent.putExtra(SELECTED_YEAR, year);
                intent.putExtra(SELECTED_MONTH, month);
                intent.putExtra(SELECTED_DAY, day);
                intent.putExtra(DISPLAY_DATA_DAY, 0);
                intent.putExtra(CHANGE_TO_GRAPHS, 0);

                startActivity(intent);
                finish();
            }


        });
    }

    private void populateListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.jouney_list, dateListDescriptions);
        ListView list = (ListView) findViewById(R.id.listViewDates);
        list.setAdapter(adapter);
    }



    private List<DateYMD> createDateList(JourneyManager journeyManager) {
        HashSet hashSet = new HashSet();
        int journeyListSize = journeyManager.getSize();
        for (int i = 0; i < journeyListSize; i++) {
            Journey journey = journeyManager.getJourney(i);
            int year = journey.getYear();
            int month = journey.getMonth();
            int day = journey.getDay();
            String description = month + "/" + day + "/" + year;
            DateYMD date = new DateYMD(year, month, day);
            if (hashSet.add(description)) {
                dateListDescriptions.add(description);
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
