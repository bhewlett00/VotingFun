package com.example.votingfun;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner songSpinner;
    EditText voterEditText;
    String song;
    String voter;
    DBHandler dbHandler;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        songSpinner = (Spinner) findViewById(R.id.songSpinner);
        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.songs, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        songSpinner.setAdapter(adapter);
        songSpinner.setOnItemSelectedListener(this);
        voterEditText = (EditText) findViewById(R.id.voterEditText);
        dbHandler = new DBHandler(this, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        song = adapterView.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void likeSong(MenuItem menuItem){
        voter = voterEditText.getText().toString();
        if(voter.trim().equals("") || song.trim().equals(""))
            Toast.makeText(this, "Please select a song and enter your name!", Toast.LENGTH_LONG).show();
        else {
            dbHandler.addSongVote(song, voter, 1);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle("Rock the Vote");
            builder.setContentText("You like " + song);

            intent = new Intent(this, MainActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(2142, builder.build());
        }
    }

    public void dislikeSong(MenuItem menuItem){
        voter = voterEditText.getText().toString();
        if(voter.trim().equals("") || song.trim().equals(""))
            Toast.makeText(this, "Please select a song and enter your name!", Toast.LENGTH_LONG).show();
        else {
            dbHandler.addSongVote(song, voter, 0);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle("Rock the Vote");
            builder.setContentText("You dislike " + song);

            intent = new Intent(this, MainActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(2142, builder.build());
        }
    }
}
