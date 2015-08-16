package com.example.kjonckers.hypt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kjonckers.hypt.db.ShareDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import domain.Share;
import domain.User;

public class SharesActivity extends AppCompatActivity {

    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;
    private ShareDatabase shareDB = new ShareDatabase();
    private DistanceCalculator calculator = new DistanceCalculator();
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shares);
        currentUser = (User) getIntent().getSerializableExtra("currentUser");

        // Find the ListView resource.
        mainListView = (ListView) findViewById( R.id.mainListView );


        shareDB.getAllShares();
        String[] planets = new String[] { "Dit is een hard coded hypt bericht", "Dit is ook een hard coded hypt bericht"};
        ArrayList<String> shares = new ArrayList<String>();
        shares.addAll(Arrays.asList(planets));

        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<String>(this, R.layout.share_row, shares);
        List<Share> shareObjects = shareDB.getAllShares();
        for(int i = 0; i < shareObjects.size(); i++) {
            Share share = shareObjects.get(i);
            listAdapter.add(share.getText() + "(" + calculator.calc(currentUser.getLocation(), share.getLocation()) + "km)");
        }


        // Set the ArrayAdapter as the ListView's adapter.
        mainListView.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shares, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
