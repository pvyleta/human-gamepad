package cz.butab.humanpad;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout.ViewPagerOnTabSelectedListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.util.DisplayMetrics;
import android.util.Log;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;

public class MyTabListener extends ViewPagerOnTabSelectedListener implements TabLayout.OnTabSelectedListener {

    public MyTabListener(ViewPager inputViewPager) {
        super(inputViewPager);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        super.onTabSelected(tab);
        Log.i("POSITION", String.valueOf(tab.getPosition()));
        Constants.TAB = tab.getPosition();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}