package haowei.computer.goldbowl.ui.sign;

import android.view.View;

import butterknife.OnClick;
import haowei.computer.goldbowl.R;
import haowei.computer.goldbowl.base.BaseFragment;

/**
 * Created by Administrator on 2016/12/21.
 */

public class ForgetPasswordFirstFragment extends BaseFragment {

    private static final String TAG ="ForgetPasswordFirstFragment";

    @Override
    protected int getContentView() {
        return R.layout.fragment_forget_password_first;
    }

    @Override
    protected void updateUI() {

    }
    @OnClick({R.id.back_left,R.id.btn_get_code,R.id.btn_next})public void onClick(View view){
        switch (view.getId()){
            case R.id.back_left:
                onBackPressed();
                break;
            case R.id.btn_get_code:

                break;
            case R.id.btn_next:
                fragmentMgr.beginTransaction()
                        .addToBackStack(TAG)
                        .replace(R.id.fragment_login_container, new ForgetPasswordSecondFragment())
                        .commit();
                break;
        }
    }
}
