package info.upump.jym.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import info.upump.jym.activity.exercise.ExerciseListFragmentForViewPagerChoose;


public class PagerAdapterExerciseForChoose extends PagerAdapterExercise {
    public PagerAdapterExerciseForChoose(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = ExerciseListFragmentForViewPagerChoose.newInstance(typeMuscles[position]);
        return fragment;
    }
}
