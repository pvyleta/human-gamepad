package cz.butab.humanpad;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class Tab1Fragment extends Fragment implements View.OnClickListener {

    public ImageView btnUp;
    public ImageView btnDown;

    private MakeRequest mkReq = new MakeRequest(getString(R.string.urlweb));

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
            case R.id.btnDown:
                mkReq.sendKeyAction(KeyMapper.Tab1.ArrowDownKey, KeyAction.KeyClick);
                break;
                
            case R.id.btnUp:
                mkReq.sendKeyAction(KeyMapper.Tab1.ArrowUpKey, KeyAction.KeyClick);
                break;

            default:
                break;
        }
    }
}
