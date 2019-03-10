package cz.butab.humanpad;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Tab3Fragment extends Fragment implements SensorEventListener {
    enum JumpState {
        NO_JUMP,
        GOING_UP,
        GOING_DOWN,
        LANDING,
        COOLDOWN
    }

    private Sensor accelerometerSensor;
    private SensorManager sensorManager;
    private MakeRequest request;
    private JumpState mJumpState = JumpState.NO_JUMP;
    private long mEventBeginning = 0;
    private long mEventCooldownBeginning = 0;
    private static final float GOING_UP_GRAVITY_THRESHOLD = 20.0f;
    private static final float GOING_DOWN_GRAVITY_THRESHOLD = 8.0f;
    private static final float LANDING_GRAVITY_THRESHOLD = 15.0f;
    private static final float STABLE_LOWER_GRAVITY_THRESHOLD = 7.0f;
    private static final float STABLE_UPPER_GRAVITY_THRESHOLD = 11.0f;
    private static final long MAX_EVENT_TIME_NS = 2000000000L; // in nanoseconds (= 2sec)
    private static final long EVENT_COOLDOWN_TIME_NS = 200000000L; // in nanoseconds (= 0.2sec)
//    private ImageView icon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        request = new MakeRequest(getString(R.string.urlweb));

        // Create out Sensor Manager
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        // Accelerometer sensor
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register sensor listener
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);
//        icon = view.findViewById(R.id.ballMove);
//        icon.setY(Constants.HIGH/2);

        // Inflate the layout for this fragment
        return view;


    }
    
    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if(Constants.TAB == 2) {
            detectJump(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not in use
    }

    private void detectJump(SensorEvent event) {
        double velocity = Math.sqrt(Math.pow(event.values[0],2) +
                Math.pow(event.values[1],2) +
                Math.pow(event.values[2],2));

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
                if (Math.abs(velocity) > GOING_UP_GRAVITY_THRESHOLD)
                {
                    mEventBeginning = event.timestamp;
                    mJumpState = JumpState.GOING_UP;
                }
                break;
            case GOING_UP:
                if (Math.abs(velocity) < GOING_DOWN_GRAVITY_THRESHOLD)
                {
                    mJumpState = JumpState.GOING_DOWN;
                }
                break;
            case GOING_DOWN:
                if (Math.abs(velocity) > LANDING_GRAVITY_THRESHOLD)
                {
                    mJumpState = JumpState.LANDING;
                    request.sendKeyAction(KeyMapper.Tab3.FireKey, KeyAction.KeyPressed);
                }
                break;
            case LANDING:
                request.sendKeyAction(KeyMapper.Tab3.FireKey, KeyAction.KeyRelease);
                if (isStable(velocity))
                {
                    mEventCooldownBeginning = event.timestamp;
                    mEventBeginning = 0;
                    mJumpState = JumpState.COOLDOWN;
                }
                break;
            case COOLDOWN:
                if ((event.timestamp - mEventCooldownBeginning) > EVENT_COOLDOWN_TIME_NS)
                {
                    mEventCooldownBeginning = 0;
                    mJumpState = JumpState.NO_JUMP;
                }
                break;
        }
    }

    private boolean isStable(double velocity)
    {
        return ((Math.abs(velocity) > STABLE_LOWER_GRAVITY_THRESHOLD) &&
                (Math.abs(velocity) < STABLE_UPPER_GRAVITY_THRESHOLD));
    }

    private boolean hasTimeoutOccurred(long currentTimestamp, long eventBeginning)
    {
        return ((currentTimestamp - eventBeginning) > MAX_EVENT_TIME_NS);
    }
}
