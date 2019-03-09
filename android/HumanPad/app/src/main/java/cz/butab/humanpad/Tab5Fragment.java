package cz.butab.humanpad;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.UnsupportedEncodingException;


public class Tab5Fragment extends Fragment implements View.OnTouchListener {

    private ImageView btnUp;
    private ImageView btnDown;
    private ImageView btnLeft;
    private ImageView btnRight;
    private Button btnTlacitko;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_tab5, container, false);

        btnUp = (ImageView) myView.findViewById(R.id.arrowUp);
        btnUp.setOnTouchListener(this);


        btnDown = (ImageView) myView.findViewById(R.id.arrowDown);
        btnDown.setOnTouchListener(this);

        btnLeft = (ImageView) myView.findViewById(R.id.arrowLeft);
        btnLeft.setOnTouchListener(this);

        btnRight = (ImageView) myView.findViewById(R.id.arrowRight);
        btnRight.setOnTouchListener(this);

//        btnTlacitko = (Button) myView.findViewById(R.id.tlacitko);
//        btnTlacitko.setOnTouchListener(this);

        return myView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("onClick", "tlacim " + event);


        switch (v.getId()) {
            case R.id.arrowUp:
                sendKeyAction(KeyMapper.Tab6.ButtonAKey, KeyAction.from(event));
                break;

            case R.id.arrowDown:
                sendKeyAction(KeyMapper.Tab6.ButtonBKey, KeyAction.from(event));
                break;

            case R.id.arrowLeft:
                sendKeyAction(KeyMapper.Tab6.ButtonCKey, KeyAction.from(event));
                break;

            case R.id.arrowRight:
                sendKeyAction(KeyMapper.Tab6.ButtonDKey, KeyAction.from(event));
                break;

            default:
                break;
        }


        return false;
    }

    private void sendKeyAction(final int keycode, final String keyAction) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Do network action in this function
                try {
                    Log.d("onClick", "pripravuji");
                    MakeRequest.putJSON(getString(R.string.urlweb), keycode, "player1", keyAction);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
