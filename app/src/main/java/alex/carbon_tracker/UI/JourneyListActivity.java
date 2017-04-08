package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.Model.SaveData;
import alex.carbon_tracker.R;

/*
* JourneyListActivity class page which allows the
* user to add a new journey to the system
* */
public class JourneyListActivity extends AppCompatActivity {

    private CarbonTrackerModel carbonTrackerModel;
    private JourneyManager journeyManager;
    private int currentJourneyPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        carbonTrackerModel = CarbonTrackerModel.getInstance();
        journeyManager = carbonTrackerModel.getJourneyManager();
        Log.d("JLAct", journeyManager.totalCarbonEmissionsPublicTransportation() + "");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_list);
        String[] journeyLists = journeyManager.getJourneyDescriptions();
        setCurrentJourneyPosition();
        ListView journeyList = (ListView) findViewById(R.id.journeyListView);
        registerForContextMenu(journeyList);
        journeyListView();
        setupAddJourneyButton();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.carbontrackerlogo5);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.back, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.back_actionbar_icon:
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SaveData.storeSharePreference(this);
    }

    private void setupAddJourneyButton() {
        Button btn = (Button) findViewById(R.id.AddJourneyButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SelectDateActivity.makeIntent(JourneyListActivity.this);
                startActivity(intent);
            }
        });
    }

    private void journeyListView() {
        String[] journeyList = carbonTrackerModel.getJourneyManager().getJourneyDescriptions();
      ArrayAdapter<String> adapter = new MyListAdapter();
        //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.jouney_list, journeyList);
        ListView list = (ListView) findViewById(R.id.journeyListView);
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<String> {
        public MyListAdapter() {
            super(JourneyListActivity.this, R.layout.journey_icon, journeyManager.getJourneyDescriptions());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.journey_icon, parent, false);
            }
            String currentDistription = journeyManager.getJourneyDescriptions()[position];
            if(journeyManager.getJourney(position).hasVehicle()) {
                int imageId = journeyManager.getJourney(position).getUserVehicle().getIconID();
                ImageView imageView = (ImageView) itemView.findViewById(R.id.iconForList);
                imageView.setImageResource(imageId);
            }
            TextView textView = (TextView)itemView.findViewById(R.id.journeyDisTextView);
            textView.setText(currentDistription);
        return itemView;
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Delete");
        menu.add(0, v.getId(), 0, "Edit Transportation");
        menu.add(0, v.getId(), 0, "Edit Route");
        menu.add(0, v.getId(), 0, "Edit Date");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals("Delete")) {

            carbonTrackerModel.getDateManager().deleteJourney
                    (carbonTrackerModel.getJourneyManager().getJourney(currentJourneyPosition));
            carbonTrackerModel.getJourneyManager().delete(currentJourneyPosition);
            journeyListView();
            return true;
        } else if (item.getTitle().equals("Edit Transportation")) {
            // editing car
            Intent intent = SelectTransportationModeActivity.makeIntent(this);
            intent.putExtra("editJourney", true);
            intent.putExtra("journeyPosition", currentJourneyPosition);
            startActivity(intent);
            return true;
        } else if (item.getTitle().equals("Edit Route")) {
            Intent intent = SelectRouteActivity.makeIntent(this);
            intent.putExtra("editJourney", true);
            intent.putExtra("journeyPosition", currentJourneyPosition);
            startActivity(intent);
            return true;
        } else if (item.getTitle().equals("Edit Date")) {
            Intent intent = SelectDateActivity.makeIntent(this);
            intent.putExtra("editJourney", true);
            intent.putExtra("journeyPosition", currentJourneyPosition);
            startActivity(intent);
            return true;
        } else {
            return false;
        }

    }

    private void setCurrentJourneyPosition() {
        final ListView listView = (ListView) findViewById(R.id.journeyListView);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                currentJourneyPosition = position;
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        journeyListView();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, JourneyListActivity.class);
    }
}