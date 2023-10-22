package edu.wm.cs.cs301.alexlongo.gui;

import static edu.wm.cs.cs301.alexlongo.gui.GeneratingActivity.theMaze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import edu.wm.cs.cs301.alexlongo.R;
import edu.wm.cs.cs301.alexlongo.generation.Maze;

public class PlayManuallyActivity extends AppCompatActivity implements GameplayActivity {

    ///////////////////////////////////
    //        Class Variables        //
    ///////////////////////////////////

    // Maze panel object for drawing the graphics
    MazePanel mazePanel;

    // working maze object
    private Maze workingMaze;

    // string to send to state winning
    String winning_string;

    // keep track of Path Length
    private int pathLength;

    // keep track of shortestPath
    private int shortestPath;

    // state playing to act as a sort of controller
    private StatePlaying statePlayingController;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_manually);

        ////////////////// TESTING / DEBUGGING CODE ////////////////////
        /*
        mazePanel = findViewById(R.id.mazePanel);
        mazePanel.addBackground(42);
        int[] xPoints = new int[]{30, 40, 50, 60};
        int[] yPoints = new int[]{100, 110, 120, 130};
        mazePanel.addFilledPolygon(xPoints, yPoints, 4);
         */
        ////////////////////////////////////////////////////////////////

        pathLength = 0;
        winning_string = "Manual";

        // import the static maze object from generating activity
        Maze workingMaze = theMaze;

        int x = workingMaze.getStartingPosition()[0];
        int y = workingMaze.getStartingPosition()[1];
        shortestPath = workingMaze.getDistanceToExit(x, y);

        statePlayingController = new StatePlaying();
        statePlayingController.setMaze(workingMaze);
        statePlayingController.start(this, findViewById(R.id.mazePanel));
    }

    /*
    When back arrow pressed return to main menu state
     */
    @Override
    public void onBackPressed(){
        Intent mainMenuState = new Intent(PlayManuallyActivity.this, AMazeActivity.class);

        // Log the switch
        Log.v( "Bundle", "Switching from PlayManuallyActivity to AMazeActivity");

        // Initiate Switch
        startActivity(mainMenuState);
    }

    /*
    Switches to the WinningActivity Layout
    */
    public void switchToWinningActivity(View v)
    {
        // Create a new Intent for switching to the Winning activity
        // Store the robot data for transport to the generating activity
        Intent winningState = new Intent(PlayManuallyActivity.this, WinningActivity.class);
        winningState.putExtra("shortestPath", shortestPath);
        winningState.putExtra("pathLength", pathLength);
        winningState.putExtra("GameplayActivity", winning_string);

        // Log the switch
        Log.v("Bundle", "Switching from PlayManuallyActivity to WinningActivity");

        // Initiate Switch
        startActivity(winningState);
    }

    @Override
    public void switchToLosingActivity(){

    }

    @Override
    public void adjustSensors(boolean flipper) {

    }

    /*
    Switches to the WinningActivity Layout
    */
    @Override
    public void switchToWinningActivity()
    {
        // Create a new Intent for switching to the Winning activity
        // Store the robot data for transport to the generating activity
        Intent winningState = new Intent(PlayManuallyActivity.this, WinningActivity.class);
        winningState.putExtra("shortestPath", shortestPath);
        winningState.putExtra("pathLength", pathLength);
        winningState.putExtra("GameplayActivity", winning_string);

        // Log the switch
        Log.v("Bundle", "Switching from PlayManuallyActivity to WinningActivity");

        // Initiate Switch
        startActivity(winningState);
    }

    /**
     * Increments the odometer by the specified amount
     * @param steps - number of steps to increment the odometer by
     */
    @Override
    public void incrementPathLength(int steps)
    {
        pathLength += steps;
    }

    ////////////////////////////////////////////////////////////
    /////////////////// USER INPUT METHODS /////////////////////
    ////////////////////////////////////////////////////////////

    /**
     * Handles User Input for moving foward
     */
    public void moveFWD(View v)
    {
        this.statePlayingController.handleUserInput("UP");
    }

    /**
     * Handles User Input for jumping
     */
    public void jump(View v)
    {
        this.statePlayingController.handleUserInput("JUMP");
    }

    /**
     * Handles User Input for rotating left
     */
    public void rotateLEFT(View v)
    {
        this.statePlayingController.handleUserInput("LEFT");
    }

    /**
     * Handles User Input for rotating right
     */
    public void rotateRIGHT(View v)
    {
        this.statePlayingController.handleUserInput("RIGHT");
    }

    /**
     * Handles User input for switching the map view on and off
     */
    public void toggleShowMap(View v)
    {
        this.statePlayingController.handleUserInput("TOGGLELOCALMAP");
    }

    /**
     * Handles User input for switching the map solution on and off
     */
    public void toggleShowSolution(View v)
    {
        this.statePlayingController.handleUserInput("TOGGLESOLUTION");
    }

    /**
     * Handles User input for switching the full map view on and off
     */
    public void toggleShowWalls(View v)
    {
        this.statePlayingController.handleUserInput("TOGGLEFULLMAP");
    }

    /**
     * Handles User input for zooming in on the map
     */
    public void zoomIn(View v)
    {
        this.statePlayingController.handleUserInput("ZOOMIN");
    }

    /**
     * Handles User input for zooming in on the map
     */
    public void zoomOut(View v)
    {
        this.statePlayingController.handleUserInput("ZOOMOUT");
    }

}