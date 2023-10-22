package edu.wm.cs.cs301.alexlongo.gui;

import static edu.wm.cs.cs301.alexlongo.R.*;
import static edu.wm.cs.cs301.alexlongo.R.id.selectSkillLevelSeekBar;
import static edu.wm.cs.cs301.alexlongo.R.id.toggleButtonRoom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import edu.wm.cs.cs301.alexlongo.R;

public class AMazeActivity extends AppCompatActivity {

    ///////////////////////////////////
    //        Class Variables        //
    ///////////////////////////////////

    // Variable to store the skill level of the maze
    int mazeDifficulty = 0;

    // Variable to store the generation algorithm used to build the maze
    String generationAlgorithm = "Boruvka";

    // Variable to keep track of if the maze has rooms or not
    boolean rooms;

    // Variable to store the seekbar object used to select skill level
    SeekBar selectSkillLevel;

    // Variable to store the toggle button object used for toggling room generation
    ToggleButton toggleButtonRooms;


    /*
    Create the main menu layout
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_amaze);

        // Connect the toggle button object from the layout to the global variable of this activity
        toggleButtonRooms = (ToggleButton)findViewById(toggleButtonRoom);

        // Connect the seekbar object from the layout to the global variable of this activity
        selectSkillLevel = (SeekBar)findViewById(selectSkillLevelSeekBar);

        // Add a listener to correctly update the skill level when the seek bar is changed
        selectSkillLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                mazeDifficulty = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /*
    Sets the generation algroithm variable to Boruvka
    */
    public void setBoruvka(View v)
    {
        generationAlgorithm = "Boruvka";
    }

    /*
    Sets the generation algroithm variable to DFS
    */
    public void setDFS(View v)
    {
        generationAlgorithm = "DFS";
    }

    /*
    Sets the generation algroithm variable to Prim
    */
    public void setPrim(View v)
    {
        generationAlgorithm = "Prim";
    }

    /*
    Sets the rooms variable
    */
    public void toggleRooms(View v)
    {
        if(toggleButtonRooms.getText().equals("ON"))
        {
            rooms = true;
        }
        if(toggleButtonRooms.getText().equals("OFF"))
        {
            rooms = false;
        }
    }

    /*
    When back arrow pressed do nothing on main menu state
     */
    @Override
    public void onBackPressed(){

    }


    /*
    Switches to the GeneratingActivity Layout
    Used by the 'Explore New Maze' button
     */
    public void switchToGeneratingActivity(View v)
    {
        // Create a new Intent for switching to the generating activity
        // Store the user selections for transport to the generating activity
        Intent generationStage = new Intent(AMazeActivity.this, GeneratingActivity.class);
        generationStage.putExtra("mazeDifficulty", mazeDifficulty);
        generationStage.putExtra("generationAlgorithm", generationAlgorithm);
        generationStage.putExtra("rooms", rooms);

        // Log the switch
        Log.v( "Bundle", "Switching from AMazeActivity to GeneratingActivity");

        // Initiate Switch
        startActivity(generationStage);

        // Debugging code
        /*
        System.out.println("Skill Level: " + mazeDifficulty);
        System.out.println("Generation Alg: " + generationAlgorithm);
        System.out.println("Rooms: " + rooms);
         */
    }
}