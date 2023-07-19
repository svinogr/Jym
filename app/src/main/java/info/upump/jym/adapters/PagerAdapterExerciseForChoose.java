//package info.upump.jym.adapters;
//
//import android.content.Context;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//
//import info.upump.jym.activity.exercise.ExerciseListFragmentForViewPagerChoose;
//
//
//public class PagerAdapterExerciseForChoose extends PagerAdapterExercise {
//    public PagerAdapterExerciseForChoose(FragmentManager fm, Context context) {
//        super(fm, context, null);
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        Fragment fragment = ExerciseListFragmentForViewPagerChoose.newInstance(typeMuscles[position]);
//        return fragment;
//    }
//}
