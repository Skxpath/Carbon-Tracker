package alex.carbon_tracker.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import alex.carbon_tracker.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        journeyListView();
        setupAddJourneyButton();
    }

    private void setupAddJourneyButton() {
        Button btn = (Button) findViewById(R.id.AddJouenwyButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SelectTransportationMode.makeIntent(MainActivity.this);
                startActivity(intent);
                finish();
            }
        });

    }
    private void journeyListView() {
        String[] journwyList = {"journey 1","journwy 2","journey 3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.jouney_list,journwyList);
        ListView list =(ListView) findViewById(R.id.journeyListView);
        list.setAdapter(adapter);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0,v.getId(),0,"Delete");
        menu.add(0,v.getId(),0,"Edit");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals("Delete")){
            // delete the entry
            return true;
        }
        else if (item.getTitle().equals("Edit")){
            return true;
        }

        else {return false;
        }

    }


}