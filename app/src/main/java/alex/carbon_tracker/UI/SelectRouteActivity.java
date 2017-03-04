package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import alex.carbon_tracker.R;

public class SelectRouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);
        routeListView();
        setupAddRouteButton();
    }

    private void setupAddRouteButton() {
        Button btn = (Button) findViewById(R.id.addRouteButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddRouteActivity.makeIntent(SelectRouteActivity.this);
                startActivity(intent);

            }
        });

    }

    private void routeListView() {
        String[] routeList = {"route 1","route 2","route 3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.jouney_list,routeList);
        ListView list =(ListView) findViewById(R.id.routeListView);
        list.setAdapter(adapter);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectRouteActivity.class);
    }
}
