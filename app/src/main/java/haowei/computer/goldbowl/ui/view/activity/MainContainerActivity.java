package haowei.computer.goldbowl.ui.view.activity;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import haowei.computer.goldbowl.R;
import haowei.computer.goldbowl.base.BaseActivity;
import haowei.computer.goldbowl.ui.adapter.MainPagerAdapter;

/**
 * Created by Administrator on 2017/01/03.
 */

public class MainContainerActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    private static final int NAVIGATOR_COUNT = 4;
    @BindView(R.id.root_container)View rootView;
    @BindView(R.id.fragment_container)
    ViewPager pagerContainer;
    @BindViews({R.id.tv_home,R.id.tv_money,R.id.tv_code,R.id.tv_my})List<TextView> navigators;
    //默认图标
    int[] navigatorDrawablesNormal = {
            R.drawable.ic_home_default, R.drawable.ic_money_default, R.drawable.ic_code_default,
           R.drawable.ic_my_default
    };
    //选中图标
    int[] navigatorDrawablesSelect = {
            R.drawable.ic_home_select , R.drawable.ic_money_select, R.drawable.ic_code_select,
        R.drawable.ic_my_select
    };
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void updateUI() {
        updatePagerContainer();
        setCurrentNavigator(0);

    }
    @OnClick({R.id.tv_home,R.id.tv_money,R.id.tv_code,R.id.tv_my})public void onNavigatorClick(View view){
        int pageIndex = 0;

        switch (view.getId()){
            case R.id.tv_home:
                pageIndex=0;
                break;
            case R.id.tv_money:
                pageIndex=1;
                break;
            case R.id.tv_code:
                pageIndex=2;
                break;
            case R.id.tv_my:
                pageIndex=3;
                break;
        }
        setCurrentNavigator(pageIndex);
        pagerContainer.setCurrentItem(pageIndex);
    }
///滑动切换fargment
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setCurrentNavigator(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void updatePagerContainer() {
        MainPagerAdapter adapter = new MainPagerAdapter(supportFragmentMgr);
        pagerContainer.setAdapter(adapter);
        pagerContainer.addOnPageChangeListener(this);
    }
    private void setCurrentNavigator(int index) {
        refreshNavigator();
        navigators.get(index)
                .setCompoundDrawablesWithIntrinsicBounds(0, navigatorDrawablesSelect[index], 0, 0);
            navigators.get(index).setTextColor(ContextCompat.getColor(MainContainerActivity.this, R.color.colorPrimary));


    }
    private void refreshNavigator() {
        for (int i = 0; i < NAVIGATOR_COUNT; i++) {
            navigators.get(i)
                    .setCompoundDrawablesWithIntrinsicBounds(0, navigatorDrawablesNormal[i], 0, 0);
            navigators.get(i).setTextColor(ContextCompat.getColor(MainContainerActivity.this, R.color.gray_deep));
        }
    }
}
