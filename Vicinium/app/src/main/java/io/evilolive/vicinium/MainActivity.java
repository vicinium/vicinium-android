package io.evilolive.vicinium;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;

import java.util.Timer;


public class MainActivity extends ActionBarActivity {

    // UI login stuff
    TextView usernameField;
    Button signinButton;

    // Location stuff
    LocationHandler locationHandler;
    Timer timer;
    Location location;

    private static final String PREFS_NAME = "ListOfUsers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameField = (TextView) findViewById(R.id.usernameField);
//        signinButton = (Button) findViewById(R.id.signinButton);
        location = LocationHandler.getInstance(this).getLocation();

        ActionProcessButton btnSignIn = (ActionProcessButton) findViewById(R.id.btnSignIn);
        btnSignIn.setMode(ActionProcessButton.Mode.PROGRESS);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences usernamePref = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = usernamePref.edit();
                editor.putString("username", usernameField.getText().toString());

                editor.commit();

                createMessageActivityList(v);
            }
        });

        locationHandler = LocationHandler.getInstance(this);
        timer = new Timer();
        timer.scheduleAtFixedRate(new LocationUpdater(locationHandler), 0, 60000);
    }

    @Override
    protected void onPause() {
        locationHandler.removeUpdates();
        super.onPause();
    }

    @Override
    protected void onResume() {
        locationHandler.resumeUpdates();
        super.onResume();
    }


    private void createMessageActivityList(View view) {
        Intent intent = new Intent(this, MessageList.class);
        startActivity(intent);
    }

    public static String getUsername(Context context) {
        SharedPreferences preference = context.getSharedPreferences(PREFS_NAME, 0);
        String username = preference.getString("username", null);

        return username;
    }
}
