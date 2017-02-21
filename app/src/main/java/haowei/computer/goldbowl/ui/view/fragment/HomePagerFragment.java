package haowei.computer.goldbowl.ui.view.fragment;



import com.yyydjk.library.BannerLayout;

import java.util.Arrays;


import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import haowei.computer.goldbowl.R;
import haowei.computer.goldbowl.base.BaseSupportFragment;

/**
 * Created by Administrator on 2017/01/03.
 */

public class HomePagerFragment extends BaseSupportFragment {



    private Integer[] ids = {R.drawable.ic_banner_first, R.drawable.ic_banner_second, R.drawable.ic_banner_third, R.drawable.ic_banner_fourth};  //  int viewRes=
    @BindView(R.id.banner)
    BannerLayout bannerLayout;
    private BGARefreshLayout mRefreshLayout;

    @Override
    protected int getContentView() {
        return R.layout.fragment_test_home_pager;
    }

    @Override
    protected void updateUI() {
        //初始化banner数据
        bannerLayout.setViewRes(Arrays.asList(ids));

        //getInternetData();


    }

    @Override
    public void onResume() {
        super.onResume();
        //initRefreshLayout(mRefreshLayout);
    }


//
//    private void getInternetData() {
//        //本地数据
//        bannerLayout.setViewRes(Arrays.asList(ids));
//        //点击事件
//        bannerLayout.setOnBannerItemClickListener(i -> {
//            switch (i){
//                case 0:
//                    System.out.println("打印banner位置"+i);
//
//                    break;
//
//                case 1:
//                    System.out.println("打印banner位置"+i);
//                    break;
//
//                case 2:
//                    System.out.println("打印banner位置"+i);
//                    break;
//                case 3:
//                    System.out.println("打印banner位置"+i);
//                    break;
//            }
//
//        });
//    }
//    private void initRefreshLayout(BGARefreshLayout mRefreshLayout) {
//        mRefreshLayout= (BGARefreshLayout) getActivity().findViewById(R.id.mRefreshLayout);
//
//        // 为BGARefreshLayout 设置代理
//        mRefreshLayout.setDelegate(this);
//        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
//        BGARefreshViewHolder refreshViewHolder = new BGAMeiTuanRefreshViewHolder(context, true);
//        // 设置下拉刷新和上拉加载更多的风格
//        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
//
//
//        // 为了增加下拉刷新头部和加载更多的通用性，提供了以下可选配置选项  -------------START
//        // 设置正在加载更多时不显示加载更多控件
//        // mRefreshLayout.setIsShowLoadingMoreView(false);
//        // 设置正在加载更多时的文本
//        refreshViewHolder.setLoadingMoreText("正在加载...");
//        // 设置整个加载更多控件的背景颜色资源 id
//        refreshViewHolder.setLoadMoreBackgroundColorRes(R.color.colorPrimary);
//        // 设置整个加载更多控件的背景 drawable 资源 id
//        refreshViewHolder.setLoadMoreBackgroundDrawableRes(R.drawable.ic_logo);
//        // 设置下拉刷新控件的背景颜色资源 id
//        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.white);
//        // 设置下拉刷新控件的背景 drawable 资源 id
//        refreshViewHolder.setRefreshViewBackgroundDrawableRes(R.drawable.ic_experience_gold);
//        // 设置自定义头部视图（也可以不用设置）     参数1：自定义头部视图（例如广告位）， 参数2：上拉加载更多是否可用
//      //  mRefreshLayout.setCustomHeaderView(mBanner, false);
//        // 可选配置  -------------END
//    }
//
//
//    @Override
//    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
//    }
//
//    @Override
//    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
//        return false;
//    }



}
