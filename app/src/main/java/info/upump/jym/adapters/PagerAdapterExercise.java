//package info.upump.jym.adapters;
//
//import android.content.Context;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentStatePagerAdapter;
//
//import info.upump.jym.entity.TypeMuscle;
//import info.upump.jym.fragments.exercises.ExerciseListFragmentForViewPager;
//import info.upump.jym.fragments.exercises.TabChanger;
//
//
//public class PagerAdapterExercise extends FragmentStatePagerAdapter {
//    protected TypeMuscle[] typeMuscles = TypeMuscle.values();
//    protected Context context;
//    private TabChanger tabChanger;
////    private static Fragment[] fragments = new Fragment[12];
//
//    public PagerAdapterExercise(FragmentManager fm) {
//        super(fm);
//    }
//
//    public PagerAdapterExercise(FragmentManager fm, Context context, TabChanger tabChanger) {
//        super(fm);
//        this.context = context;
//        this.tabChanger = tabChanger;
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        Fragment fragment = ExerciseListFragmentForViewPager.newInstance(typeMuscles[position]);
////        fragments[position]= fragment;
//        ((ExerciseListFragmentForViewPager) fragment).tabChanger = tabChanger;
//        return fragment;
//    }
//
//    @Override
//    public int getCount() {
//        return typeMuscles.length;
//    }
//
//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return  context.getResources().getString(typeMuscles[position].getName());
//    }
//
//    @Override
//    public int getItemPosition(@NonNull Object object) {
//        return POSITION_NONE;
//    }
//
//   /* public Fragment getMItem(int poz){
//        System.out.println(poz);
//        System.out.println(fragments[poz]);
//        return  fragments[poz];
//    }*/
//
//}
