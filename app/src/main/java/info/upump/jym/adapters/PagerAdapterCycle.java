//package info.upump.jym.adapters;
//
//import android.content.Context;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentStatePagerAdapter;
//
//import info.upump.jym.R;
//import info.upump.jym.activity.cycle.fragments.CycleFragmentForViewPagerDescription;
//import info.upump.jym.activity.cycle.fragments.CycleFragmentForViewPagerWorkouts;
//import info.upump.jym.entity.Cycle;
//
//
//public class PagerAdapterCycle extends FragmentStatePagerAdapter {
//    protected String[] tabs;
//    protected Cycle cycle;
//    protected Context context;
//
//    public PagerAdapterCycle(FragmentManager fm, Cycle cycle, Context context) {
//        super(fm);
//        this.cycle = cycle;
//        this.context = context;
//        this.tabs = new String[]{context.getResources().getString(R.string.tab_workout), context.getResources().getString(R.string.tab_description)};
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//
//        return getFragment(position);
//    }
//
//    protected Fragment getFragment(int poz) {
//        Fragment fragment = null;
//        switch (poz) {
//            case 1:
//                fragment = CycleFragmentForViewPagerDescription.newInstance(cycle);
//                break;
//            case 0:
//                fragment = CycleFragmentForViewPagerWorkouts.newInstance(cycle);
//        }
//        return fragment;
//    }
//
//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return tabs[position];
//    }
//
//    @Override
//    public int getCount() {
//        return tabs.length;
//    }
//}
