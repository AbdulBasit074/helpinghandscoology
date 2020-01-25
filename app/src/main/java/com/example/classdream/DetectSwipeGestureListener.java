package com.example.classdream;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public class DetectSwipeGestureListener extends GestureDetector.SimpleOnGestureListener {

    // Minimal x and y axis swipe distance.
    private static int MIN_SWIPE_DISTANCE_X = 100;
    private static int MIN_SWIPE_DISTANCE_Y = 100;

    // Maximal x and y axis swipe distance.
    private static int MAX_SWIPE_DISTANCE_X = 1000;
    private static int MAX_SWIPE_DISTANCE_Y = 1000;

    // Source activity that display message in text view.
    private Class_Home activity = null;

    public Class_Home getActivity() {
        return activity;
    }

    public void setActivity(Class_Home activity) {
        this.activity = activity;
    }

    /* This method is invoked when a swipe gesture happened. */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        // Get swipe delta value in x axis.
        float deltaX = e1.getX() - e2.getX();

        // Get swipe delta value in y axis.
        float deltaY = e1.getY() - e2.getY();

        // Get absolute value.
        float deltaXAbs = Math.abs(deltaX);
        float deltaYAbs = Math.abs(deltaY);

        // Only when swipe distance between minimal and maximal distance value then we treat it as effective swipe
        if((deltaXAbs >= MIN_SWIPE_DISTANCE_X) && (deltaXAbs <= MAX_SWIPE_DISTANCE_X))
        {
            if(deltaX > 0)
            {
                Toast.makeText(activity.getApplicationContext()," Swap to Left ",Toast.LENGTH_LONG).show();
            }else
            {
                Toast.makeText(activity.getApplicationContext()," Swap to right ",Toast.LENGTH_LONG).show();
            }
        }

        if((deltaYAbs >= MIN_SWIPE_DISTANCE_Y) && (deltaYAbs <= MAX_SWIPE_DISTANCE_Y))
        {
            if(deltaY > 0)
            {
                Toast.makeText(activity.getApplicationContext()," Swap to up ",Toast.LENGTH_LONG).show();

            }else
            {
                Toast.makeText(activity.getApplicationContext()," Swap to Down ",Toast.LENGTH_LONG).show();

            }
        }


        return true;
    }

    // Invoked when single tap screen.
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Toast.makeText(activity.getApplicationContext()," Single Tab occured ",Toast.LENGTH_LONG).show();

        return true;
    }

    // Invoked when double tap screen.
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Toast.makeText(activity.getApplicationContext()," Double tab Occured ",Toast.LENGTH_LONG).show();
        return true;

    }
}