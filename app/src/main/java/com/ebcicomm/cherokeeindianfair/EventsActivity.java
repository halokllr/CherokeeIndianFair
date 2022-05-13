package com.ebcicomm.cherokeeindianfair;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.zip.Inflater;

public class EventsActivity extends AppCompatActivity {

    String fileExtension = ".txt";


    ArrayList<Event> stickballEvents = new ArrayList<>();
    String stickballEventsString = "Stickball";

    ArrayList<Event> pageantEvents = new ArrayList<>();
    String pageantEventsString = "Pageant";

    ArrayList<Event> otherEvents = new ArrayList<>();
    String otherEventsString = "Other";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);


        stickballEvents = loadDataFromLocal(stickballEventsString);
        pageantEvents = loadDataFromLocal(pageantEventsString);
        otherEvents = loadDataFromLocal(otherEventsString);



        // ImageButtons for the event categories
        ImageButton stickballButton = findViewById(R.id.stickballButton);
        ImageButton pageantsButton = findViewById(R.id.pageantsButton);
        ImageButton otherButton = findViewById(R.id.otherButton);


        //      Get all of the allEvents and set the ArrayLists
        // Stickball allEvents
        stickballButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadEventsToView(stickballEvents, "Stickball");
            }
        });
        pageantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadEventsToView(pageantEvents, "Miss Cherokee Pageants");
            }
        });
        otherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadEventsToView(otherEvents, "Other Events");
            }
        });

    }

    public void loadEventsToView(ArrayList<Event> events, String eventName) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(EventsActivity.this);

        Log.d("Query-CARD_CREATION", "Beginning card creation...");
        LayoutInflater inflater = getLayoutInflater();
        View eventsListView = inflater.inflate(R.layout.events_list, null);
        TableLayout eventsTableLayoutView = eventsListView.findViewById(R.id.eventsTable);
        for (int i = 0; i < events.size(); i++) {
            TableRow row = new TableRow(getApplicationContext());
            CardView card = (CardView) inflater.inflate(R.layout.event_card, row, false);
            TextView cardTextTitle = card.findViewById(R.id.eventNameTextView);
            cardTextTitle.setText(events.get(i).getEventName());
            row.addView(card);
            eventsTableLayoutView.addView(row);
        }
        alert.setView(eventsListView);
        final AlertDialog alertDialog = alert.create();

        Button backButton = eventsListView.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    public ArrayList<Event> loadDataFromLocal(String eventString) {
        Log.d("Query-LOAD_DATA", "Loading data from device...");
        ArrayList<Event> eventArray = null;
        try {
            String fileName = eventString + fileExtension;
            FileInputStream fis = openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            eventArray = (ArrayList<Event>) ois.readObject();
            Log.d("Query-EVENTS_VIEW-LOAD_DATA", "Event loaded: " + eventArray.toString());
            return eventArray;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close(View view) {
        finish();
    }
}