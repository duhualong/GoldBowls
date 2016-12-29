package haowei.computer.goldbowl.ui.sign;


import android.app.Fragment;
import android.os.Build;

import haowei.computer.goldbowl.R;
import haowei.computer.goldbowl.base.BaseActivity;
import haowei.computer.goldbowl.util.StatusBarUtil;

/**
 * Created by Administrator on 2016/12/19.
 * 登录注册的容器
 */

public class LoginActivity  extends BaseActivity{

    @Override
    protected int getContentView() {
        //设置顶部导航栏图标为黑色
      //  StatusBarUtil.StatusBarLightMode(LoginActivity.this);


        return R.layout.activity_login;
    }

    @Override
    protected void updateUI() {
       // 设置状态栏黑色字体图标，
       // * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
        StatusBarUtil.StatusBarLightMode(LoginActivity.this);

        Fragment fragment=fragmentMgr.findFragmentById(R.id.fragment_login_container);
        if (fragment == null) {
            //登录页面
            fragment = new LoginFragment();
            fragmentMgr.beginTransaction().add(R.id.fragment_login_container, fragment).commit();
        }
    }



}
