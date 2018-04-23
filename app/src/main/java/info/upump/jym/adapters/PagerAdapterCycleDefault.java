package info.upump.jym.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import info.upump.jym.activity.cycle.fragments.CycleFragmentForViewPagerDescription;
import info.upump.jym.activity.cycle.fragments.CycleFragmentForViewPagerWorkoutDefault;
import info.upump.jym.activity.cycle.fragments.CycleFragmentForViewPagerWorkouts;
import info.upump.jym.entity.Cycle;

/**
 * Created by explo on 06.04.2018.
 */

public class PagerAdapterCycleDefault extends PagerAdapterCycle {

    public PagerAdapterCycleDefault(FragmentManager fm, Cycle cycle, Context context) {
        super(fm, cycle, context);
    }

    @Override
    protected Fragment getFragment(int poz){
        Fragment fragment = null;
        switch (poz) {
            case 1:
                fragment = CycleFragmentForViewPagerDescription.newInstance(cycle);
                break;
            case 0:
                fragment = CycleFragmentForViewPagerWorkoutDefault.newInstance(cycle);
        }
        return fragment;
    }


}
