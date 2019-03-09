package com.example.hackathontest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.widget.TextView;

enum JumpState {
    NO_JUMP,
    GOING_UP,
    GOING_DOWN,
    LANDING
}

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView xText, yText, zText, jumpCounter;
    private Sensor accelerometerSensor;
    private Sensor gravitySensor;
    private SensorManager SM;

    private static final float GRAVITY_THRESHOLD = 7.0f;
    private static final float UPPER_GRAVITY_THRESHOLD = 20.0f;
    private static final float LOWER_GRAVITY_THRESHOLD = 8.0f;
    private static final long TIME_THRESHOLD_NS = 3000000000L; // in nanoseconds (= 2sec)
    private long mEventBeginning = 0;
    private JumpState mJumpState = JumpState.NO_JUMP;
    private int mJumpCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create out Sensor Manager
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        // Accelerometer sensor
        accelerometerSensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gravitySensor = SM.getDefaultSensor(Sensor.TYPE_GRAVITY);


        // Register sensor listener
        SM.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);

        // Assign our text views
        xText = (TextView)findViewById(R.id.xText);
        yText = (TextView)findViewById(R.id.yText);
        zText = (TextView)findViewById(R.id.zText);
        jumpCounter = (TextView)findViewById(R.id.jumpCounter);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        xText.setText("X: " + event.values[0]);
        yText.setText("Y: " + event.values[1]);
        zText.setText("Z: " + event.values[2]);

        detectJump(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not in use
    }

    private void detectJump(SensorEvent event) {
        Log.d("JumpState", "Jump state is " + mJumpState.toString());

        double velocity = Math.sqrt(Math.pow(event.values[0],2) +
                Math.pow(event.values[1],2) +
                Math.pow(event.values[2],2));

        Log.d("velocity", "velocity: " + Double.toString(velocity));

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
        jumpCounter.setText(Integer.toString(mJumpCounter));
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
