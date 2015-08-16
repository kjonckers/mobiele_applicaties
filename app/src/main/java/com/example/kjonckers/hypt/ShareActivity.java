package com.example.kjonckers.hypt;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kjonckers.hypt.db.ShareDatabase;

import domain.Share;
import domain.User;
import exceptions.DomainException;


public class ShareActivity extends AppCompatActivity {

    Toolbar toolbar;
    private User currentUser;
    private ShareDatabase shareDB;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        currentUser = (User) getIntent().getSerializableExtra("currentUser");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.shareText);
                String text = editText.getText().toString();
                try {
                    Share share = new Share("0", currentUser.getId(), text, currentUser.getLocation());
                    shareDB = new ShareDatabase();
                    shareDB.updateShare(share);
                } catch (DomainException e) {
                    e.printStackTrace();
                }

                Intent i = new Intent(ShareActivity.this, MainActivity.class);
                i.putExtra("currentUser", currentUser);
                startActivity(i);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_share, menu);
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