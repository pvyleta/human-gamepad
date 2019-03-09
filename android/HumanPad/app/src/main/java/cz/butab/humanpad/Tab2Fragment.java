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
        import android.widget.ImageView;

        import java.util.Vector;

public class Tab2Fragment extends Fragment implements SensorEventListener {

    private MakeRequest mkReq;
    private ImageView ball;

    Vector<Float> lastTenValues;

    private Sensor mySensor;
    private SensorManager SM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_tab2, container, false);
        mkReq = new MakeRequest(getString(R.string.urlweb));

        // Create out Sensor Manager
        SM = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);

        // Accelerometer sensor
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register sensor listener
        SM.registerListener(this,mySensor, SensorManager.SENSOR_DELAY_GAME);

        ball = (ImageView) myView.findViewById(R.id.ballSide);

        ball.setX(Constants.WIDTH/2);

        lastTenValues = new Vector<Float>(10);

        return myView;
    }


    public void onSensorChanged(SensorEvent event) {

        lastTenValues.add(event.values[0]);

        if(lastTenValues.size() == 10)
        {
            float sum = 0;
            for(float i : lastTenValues)
            {
                sum+=i;
            }

            ball.setX((float) ((2*Constants.WIDTH/6.0)*(((sum/10.0)/9.81 + 1))));

            lastTenValues.remove(lastTenValues.firstElement());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Not used
    }

//    @Override
//    public void onClick(View v) {
//        Log.d("onClick", "volam");
//        switch (v.getId()) {
//            case R.id.btnDown:
//                mkReq.sendKeyAction(KeyMapper.Tab1.ArrowDownKey, KeyAction.KeyClick);
//                break;
//
//            case R.id.btnUp:
//                mkReq.sendKeyAction(KeyMapper.Tab1.ArrowUpKey, KeyAction.KeyClick);
//                break;
//
//            default:
//                break;
//        }
//    }
}
