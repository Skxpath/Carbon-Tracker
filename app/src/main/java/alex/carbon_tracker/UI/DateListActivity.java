package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.Journey;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.Model.SaveData;
import alex.carbon_tracker.R;

/*DateListActivity to show a calender for the dates
* which lets the user choose when they made
* their bill or journey.
* */
public class DateListActivity extends AppCompatActivity {

    public static final String CHANGE_TO_GRAPHS = "Change to graphs from date list";
    public static final String SELECTED_DATE = "Selected Date";
    public static final String DISPLAY_DATA_YEAR = "Display Data Year";
    public static final String DISPLAY_DATA_MONTH = "Display Data Month";
    public static final String DISPLAY_DATA_DAY = "Display Data Day";

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private JourneyManager journeyManager = carbonTrackerModel.getJourneyManager();

    private List<Date> dateList = new ArrayList<>();
    private List<String> dateListDescriptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_list);
        setupLastMonthButton(R.id.lastYearGraphButton);
        setupLastMonthButton(R.id.lastMonthGraphButton);
        setupPieGraphButton(R.id.buttonPieMonth);
        setupPieGraphButton(R.id.buttonPieYear);
        createDateList(journeyManager);
        setupOnItemClickListener();
        populateListView();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.carbontrackerlogo5);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.back, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupPieGraphButton(final int id) {
        Button button = (Button) findViewById(id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = PieGraphMonthYear.makeIntent(DateListActivity.this);
                if (id == R.id.lastYearGraphButton) {
                    intent.putExtra("yearGraph", true);
                } else {
                    intent.putExtra("monthGraph", true);
                }
                startActivity(intent);
                finish();
            }
        });
    }

    private void setupLineGraphButton(final int id) {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.back_actionbar_icon:
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void setupLastMonthButton(final int id) {
        Button button = (Button) findViewById(id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LineGraphActivity.makeIntent(DateListActivity.this);
                if (id == R.id.lastYearGraphButton) {
                    intent.putExtra("yearGraph", true);
                } else {
                    intent.putExtra("monthGraph", true);
                }
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
                Date date = dateList.get(i);

                Intent intent = DisplayCarbonFootPrintActivity.makeIntent(DateListActivity.this);
                intent.putExtra(SELECTED_DATE, date);
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


    private void createDateList(JourneyManager journeyManager) {
        HashSet hashSet = new HashSet();
        int journeyListSize = journeyManager.getSize();
        for (int i = 0; i < journeyListSize; i++) {
            Journey journey = journeyManager.getJourney(i);
            Date journeyDate = journey.getDate();
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            String description = f.format(journeyDate);
            if (hashSet.add(description)) {
                dateListDescriptions.add(description);
                dateList.add(journeyDate);
                CarbonTrackerModel.getInstance().getDateManager();
            }
        }
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
