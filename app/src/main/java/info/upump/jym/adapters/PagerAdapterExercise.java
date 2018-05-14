package info.upump.jym.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import info.upump.jym.entity.TypeMuscle;
import info.upump.jym.fragments.exercises.ExerciseListFragmentForViewPager;
import info.upump.jym.fragments.exercises.TabChanger;


public class PagerAdapterExercise extends FragmentStatePagerAdapter {
    protected TypeMuscle[] typeMuscles = TypeMuscle.values();
    protected Context context;
    private TabChanger tabChanger;
//    private static Fragment[] fragments = new Fragment[12];

    public PagerAdapterExercise(FragmentManager fm) {
        super(fm);
    }

    public PagerAdapterExercise(FragmentManager fm, Context context, TabChanger tabChanger) {
        super(fm);
        this.context = context;
        this.tabChanger = tabChanger;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = ExerciseListFragmentForViewPager.newInstance(typeMuscles[position]);
//        fragments[position]= fragment;
        ((ExerciseListFragmentForViewPager) fragment).tabChanger = tabChanger;
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

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

   /* public Fragment getMItem(int poz){
        System.out.println(poz);
        System.out.println(fragments[poz]);
        return  fragments[poz];
    }*/

}
