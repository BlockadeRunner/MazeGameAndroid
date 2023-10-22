package edu.wm.cs.cs301.alexlongo.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import edu.wm.cs.cs301.alexlongo.R;
import edu.wm.cs.cs301.alexlongo.generation.DefaultOrder;
import edu.wm.cs.cs301.alexlongo.generation.Maze;
import edu.wm.cs.cs301.alexlongo.generation.MazeFactory;
import edu.wm.cs.cs301.alexlongo.generation.Order;

import static edu.wm.cs.cs301.alexlongo.R.id.progressBarLL;


public class GeneratingActivity extends AppCompatActivity implements Runnable
{

    ///////////////////////////////////
    //        Class Variables        //
    ///////////////////////////////////

    // Variable to store the skill level of the maze
    int mazeDifficulty = 0;

    // Variables to store the generation algorithm used to build the maze
    String generationAlgorithm = "Boruvka";
    Order.Builder theMazeBuilder;

    // Variable to store the seed of the maze
    int mazeSeed;

    // Variable to keep track of if the maze has rooms or not
    boolean rooms;

    // MazeFactory object to build maze
    MazeFactory mazeFactory;

    // DefaultOrder object to be passed into the maze factory
    DefaultOrder mazeOrder;

    // Store the actual completed maze object
    static Maze theMaze;

    // Create a background thread
    Thread backgroundThread;

    // Variable to store the progress bar object used to display maze generation progress
    ProgressBar myProgressBar;

    // Variable to keep track of the driver
    String driver;

