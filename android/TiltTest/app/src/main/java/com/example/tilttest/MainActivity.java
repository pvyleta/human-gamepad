package com.example.tilttest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView xText, yText;
    private Button forward, back, left, right;
    private Sensor mySensor;
    private SensorManager SM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create out Sensor Manager
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        // Accelerometer sensor
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register sensor listener
        SM.registerListener(this,mySensor, SensorManager.SENSOR_DELAY_GAME);

        // Assign button
        forward = (Button)findViewById(R.id.forward);
        back = (Button)findViewById(R.id.back);
        left = (Button)findViewById(R.id.left);
        right = (Button)findViewById(R.id.right);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        int stepSize = 4;

        if(event.values[0] > stepSize)
        {
            right.setBackgroundColor(Color.LTGRAY);
        }else
        {
            right.setBackgroundColor(Color.WHITE);
        }

        if(event.values[0] < -stepSize)
        {
            left.setBackgroundColor(Color.LTGRAY);
         }else
        {
            left.setBackgroundColor(Color.WHITE);
        }

         if(event.values[1] > stepSize)
        {
            back.setBackgroundColor(Color.LTGRAY);
        }else
         {
             back.setBackgroundColor(Color.WHITE);
         }

        if(event.values[1] < -stepSize)
        {
            forward.setBackgroundColor(Color.LTGRAY);
        }else
        {
            forward.setBackgroundColor(Color.WHITE);
        }
    }
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not in use
    }
}

