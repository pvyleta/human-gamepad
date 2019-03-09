package cz.butab.humanpad;

import java.util.*;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class Tab1Fragment extends Fragment implements SensorEventListener {

    private MakeRequest mkReq;
    private ImageView ball;

    private Vector<Float> lastTenValues;

    private int state;

    private Sensor mySensor;
    private SensorManager SM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_tab1, container, false);
        mkReq = new MakeRequest(getString(R.string.urlweb));

        // Create out Sensor Manager
        SM = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);

        // Accelerometer sensor
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register sensor listener
        SM.registerListener(this,mySensor, SensorManager.SENSOR_DELAY_GAME);

        ball = (ImageView) myView.findViewById(R.id.ballMove);

        ball.setY(Constants.HIGH/2);

        lastTenValues = new Vector<Float>(10);

        state = 0;

        return myView;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(Constants.TAB == 1) {
            Log.i("Tab1", "onSensorChange");
            lastTenValues.add(event.values[1]);

            if (lastTenValues.size() == 10) {
                float sum = 0;
                for (float i : lastTenValues) {
                    sum += i;
                }

                float range = (float) (2 - ((sum / 10.0) / 9.81 + 1)); /// 0-2
                ball.setY((float) (Constants.HIGH / 30.0 + (2 * Constants.HIGH / 6.0) * (range)));

                if (range > 1.3) {
                    if (state != 1) {
                        mkReq.sendKeyAction(KeyMapper.Tab1.ArrowUpKey, KeyAction.KeyPressed);
                        state = 1;
                    }
                } else if (range < 0.7) {
                    if (state != 2) {
                        mkReq.sendKeyAction(KeyMapper.Tab1.ArrowDownKey, KeyAction.KeyPressed);
                        state = 2;
                    }
                } else {
                    if (state != 3) {
                        if (state == 2) {
                            mkReq.sendKeyAction(KeyMapper.Tab1.ArrowDownKey, KeyAction.KeyRelease);
                        }
                        if (state == 1) {
                            mkReq.sendKeyAction(KeyMapper.Tab1.ArrowUpKey, KeyAction.KeyRelease);
                        }
                        state = 3;
                    }
                }

                lastTenValues.remove(lastTenValues.firstElement());
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Not used
    }
}