    // Variable to keep track of the robot configuration
    String robotConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generating);

        // Obtain the settings from the previous activity
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            mazeDifficulty = extras.getInt("mazeDifficulty");
            generationAlgorithm = extras.getString("generationAlgorithm");
            rooms = extras.getBoolean("rooms");
        }
        switch(generationAlgorithm)
        {
            case "Boruvka" :
                theMazeBuilder = Order.Builder.Boruvka;
                break;
            case "Prim" :
                theMazeBuilder = Order.Builder.Prim;
                break;
            case "DFS" :
                theMazeBuilder = Order.Builder.DFS;
        }

        // initialize local variables
        mazeFactory = new MazeFactory();
        mazeSeed = 24;
        mazeOrder = new DefaultOrder(mazeDifficulty, theMazeBuilder, !rooms, mazeSeed);
        theMaze = null;
        driver = null;
        robotConfig = null;

        // Connect progress bar object of the layout to the global variable of this activity
        myProgressBar = (ProgressBar)findViewById(progressBarLL);
        myProgressBar.setProgress(0);

        backgroundThread = new Thread(this);
        backgroundThread.start();
    }

    /*
    Sets the driver Variable to Manual
     */
    public void selectDriverManual(View v)
    {
        driver = "Manual";
        if(myProgressBar.getProgress() == 100){
            this.switchToManualPlayingActivity(v);
        }
    }

    /*
    Sets the driver Variable to Wallfollower
     */
    public void selectDriverWallFollower(View v)
    {
        driver = "WallFollower";
        if(myProgressBar.getProgress() == 100 && robotConfig != null)
        {
            this.switchToAnimationPlayingActivity(v);
        }
    }

    /*
    Sets the driver Variable to Wizard
     */
    public void selectDriverWizard(View v)
    {
        driver = "Wizard";
        if(myProgressBar.getProgress() == 100 && robotConfig != null)
        {
            this.switchToAnimationPlayingActivity(v);
        }
    }

    /*
    Sets the robotConfig Variable to Premium
     */
    public void selectConfigPremium(View v)
    {
        robotConfig = "Premium";
        if(myProgressBar.getProgress() == 100 && driver != null){
            this.switchToAnimationPlayingActivity(v);
        }
    }

    /*
    Sets the robotConfig Variable to Mediocre
     */
    public void selectConfigMediocre(View v)
    {
        robotConfig = "Mediocre";
        if(myProgressBar.getProgress() == 100 && driver != null){
            this.switchToAnimationPlayingActivity(v);
        }
    }

    /*
    Sets the robotConfig Variable to Soso
     */
    public void selectConfigSoso(View v)
    {
        robotConfig = "Soso";
        if(myProgressBar.getProgress() == 100 && driver != null){
            this.switchToAnimationPlayingActivity(v);
        }
    }

    /*
    Sets the robotConfig Variable to Shaky
     */
    public void selectConfigShaky(View v)
    {
        robotConfig = "Shaky";
        if(myProgressBar.getProgress() == 100 && driver != null){
            this.switchToAnimationPlayingActivity(v);
        }
    }

    /*
    When back arrow pressed return to main menu state
     */
    @Override
    public void onBackPressed()
    {
        Intent mainMenuState = new Intent(GeneratingActivity.this, AMazeActivity.class);

        // Kill the current order being made
        mazeFactory.cancel();
        backgroundThread.interrupt();

        // Log the switch
        Log.v( "Bundle", "Switching from GeneratingActivity to AMazeActivity");

        // Initiate Switch
        startActivity(mainMenuState);
    }

    /*
    Switches to the PlayManuallyActivity Layout
     */
    public void switchToManualPlayingActivity(View v)
    {
        // Create a new Intent for switching to the generating activity
        // Store the user selections for transport to the generating activity
        Intent manualPlayState = new Intent(GeneratingActivity.this, PlayManuallyActivity.class);
        manualPlayState.putExtra("driver", driver);

        // Log the switch
        Log.v( "Bundle", "Switching from GeneratingActivity to PlayManuallyActivity");

        // Initiate Switch
        startActivity(manualPlayState);
    }

    /*
    Switches to the PlayManuallyActivity Layout
     */
    public void switchToManualPlayingActivity()
    {
        // Create a new Intent for switching to the generating activity
        // Store the user selections for transport to the generating activity
        Intent manualPlayState = new Intent(GeneratingActivity.this, PlayManuallyActivity.class);
        manualPlayState.putExtra("driver", driver);

        // Log the switch
        Log.v( "Bundle", "Switching from GeneratingActivity to PlayManuallyActivity");

        // Initiate Switch
        startActivity(manualPlayState);
    }



    /*
    Switches to the PlayAnimationActivity Layout
     */
    public void switchToAnimationPlayingActivity(View v)
    {
        // Create a new Intent for switching to the generating activity
        // Store the user selections for transport to the generating activity
        Intent animationPlayState = new Intent(GeneratingActivity.this, PlayAnimationActivity.class);
        animationPlayState.putExtra("driver", driver);
        animationPlayState.putExtra("robotConfig", robotConfig);

        // Log the switch
        Log.v( "Bundle", "Switching from GeneratingActivity to PlayAnimationActivity");

        // Initiate Switch
        startActivity(animationPlayState);
    }

    /*
    Switches to the PlayAnimationActivity Layout
     */
    public void switchToAnimationPlayingActivity()
    {
        // Create a new Intent for switching to the generating activity
        // Store the user selections for transport to the generating activity
        Intent animationPlayState = new Intent(GeneratingActivity.this, PlayAnimationActivity.class);
        animationPlayState.putExtra("driver", driver);
        animationPlayState.putExtra("robotConfig", robotConfig);

        // Log the switch
        Log.v( "Bundle", "Switching from GeneratingActivity to PlayAnimationActivity");

        // Initiate Switch
        startActivity(animationPlayState);
    }

    @Override
    public void run()
    {
        // Place the order
        mazeFactory.order(mazeOrder);

        // Update the progress bar while the order is being completed
        while(theMaze == null && myProgressBar.getProgress() < 100)
        {
            myProgressBar.setProgress(mazeOrder.getProgress());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // store the new maze once delivered
        mazeFactory.waitTillDelivered();
        theMaze = mazeOrder.getMaze();

        // switch to the next activity if selections have been made
        if(driver == "Manual")
        {
            this.switchToManualPlayingActivity();
        }
        else if(driver != null)
        {
            if(robotConfig != null)
            {
                this.switchToAnimationPlayingActivity();
            }
        }
    }
}