package info.upump.jym.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import info.upump.jym.activity.cycle.fragments.CycleFragmentForViewPagerDescription;
import info.upump.jym.activity.cycle.fragments.CycleFragmentForViewPagerWorkouts;
import info.upump.jym.activity.workout.fragments.WorkoutFragmentForViewPagerDescription;
import info.upump.jym.activity.workout.fragments.WorkoutFragmentForViewPagerExercises;
import info.upump.jym.entity.Cycle;
import info.upump.jym.entity.Workout;

/**
 * Created by explo on 22.03.2018.
 */

public class PagerAdapterWorkout extends FragmentStatePagerAdapter {
    protected String[] tabs = new String[]{"Упражнения", "Описание"};
    protected Workout workout;

    public PagerAdapterWorkout(FragmentManager fm, Workout workout) {
        super(fm);
        this.workout = workout;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        System.out.println(1);
        switch (position) {
            case 1:
                fragment = WorkoutFragmentForViewPagerDescription.newInstance(workout);
                break;
            case 0:
               // fragment = WorkoutFragmentForViewPagerDescription.newInstance(workout);
                fragment = WorkoutFragmentForViewPagerExercises.newInstance(workout);
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
