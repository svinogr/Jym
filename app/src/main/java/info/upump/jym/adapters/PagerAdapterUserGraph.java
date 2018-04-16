/*
package info.upump.jym.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import info.upump.jym.activity.user.UserGraphFragmentForViewPager;
import info.upump.jym.entity.UserProgressEnum;

*/
/**
 * Created by explo on 12.04.2018.
 *//*


public class PagerAdapterUserGraph extends FragmentStatePagerAdapter {
    private Context context;
    private UserProgressEnum[] tab  = UserProgressEnum.values();

    public PagerAdapterUserGraph(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        System.out.println(position);
        //return null;
        return  UserGraphFragmentForViewPager.newInstance(tab[position]);
    }

    @Override
    public int getCount() {
        return tab.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString( tab[position].getName());
    }


}
*/
