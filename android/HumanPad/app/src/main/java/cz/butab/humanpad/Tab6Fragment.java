package cz.butab.humanpad;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.UnsupportedEncodingException;


public class Tab6Fragment extends Fragment implements View.OnClickListener {

    public ImageView btnA;
    public ImageView btnB;
    public ImageView btnC;
    public ImageView btnD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_tab6, container, false);

        btnA = (ImageView) myView.findViewById(R.id.btnA);
        btnA.setOnClickListener(this);


        btnB = (ImageView) myView.findViewById(R.id.btnB);
        btnB.setOnClickListener(this);

        btnC = (ImageView) myView.findViewById(R.id.btnC);
        btnC.setOnClickListener(this);

        btnD = (ImageView) myView.findViewById(R.id.btnD);
        btnD.setOnClickListener(this);

        return myView;
    }

    @Override
    public void onClick(View v) {
        Log.d("onClick", "volam");
        switch (v.getId()) {
            case R.id.btnA:
                sendKeyAction(HIDKeyCodes.Char_S, KeyAction.KeyClick);
                break;
                
            case R.id.btnB:
                sendKeyAction(HIDKeyCodes.Char_W, KeyAction.KeyClick);
                break;

            case R.id.btnC:
                sendKeyAction(HIDKeyCodes.Char_S, KeyAction.KeyClick);
                break;

            case R.id.btnD:
                sendKeyAction(HIDKeyCodes.Char_W, KeyAction.KeyClick);
                break;

            default:
                break;
        }
    }

    private void sendKeyAction(final int keycode, final String keyAction) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Do network action in this function
                try {
                    Log.d("onClick", "pripravuji");
                    makeRequest.putJSON(getString(R.string.urlweb), keycode, "player1", keyAction);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
