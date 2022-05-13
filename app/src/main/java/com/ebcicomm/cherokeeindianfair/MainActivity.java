package com.ebcicomm.cherokeeindianfair;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    String fileExtension = ".txt";

    // TODO: Condense this down
    private String aboutMessage = "In the summer of 1912, Mr. Cato Sells, Commissioner of Indian Affairs made a special trip from Washington, D.C. to Cherokee. " +
            "His objective in coming to Cherokee was to discuss with James E. Henderson, Superintendent and James Blythe, Farmer Agent, the possibility of promoting and sponsoring an Indian Fair. " +
            "The Superintendent's desire was that the farmers of the six townships organize a club in each respective township; the clubs would form a Farmer's Organization. " +
            "When the clubs were organized the membership totaled twenty-two members. " +
            "The officers elected were as follows: Chairman ,John W. Wolfe; Vice-Chairman, Johnson B. Thompson; English Clerk, Johnson Owle; Indian Clerk, Deliskie Climbingbear; Treasurer, Mary E. Wolfe. " +
            "The Program Committee consisted of: Will West Long, Charlie Lambert and Arsene Thompson. " +
            "During the year of 1914 the Commissioner of Indian Affairs officially authorized the presentation of the Fair. " +
            "The Big Cove Farmer's Organization had a special meeting to elect a Fair Committee to work with the Superintendent and Farm Agent. " +
            "The committee included: Will West Long, Charlie Lambert, and Arsene Thompson. " +
            "They were confronted with the problem of whether to hold the fair at Cherokee of Big Cove. " +
            "When a vote was taken Cherokee was chosen, the count being 11 to 10. " +
            "The next decision was what to name the fair. " +
            "The two names put up for vote were \"Indian Fair\" presented by Will West Long and \"Cherokee Indian Fair\" by Mary E. Wolfe. " +
            "The latter name was chosen and has never been changed. " +
            "The first exhibit was held in what was then the school Chapel and is now known as Qualla Hall. " +
            "Funding for the exhibit prizes was originally provided by the Cherokee Historical Association but today it is provided by the Eastern Band of Cherokee Indians through their Destination Marketing Department.\n\n" +
            "After World War II the NC Cooperative Extension Agency would send their first extension agent to the Qualla Boundary. " +
            "Agents were instructed to assist the Cherokee with developing community clubs for the members of the Tribe. " +
            "Much to their surprise they discovered that six townships had established Gadugi Groups and Farm Clubs. ";


    ArrayList<Event> stickballEvents = new ArrayList<>();
    String stickballEventsString = "Stickball";

    ArrayList<Event> pageantEvents = new ArrayList<>();
    String pageantEventsString = "Pageant";

    ArrayList<Event> otherEvents = new ArrayList<>();
    String otherEventsString = "Other";







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loadDataFromServer(stickballEvents, stickballEventsString);
        loadDataFromServer(pageantEvents, pageantEventsString);
        loadDataFromServer(otherEvents, otherEventsString);


        Button aboutButton = findViewById(R.id.aboutButton);
        Button exhibitsButton = findViewById(R.id.exhibitsButton);
        Button eventsButton = findViewById(R.id.eventsButton);





        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAboutInfo();
            }
        });


        // TODO: Add implementation
        exhibitsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        eventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openView();
            }
        });



    }


    public void showAboutInfo() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.about_dialog, null);
        TextView aboutTextView = (TextView) mView.findViewById(R.id.aboutText);
        Button closeButton = (Button) mView.findViewById(R.id.closeButton);
        aboutTextView.setText(getAboutMessage());

        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(true);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public String getAboutMessage() {
        return this.aboutMessage;
    }

    public void openView () {
        Intent intent = new Intent(this, EventsActivity.class);
        startActivity(intent);
    }

    public void loadDataFromServer(ArrayList<Event> eventsList, String eventsListString) {
        Log.d("Query-LOAD_DATA", "Loading data...");
        Log.d("Query-LOAD_DATA", "Event Type to load: " + eventsListString);
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("FairEvents");
        query.whereContains("eventType", eventsListString);
        query.findInBackground((foundEvents, e) -> {
            if (e == null) {
                for (int i = 0; i < foundEvents.size(); i++) {
                    Event event = new Event(
                            (String) foundEvents.get(i).get("eventName"),
                            (Date) foundEvents.get(i).get("dayAndTime"),
                            (String) foundEvents.get(i).get("location")
                    );
                    Log.d("Query-LOAD_DATA", event.toString());
                    eventsList.add(event);
                    saveDataToLocal(eventsList, eventsListString);
                };
            } else {
                Log.d("Query-ERROR", "Parse Error: " + e);
                loadDataFromLocal(eventsList, eventsListString);
            }
        });
    }
    public void saveDataToLocal(ArrayList<Event> eventToBeSaved, String eventString) {
        String fileName = eventString + fileExtension;
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(eventToBeSaved);
            oos.close();
            fos.close();
            Log.d("Query-SAVE_DATA", "Event saved: " + eventToBeSaved.toString() + " at " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadDataFromLocal(ArrayList<Event> eventToBeLoaded, String eventString) {
        Log.d("Query-LOAD_DATA", "Loading data from device...");
        try {
            String fileName = eventString + fileExtension;
            FileInputStream fis = openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            eventToBeLoaded = (ArrayList<Event>) ois.readObject();
            Log.d("Query-LOAD_DATA", "Event loaded: " + eventToBeLoaded.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}