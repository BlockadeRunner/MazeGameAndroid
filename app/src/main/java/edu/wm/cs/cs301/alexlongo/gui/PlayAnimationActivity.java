package edu.wm.cs.cs301.alexlongo.gui;

import static edu.wm.cs.cs301.alexlongo.gui.GeneratingActivity.theMaze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import edu.wm.cs.cs301.alexlongo.R;
import edu.wm.cs.cs301.alexlongo.generation.Maze;

public class PlayAnimationActivity extends AppCompatActivity implements GameplayActivity
{
    ///////////////////////////////////
    //        Class Variables        //
    ///////////////////////////////////

    // Keep track of the driver being used
    String driver;

    // Keep track of the Sensor configuration
    String robotConfig;

    // Variable to store the length of the path traversed by the robot
    int robotPathLength;

    // Variable to Store the Battery Life of the Robot:
    float batteryLevel;

    // Variable to Store the shortest possible path length
    int shortestPath;

    // state playing to act as a sort of controller
    private StatePlaying statePlayingController;

    // keep track of Path Length
    private int pathLength;

    // string to send to state winning
    String winning_string;

    // working maze object
    private Maze workingMaze;

    // store the robot driver object
    private RobotDriver theDriver;

    // store the robot
    private Robot theRobot;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_animation);

        // Obtain the settings from the previous activity
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            driver = extras.getString("driver");
            robotConfig = extras.getString("robotConfig");
        }

        pathLength = 0;
        winning_string = "Animation";

        // import the static maze object from generating activity
        Maze workingMaze = theMaze;

        int x = workingMaze.getStartingPosition()[0];
        int y = workingMaze.getStartingPosition()[1];
        shortestPath = workingMaze.getDistanceToExit(x, y);
        if(!driver.equals("Wizard"))
        {
            sanity_check_for_debugging();
        }

        statePlayingController = new StatePlaying();
        statePlayingController.setMaze(workingMaze);
        statePlayingController.start(this, findViewById(R.id.mazePanel2));

        // Robot Initialization
        if(driver.equals("Wizard"))
        {
            Wizard tempWiz = new Wizard();
            theDriver = tempWiz;
            tempWiz.setConnection(statePlayingController);
            ReliableRobot theRobotBeingBuilt = new ReliableRobot();
            theRobotBeingBuilt.setup(1,1,1,1, workingMaze, statePlayingController);
            theRobot = theRobotBeingBuilt;
            theDriver.setMaze(workingMaze);
            theDriver.setRobot(theRobotBeingBuilt);
            statePlayingController.handleUserInput("TOGGLELOCALMAP");
            Thread wizardThread = new Thread(theDriver);
            wizardThread.start();
            while(!tempWiz.getFinishied())
            {
                adjustSensors(theDriver.getFlipper());
            }
        }
        else
        {
            theDriver = new WallFollower();
            switch(robotConfig)
            {
                case "Premium" :
                    theRobot = new ReliableRobot();
                    DistanceSensor fwd = new ReliableSensor(Robot.Direction.FORWARD);
                    theRobot.addDistanceSensor(fwd, Robot.Direction.FORWARD);
                    DistanceSensor bwd = new ReliableSensor(Robot.Direction.BACKWARD);
                    theRobot.addDistanceSensor(bwd, Robot.Direction.BACKWARD);
                    DistanceSensor left = new ReliableSensor(Robot.Direction.LEFT);
                    theRobot.addDistanceSensor(left, Robot.Direction.LEFT);
                    DistanceSensor right = new ReliableSensor(Robot.Direction.RIGHT);
                    theRobot.addDistanceSensor(right, Robot.Direction.RIGHT);
                    break;
                case "Mediocre" :
                    theRobot = new UnreliableRobot();
                    DistanceSensor fwdM = new ReliableSensor(Robot.Direction.FORWARD);
                    theRobot.addDistanceSensor(fwdM, Robot.Direction.FORWARD);
                    DistanceSensor bwdM = new ReliableSensor(Robot.Direction.BACKWARD);
                    theRobot.addDistanceSensor(bwdM, Robot.Direction.BACKWARD);
                    DistanceSensor leftM = new UnreliableSensor(Robot.Direction.LEFT);
                    theRobot.addDistanceSensor(leftM, Robot.Direction.LEFT);
                    DistanceSensor rightM = new UnreliableSensor(Robot.Direction.RIGHT);
                    theRobot.addDistanceSensor(rightM, Robot.Direction.RIGHT);
                    break;
                case "Soso" :
                    theRobot = new UnreliableRobot();
                    DistanceSensor fwdS = new UnreliableSensor(Robot.Direction.FORWARD);
                    theRobot.addDistanceSensor(fwdS, Robot.Direction.FORWARD);
                    DistanceSensor bwdS = new UnreliableSensor(Robot.Direction.BACKWARD);
                    theRobot.addDistanceSensor(bwdS, Robot.Direction.BACKWARD);
                    DistanceSensor leftS = new ReliableSensor(Robot.Direction.LEFT);
                    theRobot.addDistanceSensor(leftS, Robot.Direction.LEFT);
                    DistanceSensor rightS = new ReliableSensor(Robot.Direction.RIGHT);
                    theRobot.addDistanceSensor(rightS, Robot.Direction.RIGHT);
                    break;
                case "Shaky" :
                    theRobot = new UnreliableRobot();
                    DistanceSensor fwdSH = new UnreliableSensor(Robot.Direction.FORWARD);
                    theRobot.addDistanceSensor(fwdSH, Robot.Direction.FORWARD);
                    DistanceSensor bwdSH = new UnreliableSensor(Robot.Direction.BACKWARD);
                    theRobot.addDistanceSensor(bwdSH, Robot.Direction.BACKWARD);
                    DistanceSensor leftSH = new UnreliableSensor(Robot.Direction.LEFT);
                    theRobot.addDistanceSensor(leftSH, Robot.Direction.LEFT);
                    DistanceSensor rightSH = new UnreliableSensor(Robot.Direction.RIGHT);
                    theRobot.addDistanceSensor(rightSH, Robot.Direction.RIGHT);
                    break;
                case "ERROR, REVERT" :
                    Wizard tempWiz = new Wizard();
                    theDriver = tempWiz;
                    tempWiz.setConnection(statePlayingController);
                    ReliableRobot theRobotBeingBuilt = new ReliableRobot();
                    theRobotBeingBuilt.setup(1,1,1,1, workingMaze, statePlayingController, true);
                    theRobot = theRobotBeingBuilt;
                    theDriver.setMaze(workingMaze);
                    theDriver.setRobot(theRobotBeingBuilt);
                    statePlayingController.handleUserInput("TOGGLELOCALMAP");
                    Thread wizardThread = new Thread(theDriver);
                    wizardThread.start();
//                    while(!tempWiz.getFinishied())
//                    {
//                        adjustSensors(theDriver.getFlipper());
//                    }

            }
        }




    }

    /*
    When back arrow pressed return to main menu state
     */
    @Override
    public void onBackPressed(){
        Intent mainMenuState = new Intent(PlayAnimationActivity.this, AMazeActivity.class);

        // Log the switch
        Log.v( "Bundle", "Switching from PlayAnimationActivity to AMazeActivity");

        // Initiate Switch
        startActivity(mainMenuState);
    }

    /*
    Switches to the WinningActivity Layout
    */
    public void switchToWinningActivity(View v) {
        String str = "Animation";
        batteryLevel = statePlayingController.getBatteryLevel();
        // Create a new Intent for switching to the Winning activity
        // Store the robot data for transport to the generating activity
        Intent winningState = new Intent(PlayAnimationActivity.this, WinningActivity.class);
        winningState.putExtra("pathLength", pathLength);
        winningState.putExtra("batteryLevel", batteryLevel);
        winningState.putExtra("shortestPath", shortestPath);
        winningState.putExtra("GameplayActivity", str);

        // Log the switch
        Log.v("Bundle", "Switching from PlayAnimation to WinningActivity");

        // Initiate Switch
        startActivity(winningState);
    }

    @Override
    public void switchToWinningActivity()
    {
        String str = "Animation";
        batteryLevel = statePlayingController.getBatteryLevel();
        // Create a new Intent for switching to the Winning activity
        // Store the robot data for transport to the generating activity
        Intent winningState = new Intent(PlayAnimationActivity.this, WinningActivity.class);
        winningState.putExtra("pathLength",pathLength);
        winningState.putExtra("batteryLevel", batteryLevel);
        winningState.putExtra("shortestPath", shortestPath);
        winningState.putExtra("GameplayActivity", str);

        // Log the switch
        Log.v("Bundle", "Switching from PlayAnimation to WinningActivity");

        // Initiate Switch
        startActivity(winningState);
    }

    @Override
    public void switchToLosingActivity()
    {
        String str = "Animation";
        batteryLevel = statePlayingController.getBatteryLevel();
        // Create a new Intent for switching to the Losing activity
        // Store the robot data for transport to the generating activity
        Intent losingState = new Intent(PlayAnimationActivity.this, LosingActivity.class);
        losingState.putExtra("pathLength", pathLength);
        losingState.putExtra("batteryLevel", batteryLevel);
        losingState.putExtra("shortestPath", shortestPath);
        losingState.putExtra("GameplayActivity", str);

        // Log the switch
        Log.v("Bundle", "Switching from PlayAnimation to WinningActivity");

        // Initiate Switch
        startActivity(losingState);
    }

    public void switchToLosingActivity(View v)
    {
        String str = "Animation";
        batteryLevel = statePlayingController.getBatteryLevel();
        // Create a new Intent for switching to the Losing activity
        // Store the robot data for transport to the generating activity
        Intent losingState = new Intent(PlayAnimationActivity.this, LosingActivity.class);
        losingState.putExtra("pathLength", pathLength);
        losingState.putExtra("batteryLevel", batteryLevel);
        losingState.putExtra("shortestPath", shortestPath);
        losingState.putExtra("GameplayActivity", str);

        // Log the switch
        Log.v("Bundle", "Switching from PlayAnimation to WinningActivity");

        // Initiate Switch
        startActivity(losingState);
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

    private void sanity_check_for_debugging()
    {
        robotConfig = "ERROR, REVERT";
    }

    @Override
    public void adjustSensors(boolean flip)
    {
        TextView frontSensor = findViewById(R.id.textView25);
        TextView backSensor = findViewById(R.id.textView26);
        TextView leftSensor = findViewById(R.id.textView23);
        TextView rightSensor = findViewById(R.id.textView24);
        if(flip)
        {
            Bundle extras = getIntent().getExtras();
            switch (extras.getString("robotConfig"))
            {
                case "Premium" :
                    frontSensor.setText("Front Sensor: Good");
                    backSensor.setText("Back Sensor: Good");
                    leftSensor.setText("Left Sensor: Good");
                    rightSensor.setText("Right Sensor: Good");
                    break;
                case "Mediocre" :
                    frontSensor.setText("Front Sensor: Good");
                    backSensor.setText("Back Sensor: Good");
                    leftSensor.setText("Left Sensor: Broken");
                    rightSensor.setText("Right Sensor: Broken");
                    break;
                case "Soso" :
                    frontSensor.setText("Front Sensor: Broken");
                    backSensor.setText("Back Sensor: Broken");
                    leftSensor.setText("Left Sensor: Good");
                    rightSensor.setText("Right Sensor: Good");
                    break;
                case "Shaky" :
                    frontSensor.setText("Front Sensor: Good");
                    backSensor.setText("Back Sensor: Broken");
                    leftSensor.setText("Left Sensor: Good");
                    rightSensor.setText("Right Sensor: Broken");
                    break;
            }
        }
        else
        {
            Bundle extras = getIntent().getExtras();
            switch (extras.getString("robotConfig"))
            {
                case "Premium" :
                    frontSensor.setText("Front Sensor: Good");
                    backSensor.setText("Back Sensor: Good");
                    leftSensor.setText("Left Sensor: Good");
                    rightSensor.setText("Right Sensor: Good");
                    break;
                case "Mediocre" :
                    frontSensor.setText("Front Sensor: Good");
                    backSensor.setText("Back Sensor: Good");
                    leftSensor.setText("Left Sensor: Good");
                    rightSensor.setText("Right Sensor: Good");
                    break;
                case "Soso" :
                    frontSensor.setText("Front Sensor: Good");
                    backSensor.setText("Back Sensor: Good");
                    leftSensor.setText("Left Sensor: Good");
                    rightSensor.setText("Right Sensor: Good");
                    break;
                case "Shaky" :
                    frontSensor.setText("Front Sensor: Broken");
                    backSensor.setText("Back Sensor: Good");
                    leftSensor.setText("Left Sensor: Broken");
                    rightSensor.setText("Right Sensor: Good");
                    break;
            }
        }
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