package com.example.kjonckers.hypt;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kjonckers.hypt.db.ShareDatabase;
import com.example.kjonckers.hypt.db.UserDatabase;

import java.util.List;

import domain.GeoLocation;
import domain.Share;
import domain.User;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageButton FAB;
    private User currentUser;
    private ListView mUsersList;
    private ShareDatabase shareDB = new ShareDatabase();
    private UserDatabase userDB = new UserDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentUser = (User) getIntent().getSerializableExtra("currentUser");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        manageLocation();
        FAB = (ImageButton) findViewById(R.id.imageButton);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,ShareActivity.class);
                i.putExtra("currentUser", currentUser);
                startActivity(i);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    //When your device updates your location, 2 things happen:
    //1. Current users in memory location will be set to this value
    //2. Current users database location will be set to this value
    private void manageLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                Log.i("Location Device", location.getLatitude() + "," + location.getLongitude());
                currentUser.setLocation(new GeoLocation(location.getLatitude(), location.getLongitude()));

                userDB.updateUser(currentUser);

            }
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        //Only add the location managers the device has
        if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }
}
