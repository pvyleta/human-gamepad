package cz.butab.humanpad;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Tab3Fragment extends Fragment implements SensorEventListener {
    enum JumpState {
        NO_JUMP,
        GOING_UP,
        GOING_DOWN,
        LANDING
    }

    private Sensor accelerometerSensor;
    private SensorManager SM;

    private static final float GRAVITY_THRESHOLD = 7.0f;
    private static final float UPPER_GRAVITY_THRESHOLD = 20.0f;
    private static final float LOWER_GRAVITY_THRESHOLD = 8.0f;
    private static final long TIME_THRESHOLD_NS = 3000000000L; // in nanoseconds (= 2sec)
    private long mEventBeginning = 0;
    private JumpState mJumpState = JumpState.NO_JUMP;
    private int mJumpCounter = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create out Sensor Manager
        SM = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        // Accelerometer sensor
        accelerometerSensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register sensor listener
        SM.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab3, container, false);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        detectJump(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not in use
    }

    private void detectJump(SensorEvent event) {
//        Log.d("JumpState", "Jump state is " + mJumpState.toString());

        double velocity = Math.sqrt(Math.pow(event.values[0],2) +
                Math.pow(event.values[1],2) +
                Math.pow(event.values[2],2));

//        Log.d("velocity", "velocity: " + Double.toString(velocity));

        // check if an ongoing event hasn't passed a time limit, otherwise cancel it
        if ((mJumpState != JumpState.NO_JUMP) && hasTimeoutOccurred(event.timestamp, mEventBeginning))
        {
            // check iF accelerometer is stabilized, otherwise wait
            if (isStable(velocity))
            {
                mJumpState = JumpState.NO_JUMP;
                mEventBeginning = 0;
            }

            return;
        }

        // check transfer conditions to next jump state
        switch (mJumpState)
        {
            case NO_JUMP:
                if (Math.abs(velocity) > UPPER_GRAVITY_THRESHOLD)
                {
                    mEventBeginning = event.timestamp;
                    mJumpState = JumpState.GOING_UP;
                }
                break;
            case GOING_UP:
                if (Math.abs(velocity) < LOWER_GRAVITY_THRESHOLD)
                {
                    mJumpState = JumpState.GOING_DOWN;
                }
                break;
            case GOING_DOWN:
                if (Math.abs(velocity) > 15.0f)
                {
                    mJumpState = JumpState.LANDING;
                    onJumpDetected();
                }
                break;
            case LANDING:
                if (isStable(velocity))
                {
                    mEventBeginning = 0;
                    mJumpState = JumpState.NO_JUMP;
                }
                break;
        }
    }

    private void onJumpDetected() {
        mJumpCounter++;
    }

    private boolean isStable(double velocity)
    {
        return ((Math.abs(velocity) > 7.0f) && (Math.abs(velocity) < 11.0f));
    }

    private boolean hasTimeoutOccurred(long currentTimestamp, long eventBeginning)
    {
        return ((currentTimestamp - eventBeginning) > TIME_THRESHOLD_NS);
    }

}
