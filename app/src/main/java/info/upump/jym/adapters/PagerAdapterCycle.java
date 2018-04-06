package info.upump.jym.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import info.upump.jym.activity.cycle.fragments.CycleFragmentForViewPagerDescription;
import info.upump.jym.activity.cycle.fragments.CycleFragmentForViewPagerWorkouts;
import info.upump.jym.entity.Cycle;

/**
 * Created by explo on 22.03.2018.
 */

public class PagerAdapterCycle extends FragmentStatePagerAdapter {
    protected String[] tabs = new String[]{"Тренировки", "Описание"};
    protected Cycle cycle;

    public PagerAdapterCycle(FragmentManager fm, Cycle cycle) {
        super(fm);
        this.cycle = cycle;
    }

    @Override
    public Fragment getItem(int position) {

        return getFragment(position);
    }

    protected Fragment getFragment(int poz){
        Fragment fragment = null;
        switch (poz) {
            case 1:
                fragment = CycleFragmentForViewPagerDescription.newInstance(cycle);
                break;
            case 0:
                fragment = CycleFragmentForViewPagerWorkouts.newInstance(cycle);
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}
