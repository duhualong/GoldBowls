package haowei.computer.goldbowl.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/01/03.
 */

public abstract class SmarterPagerAdapter extends FragmentStatePagerAdapter {
    private SparseArray<Fragment> fragments = new SparseArray<>(4);

    public SmarterPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fragments.put(position, fragment);
        return fragment;
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object) {
        fragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }
}
