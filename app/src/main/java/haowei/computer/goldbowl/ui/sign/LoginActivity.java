package haowei.computer.goldbowl.ui.sign;


import android.app.Fragment;

import haowei.computer.goldbowl.R;
import haowei.computer.goldbowl.base.BaseActivity;

/**
 * Created by Administrator on 2016/12/19.
 * 登录注册的容器
 */

public class LoginActivity  extends BaseActivity{

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void updateUI() {
        Fragment fragment=fragmentMgr.findFragmentById(R.id.fragment_login_container);
        if (fragment == null) {
            //登录页面
            fragment = new LoginFragment();
            fragmentMgr.beginTransaction().add(R.id.fragment_login_container, fragment).commit();
        }
    }



}
