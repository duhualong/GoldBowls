package haowei.computer.goldbowl.ui.view.fragment;

import android.view.View;
import android.widget.ImageView;

import com.yyydjk.library.BannerLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import haowei.computer.goldbowl.R;
import haowei.computer.goldbowl.base.BaseSupportFragment;

/**
 * Created by Administrator on 2017/01/03.
 */

public class HomePagerFragment extends BaseSupportFragment {

    private Integer[] ids = {R.drawable.ic_banner_first, R.drawable.ic_banner_second, R.drawable.ic_banner_third, R.drawable.ic_banner_fourth};  //  int viewRes=
    @BindView(R.id.banner)
    BannerLayout bannerLayout;

    @Override
    protected int getContentView() {
        return R.layout.fragment_home_pager;
    }

    @Override
    protected void updateUI() {
        //初始化banner数据
        getInternetData();

    }

    private void getInternetData() {
        bannerLayout.setViewRes(Arrays.asList(ids));
//本地数据

    }
}
