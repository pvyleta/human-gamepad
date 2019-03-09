package cz.butab.humanpad;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.UnsupportedEncodingException;


public class Tab1Fragment extends Fragment implements View.OnClickListener {

    public ImageView btnUp;
    public ImageView btnDown;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_tab1, container, false);
        btnUp = (ImageView) myView.findViewById(R.id.btnDown);
        btnUp.setOnClickListener(this);


        btnDown = (ImageView) myView.findViewById(R.id.btnUp);
        btnDown.setOnClickListener(this);

        return myView;
    }

    @Override
    public void onClick(View v) {
        Log.d("onClick", "volam");
        switch (v.getId()) {
            case R.id.btnUp:
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            // Do network action in this function
                            try {
                                Log.d("onClick", "pripravuji");
                                //makeRequest.dataSyncSent(getString(R.string.urlweb), "w"); //deprecated
                                makeRequest.putJSON(getString(R.string.urlweb), 22,"player1", "toggle");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                break;
            case R.id.btnDown:
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        // Do network action in this function
                        try {
                            Log.d("onClick", "pripravuji");
                            //makeRequest.dataSyncSent("w", getString(R.string.urlweb)); //deprecated
                            makeRequest.putJSON(getString(R.string.urlweb),26,"player1", "toggle");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                break;
            default:
                break;
        }
    }
}
