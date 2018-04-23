package info.upump.jym.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import info.upump.jym.activity.workout.fragments.WorkoutFragmentForViewPagerDescription;
import info.upump.jym.activity.workout.fragments.WorkoutFragmentForViewPagerExercises;
import info.upump.jym.activity.workout.fragments.WorkoutFragmentForViewPagerExercisesDefault;
import info.upump.jym.entity.Workout;

/**
 * Created by explo on 06.04.2018.
 */

public class PagerAdapterWorkoutDefault extends PagerAdapterWorkout {
    public PagerAdapterWorkoutDefault(FragmentManager fm, Workout workout, Context context) {
        super(fm, workout,context);
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
                fragment = WorkoutFragmentForViewPagerExercisesDefault.newInstance(workout);
        }
        return fragment;
    }
}
