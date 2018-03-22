package info.upump.jym.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import info.upump.jym.entity.TypeMuscle;
import info.upump.jym.fragments.exercises.ExerciseListFragmentForViewPager;

/**
 * Created by explo on 13.03.2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    private TypeMuscle[] typeMuscles;
    private Context context;
    private Map<Integer,Fragment> muscleFragmentMap = new HashMap<>();

    public Map<Integer, Fragment> getMuscleFragmentMap() {
        return muscleFragmentMap;
    }

    public void setMuscleFragmentMap(Map<Integer, Fragment> muscleFragmentMap) {
        this.muscleFragmentMap = muscleFragmentMap;
    }

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
        muscleFragmentMap.put(position,fragment);
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

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        muscleFragmentMap.remove(position);
    }
}
