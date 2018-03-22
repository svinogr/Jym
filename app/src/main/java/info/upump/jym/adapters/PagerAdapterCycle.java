package info.upump.jym.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import info.upump.jym.activity.workout.fragments.CycleFragmentForViewPagerDescription;

/**
 * Created by explo on 22.03.2018.
 */

public class PagerAdapterCycle extends FragmentStatePagerAdapter {
    private String[] tabs = new String[] {"Описание", "Упражнения"};

    public PagerAdapterCycle(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment =  CycleFragmentForViewPagerDescription.newInstance();
        System.out.println(1);
        switch (position) {
            case 0:
                fragment = CycleFragmentForViewPagerDescription.newInstance();
                break;
          /*  case 1:
                fragment = WorkoutFragmentForViewPagerExercises.newInstace();*/
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
