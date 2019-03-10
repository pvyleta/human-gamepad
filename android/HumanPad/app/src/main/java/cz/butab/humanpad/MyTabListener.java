package cz.butab.humanpad;

import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.ViewPagerOnTabSelectedListener;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.support.v4.app.Fragment;


public class MyTabListener extends ViewPagerOnTabSelectedListener implements TabLayout.OnTabSelectedListener {

    public MyTabListener(ViewPager inputViewPager) {
        super(inputViewPager);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        super.onTabSelected(tab);
        Log.i("POSITION", String.valueOf(tab.getPosition()));
        Constants.TAB = tab.getPosition();

        MakeRequest mkReq = new MakeRequest("http://192.168.10.10:8000");
        mkReq.sendKeyAction(KeyMapper.Tab1.ArrowDownKey, KeyAction.KeyRelease);
        mkReq.sendKeyAction(KeyMapper.Tab1.ArrowUpKey, KeyAction.KeyRelease);
        mkReq.sendKeyAction(KeyMapper.Tab2.ArrowRightKey, KeyAction.KeyRelease);
        mkReq.sendKeyAction(KeyMapper.Tab2.ArrowLeftKey, KeyAction.KeyRelease);
        mkReq.sendKeyAction(KeyMapper.Tab3.FireKey, KeyAction.KeyRelease);
        mkReq.sendKeyAction(KeyMapper.Tab4.JumpKey, KeyAction.KeyRelease);
        mkReq.sendKeyAction(KeyMapper.Tab5.ArrowDownKey, KeyAction.KeyRelease);
        mkReq.sendKeyAction(KeyMapper.Tab5.ArrowLeftKey, KeyAction.KeyRelease);
        mkReq.sendKeyAction(KeyMapper.Tab5.ArrowRightKey, KeyAction.KeyRelease);
        mkReq.sendKeyAction(KeyMapper.Tab5.ArrowUpKey, KeyAction.KeyRelease);
        mkReq.sendKeyAction(KeyMapper.Tab6.ButtonAKey, KeyAction.KeyRelease);
        mkReq.sendKeyAction(KeyMapper.Tab6.ButtonBKey, KeyAction.KeyRelease);
        mkReq.sendKeyAction(KeyMapper.Tab6.ButtonCKey, KeyAction.KeyRelease);
        mkReq.sendKeyAction(KeyMapper.Tab6.ButtonDKey, KeyAction.KeyRelease);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}