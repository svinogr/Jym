package info.upump.jym.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import info.upump.jym.entity.TypeMuscle;
import info.upump.jym.fragments.ExerciseFragment;
import info.upump.jym.fragments.ExerciseListFragmentForViewPager;

/**
 * Created by explo on 13.03.2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    private TypeMuscle[] typeMuscles;
    private Context context;

    public PagerAdapter(FragmentManager fm, TypeMuscle[] typeMuscles) {
        super(fm);
        this.typeMuscles = typeMuscles;
    }

    public PagerAdapter(FragmentManager fm, TypeMuscle[] typeMuscles, Context context) {
        this(fm,typeMuscles);
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

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(typeMuscles[position].getName());
    }
}
