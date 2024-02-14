// PagerAdapter.java
package com.example.b_unitconverter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new lengthFragment();
            case 1:
                return new dataFragment();
            case 2:
                return new timeFragment();
            case 3:
                return new massFragment();
            case 4:
                return new volumeFragment();
            case 5:
                return new temperatureFragment();
            case 6:
                 return new tipFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {

        return 7;
    }

    @Override
    public CharSequence getPageTitle(int position) {
               switch (position) {
            case 0:
                return "Length";
            case 1:
                return "Data";
            case 2:
                return "Time";
            case 3:
                return "Mass";
            case 4:
                return "Volume";

            case 5:
                return "Temperature";
            case 6:
                return "Tip";


            default:
                return null;
        }
    }
}
