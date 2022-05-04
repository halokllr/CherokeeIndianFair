package com.ebcicomm.cherokeeindianfair;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button aboutButton = findViewById(R.id.aboutButton);
        Button exhibitsButton = findViewById(R.id.exhibitsButton);
        Button eventsButton = findViewById(R.id.eventsButton);

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAboutInfo();
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
}