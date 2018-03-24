package info.upump.jym.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import info.upump.jym.activity.cycle.fragments.CycleFragmentForViewPagerDescription;
import info.upump.jym.activity.cycle.fragments.CycleFragmentForViewPagerDescriptionEdit;
import info.upump.jym.entity.Cycle;


public class PagerAdapterCycleEdit extends PagerAdapterCycle {
    public PagerAdapterCycleEdit(FragmentManager fm, Cycle cycle) {
        super(fm, cycle);
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = CycleFragmentForViewPagerDescription.newInstance(cycle);
        System.out.println(1);
        switch (position) {
            case 0:
                fragment = CycleFragmentForViewPagerDescriptionEdit.newInstance(cycle);
                break;
         /*   case 1:
                fragment = CycleFragmentForViewPagerWorkoutsEdit.newInstance(cycle);*/
        }
        return fragment;
    }

}

