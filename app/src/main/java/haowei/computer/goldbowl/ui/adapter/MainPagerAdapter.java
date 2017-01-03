package haowei.computer.goldbowl.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import haowei.computer.goldbowl.ui.view.fragment.HomePagerFragment;
import haowei.computer.goldbowl.ui.view.fragment.PersonalPagerFragment;

/**
 * Created by Administrator on 2017/01/03.
 */

public class MainPagerAdapter extends SmarterPagerAdapter {
    private static final int PAGE_COUNT = 4;
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return  new HomePagerFragment();

            case 1:
                return new PersonalPagerFragment();

            case 2:
                return  new HomePagerFragment();
            case 3:
                return new PersonalPagerFragment();

            default:
                return new HomePagerFragment();
        }

    }

    @Override public int getCount() {
        return PAGE_COUNT;
    }
}
