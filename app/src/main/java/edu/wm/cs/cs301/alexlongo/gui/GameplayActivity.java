package edu.wm.cs.cs301.alexlongo.gui;

public interface GameplayActivity
{
    public void switchToWinningActivity() ;

    public void incrementPathLength(int steps) ;

    void switchToLosingActivity();

    void adjustSensors(boolean flipper);
}
