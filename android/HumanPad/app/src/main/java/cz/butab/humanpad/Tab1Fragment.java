package cz.butab.humanpad;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cz.butab.humanpad.HIDKeyCodes;

import java.io.UnsupportedEncodingException;


public class Tab1Fragment extends Fragment implements View.OnClickListener {

    public Button btnUp;
    public Button btnDown;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_tab1, container, false);
        btnUp = (Button) myView.findViewById(R.id.btnUp);
        btnUp.setOnClickListener(this);


        btnDown = (Button) myView.findViewById(R.id.btnDown);
        btnDown.setOnClickListener(this);

        return myView;
    }

    @Override
    public void onClick(View v) {
        Log.d("onClick", "volam");
        switch (v.getId()) {
            case R.id.btnDown:
                sendKeyAction(HIDKeyCodes.Char_S, KeyAction.KeyClick);
                break;
                
            case R.id.btnUp:
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
