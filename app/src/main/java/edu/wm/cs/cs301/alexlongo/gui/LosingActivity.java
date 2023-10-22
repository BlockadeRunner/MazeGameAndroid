package edu.wm.cs.cs301.alexlongo.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import edu.wm.cs.cs301.alexlongo.R;

public class LosingActivity extends AppCompatActivity {

    ///////////////////////////////////
    //        Class Variables        //
    ///////////////////////////////////

    // keep track of Path Length
    private int pathLength;

    // keep track of shortestPath
    private int shortestPath;

    // keep track of the energy consumption if applicable
    private float energyConsumption;

    // keep track of GameplayActivity
    private String gameplay;

    // TextView Strings
    private TextView lengthOfPathTaken;
    private TextView shortestPossiblePathLength;
    private TextView ratRobotEnergyConsumption;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_losing);

        // Obtain the settings from the previous activity
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            pathLength = extras.getInt("pathLength");
            shortestPath = extras.getInt("shortestPath");
            gameplay = extras.getString("GameplayActivity");
        }

        if(gameplay.equals("Animation"))
        {
            energyConsumption = 3500f - extras.getFloat("batteryLevel");
        }

        ratRobotEnergyConsumption = findViewById(R.id.textView21Losing);
        ratRobotEnergyConsumption.setText("");

        if(gameplay.equals("Manual"))
        {
            ratRobotEnergyConsumption.setText(" ");
        }
        else if (gameplay.equals("Animation"))
        {
            ratRobotEnergyConsumption.setText("Rat-Robot Energy Consumption: " + energyConsumption);
        }

        lengthOfPathTaken = findViewById(R.id.textView19Losing);
        lengthOfPathTaken.setText("Length of Path Taken: " + pathLength);

        shortestPossiblePathLength = findViewById(R.id.textView20Losing);
        shortestPath--;
        shortestPossiblePathLength.setText("Shortest possible Path Length: " + shortestPath);

    }

    /*
    When back arrow pressed return to main menu state
     */
    @Override
    public void onBackPressed(){
        Intent mainMenuState = new Intent(LosingActivity.this, AMazeActivity.class);

        // Log the switch
        Log.v( "Bundle", "Switching from LosingActivity to AMazeActivity");

        // Initiate Switch
        startActivity(mainMenuState);
    }

}