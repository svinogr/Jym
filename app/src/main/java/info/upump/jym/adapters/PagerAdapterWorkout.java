package info.upump.jym.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import info.upump.jym.R;
import info.upump.jym.activity.workout.fragments.WorkoutFragmentForViewPagerDescription;
import info.upump.jym.activity.workout.fragments.WorkoutFragmentForViewPagerExercises;
import info.upump.jym.entity.Workout;


public class PagerAdapterWorkout extends FragmentStatePagerAdapter {
    protected String[] tabs ;
    protected Workout workout;

    public PagerAdapterWorkout(FragmentManager fm, Workout workout, Context context) {
        super(fm);
        this.workout = workout;
        this.tabs = new String[]{context.getResources().getString(R.string.tab_exercise), context.getResources().getString(R.string.tab_description)};
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
