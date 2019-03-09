package cz.butab.humanpad;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.UnsupportedEncodingException;


public class Tab5Fragment extends Fragment implements View.OnTouchListener {

    private ImageButton btnUp;
    private ImageButton btnDown;
    private ImageButton btnLeft;
    private ImageButton btnRight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_tab5, container, false);

        btnUp = (ImageButton) myView.findViewById(R.id.arrowUp);
        btnUp.setOnTouchListener(this);


        btnDown = (ImageButton) myView.findViewById(R.id.arrowDown);
        btnDown.setOnTouchListener(this);

        btnLeft = (ImageButton) myView.findViewById(R.id.arrowLeft);
        btnLeft.setOnTouchListener(this);

        btnRight = (ImageButton) myView.findViewById(R.id.arrowRight);
        btnRight.setOnTouchListener(this);

//        btnTlacitko = (Button) myView.findViewById(R.id.tlacitko);
//        btnTlacitko.setOnTouchListener(this);

        return myView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if ((event.getAction() == MotionEvent.ACTION_DOWN) || (event.getAction() == MotionEvent.ACTION_UP)) {
            Log.d("onClick", "tlacim " + event);
            switch (v.getId()) {
                case R.id.arrowUp:
                    sendKeyAction(KeyMapper.Tab5.ArrowUpKey, KeyAction.from(event));
                    break;

                case R.id.arrowDown:
                    sendKeyAction(KeyMapper.Tab5.ArrowDownKey, KeyAction.from(event));
                    break;

                case R.id.arrowLeft:
                    sendKeyAction(KeyMapper.Tab5.ArrowLeftKey, KeyAction.from(event));
                    break;

                case R.id.arrowRight:
                    sendKeyAction(KeyMapper.Tab5.ArrowRightKey, KeyAction.from(event));
                    break;

                default:
                    break;
            }
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
