package info.upump.jym.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import info.upump.jym.entity.TypeMuscle;
import info.upump.jym.fragments.exercises.ExerciseListFragmentForViewPager;
import info.upump.jym.fragments.exercises.TabChanger;


public class PagerAdapterExercise extends FragmentStatePagerAdapter {
    protected TypeMuscle[] typeMuscles = TypeMuscle.values();
    protected Context context;

    public PagerAdapterExercise(FragmentManager fm) {
        super(fm);
    }

    public PagerAdapterExercise(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = ExerciseListFragmentForViewPager.newInstance(typeMuscles[position]);
        return fragment;
    }

    @Override
    public int getCount() {
        return typeMuscles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return  context.getResources().getString(typeMuscles[position].getName());
    }

}
