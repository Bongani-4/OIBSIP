// PagerAdapter.java
package com.example.b_unitconverter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.b_unitconverter.CentiimetreFragment;
import com.example.b_unitconverter.FeetFragment;
import com.example.b_unitconverter.InchesFragment;
import com.example.b_unitconverter.KilometreFragment;
import com.example.b_unitconverter.MIllimetresFragment;
import com.example.b_unitconverter.MetreFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new CentiimetreFragment();
            case 1:
                return new MIllimetresFragment();
            case 2:
                return new MetreFragment();
            case 3:
                return new KilometreFragment();
            case 4:
                return new InchesFragment();
            case 5:
                return new FeetFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {

        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
               switch (position) {
            case 0:
                return "Centimeters";
            case 1:
                return "Millimeters";
            case 2:
                return "Meters";
            case 3:
                return "Kilometers";
            case 4:
                return "Inches";

            case 5:
                return "Feet";
            default:
                return null;
        }
    }
}
