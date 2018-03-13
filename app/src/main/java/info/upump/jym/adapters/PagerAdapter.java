package info.upump.jym.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import info.upump.jym.fragments.BlankFragmentTest;

/**
 * Created by explo on 13.03.2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int count;

    public PagerAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = BlankFragmentTest.newInstance(String.valueOf(position));
        return fragment;
    }

    @Override
    public int getCount() {
        return count;
    }
}
