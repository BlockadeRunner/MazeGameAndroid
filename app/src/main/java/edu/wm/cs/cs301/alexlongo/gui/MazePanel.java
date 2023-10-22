package edu.wm.cs.cs301.alexlongo.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class MazePanel extends View implements P7PanelF22
{
    ////////////////////////////////////////////////////////////
    //              Class instance variables                  //
    ////////////////////////////////////////////////////////////

    // For drawing
    private Bitmap theBitmap;
    private Canvas theCanvas;
    private Paint thePaint;
    private int theRGBColorSetting;

    // Create Constants for Colors
    static final int ANDROID_YELLOW = Color.YELLOW;
    static final int ANDROID_GREEN = Color.GREEN;
    static final int ANDROID_BLACK = Color.BLACK;
    static final int ANDROID_GRAY = Color.GRAY;
    static final int ANDROID_LIGHT_GRAY = Color.LTGRAY;
    static final int ANDROID_WHITE = Color.WHITE;
    static final int ANDROID_RED = Color.RED;
    static final int ANDROID_BLUE = Color.BLUE;

    // For custom view dimensions
    private int customViewWidth;
    private int customViewHeight;

    public MazePanel(Context context)
    {
        super(context);

        // Import the correct width and height
        this.setCustomViewWidthAndHeight(context);

        // Setup the bitmap
        theBitmap = Bitmap.createBitmap(customViewWidth, customViewHeight, Bitmap.Config.ARGB_8888);

        // Setup the Canvas
        theCanvas = new Canvas(theBitmap);

        // Setup the Paint
        thePaint = new Paint();
    }

    public MazePanel(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        // Import the correct width and height
        this.setCustomViewWidthAndHeight(context);

        // Setup the bitmap
        theBitmap = Bitmap.createBitmap(customViewWidth, customViewHeight, Bitmap.Config.ARGB_8888);

        // Setup the Canvas
        theCanvas = new Canvas(theBitmap);

        // Setup the Paint
        thePaint = new Paint();
    }

    public MazePanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        // Import the correct width and height
        setCustomViewWidthAndHeight(context);

        // Setup the bitmap
        theBitmap = Bitmap.createBitmap(customViewWidth, customViewHeight, Bitmap.Config.ARGB_8888);

        // Setup the Canvas
        theCanvas = new Canvas(theBitmap);

        // Setup the Paint
        thePaint = new Paint();
    }

    /**
     * Sets the private class variables to the proper width and height of the custom view
     */
    public void setCustomViewWidthAndHeight(Context context)
    {
        ////////////////////// OLD, NON-FUNCTIONAL CODE /////////////////////
        // Convert the layout width and height from dp to pixels
        // float density_val = context.getResources().getDisplayMetrics().density;
        // int pixel_val = (int) (500 * density_val);

        // Set the width and height variables
        customViewHeight = 1000;
        customViewWidth = 1000;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Testing and Debugging:
        // setColor(ANDROID_RED);
        //addFilledRectangle(0, 0, 100, 100);
        // addBackground(55);
        //int[] xPoints = new int[]{30, 40, 80, 60, 300};
        //int[] yPoints = new int[]{300, 60, 50, 40, 30};
        //addFilledPolygon(xPoints, yPoints, 5);
        canvas.drawBitmap(theBitmap, 0, 0, thePaint);
        Log.v("MazePanel", "Drawing on the view");

        theBitmap = Bitmap.createBitmap(customViewWidth, customViewHeight, Bitmap.Config.ARGB_8888);
        theCanvas = new Canvas(theBitmap);
    }

    /**
     * Commits all accumulated drawings to the UI.
     * Substitute for MazePanel.update method.
     */
    @Override
    public void commit()
    {
        invalidate();
    }

    /**
     * Tells if instance is able to draw. This ability depends on the
     * context, for instance, in a testing environment, drawing
     * may be not possible and not desired.
     * Substitute for code that checks if graphics object for drawing is not null.
     * @return true if drawing is possible, false if not.
     */
    @Override
    public boolean isOperational()
    {
        if (theCanvas == null)
        {
            Log.e("MazePanel", "Canvas not ready for drawing, MazePanel not operational!");
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Sets the color for future drawing requests. The color setting
     * will remain in effect till this method is called again and
     * with a different color.
     * Substitute for Graphics.setColor method.
     * @param argb gives the alpha, red, green, and blue encoded value of the color
     */
    @Override
    public void setColor(int argb)
    {
        theRGBColorSetting = argb;
        thePaint.setColor(argb);
    }

    /**
     * Returns the ARGB value for the current color setting.
     * @return integer ARGB value
     */
    @Override
    public int getColor()
    {
        return theRGBColorSetting;
    }

    /**
     * Draws two solid rectangles to provide a background.
     * Note that this also erases any previous drawings.
     * The color setting adjusts to the distance to the exit to
     * provide an additional clue for the user.
     * Colors transition from black to gold and from grey to green.
     * Substitute for FirstPersonView.drawBackground method.
     * @param percentToExit gives the distance to exit
     */
    @Override
    public void addBackground(float percentToExit)
    {
        // handle transition from black to gold and from grey to green
        if(percentToExit < 50.0)
        {
            // Draw the Sky/Ceiling (Upper Horizon)
            setColor(ANDROID_YELLOW);
            addFilledRectangle(0,0, customViewWidth, (customViewHeight/2));

            // Draw the Ground/Floor (Lower Horizon)
            setColor(ANDROID_GREEN);
            addFilledRectangle(0, (customViewHeight/2), customViewWidth, customViewHeight/2);
        }
        else
        {
            // Draw the Sky/Ceiling (Upper Horizon)
            setColor(ANDROID_BLACK);
            addFilledRectangle(0,0, customViewWidth, (customViewHeight/2));

            // Draw the Ground/Floor (Lower Horizon)
            setColor(ANDROID_GRAY);
            addFilledRectangle(0, (customViewHeight/2), customViewWidth, customViewHeight/2);
        }
    }

    /**
     * Adds a filled rectangle.
     * The rectangle is specified with the {@code (x,y)} coordinates
     * of the upper left corner and then its width for the
     * x-axis and the height for the y-axis.
     * Substitute for Graphics.fillRect() method
     * @param x is the x-coordinate of the top left corner
     * @param y is the y-coordinate of the top left corner
     * @param width is the width of the rectangle
     * @param height is the height of the rectangle
     */
    @Override
    public void addFilledRectangle(int x, int y, int width, int height)
    {
        thePaint.setStyle(Paint.Style.FILL);
        int rightCoord = x + width;
        int bottomCoord = y + height;
        theCanvas.drawRect(x, y, rightCoord, bottomCoord, thePaint);
    }

    /**
     * Adds a filled polygon.
     * The polygon is specified with {@code (x,y)} coordinates
     * for the n points it consists of. All x-coordinates
     * are given in a single array, all y-coordinates are
     * given in a separate array. Both arrays must have
     * same length n. The order of points in the arrays
     * matter as lines will be drawn from one point to the next
     * as given by the order in the array.
     * Substitute for Graphics.fillPolygon() method
     * @param xPoints are the x-coordinates of points for the polygon
     * @param yPoints are the y-coordinates of points for the polygon
     * @param nPoints is the number of points, the length of the arrays
     */
    @Override
    public void addFilledPolygon(int[] xPoints, int[] yPoints, int nPoints)
    {
        // Setup the paint object for painting walls
        thePaint.setStyle(Paint.Style.FILL);
        thePaint.setColor(ANDROID_LIGHT_GRAY);

        // Create a new path for the polygon
        Path thePolygonPath = new Path();

        // Check if there are points to be drawn
        boolean drawable = false;
        if(nPoints > 0)
        {
            drawable = true;
        }

        // Draw Points
        if(drawable)
        {
            thePolygonPath.moveTo(xPoints[0], yPoints[0]);

            // loop through each point and fill it
            for(int i = 1; i < nPoints; i++)
            {
                thePolygonPath.lineTo(xPoints[i], yPoints[i]);
            }

            // close the polygon path when finished
            thePolygonPath.close();
        }

        // add the drawn polygon to the canvas
        theCanvas.drawPath(thePolygonPath, thePaint);
    }

    /**
     * Adds a polygon.
     * The polygon is not filled.
     * The polygon is specified with {@code (x,y)} coordinates
     * for the n points it consists of. All x-coordinates
     * are given in a single array, all y-coordinates are
     * given in a separate array. Both arrays must have
     * same length n. The order of points in the arrays
     * matter as lines will be drawn from one point to the next
     * as given by the order in the array.
     * Substitute for Graphics.drawPolygon method
     * @param xPoints are the x-coordinates of points for the polygon
     * @param yPoints are the y-coordinates of points for the polygon
     * @param nPoints is the number of points, the length of the arrays
     */
    @Override
    public void addPolygon(int[] xPoints, int[] yPoints, int nPoints)
    {
        // Setup the paint object
        thePaint.setStyle(Paint.Style.STROKE);
        //thePaint.setColor(ANDROID_WHITE);

        // Create a new path for the polygon
        Path thePolygonPath = new Path();

        // Check if there are points to be drawn
        boolean drawable = false;
        if(nPoints > 0)
        {
            drawable = true;
        }

        // Draw Points
        if(drawable)
        {
            thePolygonPath.moveTo(xPoints[0], yPoints[0]);

            // loop through each point and fill it
            for(int i = 1; i < nPoints; i++)
            {
                thePolygonPath.lineTo(xPoints[i], yPoints[i]);
            }

            // close the polygon path when finished
            thePolygonPath.close();
        }

        // add the drawn polygon to the canvas
        theCanvas.drawPath(thePolygonPath, thePaint);
    }

    /**
     * Adds a line.
     * A line is described by {@code (x,y)} coordinates for its
     * starting point and its end point.
     * Substitute for Graphics.drawLine method
     * @param startX is the x-coordinate of the starting point
     * @param startY is the y-coordinate of the starting point
     * @param endX is the x-coordinate of the end point
     * @param endY is the y-coordinate of the end point
     */
    @Override
    public void addLine(int startX, int startY, int endX, int endY)
    {
        thePaint.setStyle(Paint.Style.STROKE);
        //thePaint.setColor(ANDROID_RED);
        theCanvas.drawLine(startX, startY, endX, endY, thePaint);
    }

    /**
     * Adds a filled oval.
     * The oval is specified with the {@code (x,y)} coordinates
     * of the upper left corner and then its width for the
     * x-axis and the height for the y-axis. An oval is
     * described like a rectangle.
     * Substitute for Graphics.fillOval method
     * @param x is the x-coordinate of the top left corner
     * @param y is the y-coordinate of the top left corner
     * @param width is the width of the oval
     * @param height is the height of the oval
     */
    @Override
    public void addFilledOval(int x, int y, int width, int height)
    {
        thePaint.setStyle(Paint.Style.FILL);
        //thePaint.setColor(ANDROID_BLUE);
        int rightCoord = x + width;
        int bottomCoord = y + height;
        theCanvas.drawOval(x, y, rightCoord, bottomCoord, thePaint);
    }

    /**
     * Adds the outline of a circular or elliptical arc covering the specified rectangle.
     * The resulting arc begins at startAngle and extends for arcAngle degrees,
     * using the current color. Angles are interpreted such that 0 degrees
     * is at the 3 o'clock position. A positive value indicates a counter-clockwise
     * rotation while a negative value indicates a clockwise rotation.
     * The center of the arc is the center of the rectangle whose origin is
     * (x, y) and whose size is specified by the width and height arguments.
     * The resulting arc covers an area width + 1 pixels wide
     * by height + 1 pixels tall.
     * The angles are specified relative to the non-square extents of
     * the bounding rectangle such that 45 degrees always falls on the
     * line from the center of the ellipse to the upper right corner of
     * the bounding rectangle. As a result, if the bounding rectangle is
     * noticeably longer in one axis than the other, the angles to the start
     * and end of the arc segment will be skewed farther along the longer
     * axis of the bounds.
     * Substitute for Graphics.drawArc method
     * @param x the x coordinate of the upper-left corner of the arc to be drawn.
     * @param y the y coordinate of the upper-left corner of the arc to be drawn.
     * @param width the width of the arc to be drawn.
     * @param height the height of the arc to be drawn.
     * @param startAngle the beginning angle.
     * @param arcAngle the angular extent of the arc, relative to the start angle.
     */
    @Override
    public void addArc(int x, int y, int width, int height, int startAngle, int arcAngle)
    {
        int rightCoord = x + width;
        int bottomCoord = y + height;
        theCanvas.drawArc(x, y, rightCoord, bottomCoord, startAngle, arcAngle, true, thePaint);
    }

    /**
     * Adds a string at the given position.
     * Substitute for CompassRose.drawMarker method
     * @param x the x coordinate
     * @param y the y coordinate
     * @param str the string
     */
    @Override
    public void addMarker(float x, float y, String str)
    {
        // Create a rectangle to act as a sort of textbox for the text to measure the coordinates
        Rect textBox = new Rect();

        // Setup the paint object using the textbox and string
        thePaint.setTextSize(50);
        thePaint.getTextBounds(str, 0, str.length(), textBox);

        // Adjust the x and y boundaries
        x = (float) (x - (textBox.width() / 2.0));
        y = (float) (y + (textBox.height() / 2.0));

        // Apply the text to the canvas
        theCanvas.drawText(str, x, y, thePaint);
    }

    /**
     * Sets the value of a single preference for the rendering algorithms.
     * It internally maps given parameter values into corresponding java.awt.RenderingHints
     * and assigns that to the internal graphics object.
     * Hint categories include controls for rendering quality
     * and overall time/quality trade-off in the rendering process.
     *
     * Refer to the awt RenderingHints class for definitions of some common keys and values.
     *
     * Note for Android: start with an empty default implementation.
     * Postpone any implementation efforts till the Android default rendering
     * results in unsatisfactory image quality.
     *
     * @param hintKey the key of the hint to be set.
     * @param hintValue the value indicating preferences for the specified hint category.
     */
    @Override
    public void setRenderingHint(P7RenderingHints hintKey, P7RenderingHints hintValue)
    {

    }



    void myTestImage(Canvas c)
    {

    }
}
