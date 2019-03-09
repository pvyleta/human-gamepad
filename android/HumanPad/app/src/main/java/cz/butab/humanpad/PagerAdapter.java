package cz.butab.humanpad;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;


public class PagerAdapter extends FragmentStatePagerAdapter {
//    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
//        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        Log.d("TAB", "Zmena tabu");
        Log.d("TAB", String.valueOf(position));

        Constants.TAB = position;

        switch (position) {
            case 0:
                Tab1Fragment tab1 = new Tab1Fragment();
                Log.d("TAB", "prepinam 1");
                return tab1;
            case 1:
                Tab2Fragment tab2 = new Tab2Fragment();
                Log.d("TAB", "prepinam 2");
                return tab2;
            case 2:
                Tab3Fragment tab3 = new Tab3Fragment();
                Log.d("TAB", "prepinam 3");
                return tab3;
            case 3:
                Tab4Fragment tab4 = new Tab4Fragment();
                Log.d("TAB", "prepinam 4");
                return tab4;
            case 4:
                Tab5Fragment tab5 = new Tab5Fragment();
                Log.d("TAB", "prepinam 5");
                return tab5;
            case 5:
                Tab6Fragment tab6 = new Tab6Fragment();
                Log.d("TAB", "prepinam 6");
                return tab6;
            default:
                Tab1Fragment tab11 = new Tab1Fragment();
                Log.d("TAB", "default");
                return tab11;
        }
    }

    @Override
    public int getCount() {
        return 6;
    }
}