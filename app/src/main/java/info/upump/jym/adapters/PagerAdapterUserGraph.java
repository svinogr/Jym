package info.upump.jym.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import info.upump.jym.activity.user.UserGraphFragmentForViewPager;
import info.upump.jym.entity.UserProgressEnum;

/**
 * Created by explo on 12.04.2018.
 */

public class PagerAdapterUserGraph extends FragmentStatePagerAdapter {
    private UserProgressEnum[] tab  = UserProgressEnum.values();

    public PagerAdapterUserGraph(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return  UserGraphFragmentForViewPager.newInstance(tab[position]);
    }

    @Override
    public int getCount() {
        return tab.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tab[position].toString();
    }


}
