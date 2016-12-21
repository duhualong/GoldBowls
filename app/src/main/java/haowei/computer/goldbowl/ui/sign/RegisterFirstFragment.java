package haowei.computer.goldbowl.ui.sign;

import android.view.View;

import butterknife.OnClick;
import haowei.computer.goldbowl.R;
import haowei.computer.goldbowl.base.BaseFragment;

/**
 * Created by Administrator on 2016/12/21.
 * 注册第一步
 */

public class RegisterFirstFragment extends BaseFragment {
    @Override
    protected int getContentView() {
        return R.layout.fragment_register_first;
    }

    @Override
    protected void updateUI() {

    }
    @OnClick({R.id.back_left,R.id.bt_register,R.id.tv_register_two,R.id.checkbox_password,R.id.checkbox_password_affirm})public void onClick(View view){
        switch (view.getId()){
            case R.id.back_left:
                onBackPressed();
                break;
            case R.id.bt_register:

                break;
            case R.id.tv_register_two:

                break;
            case R.id.checkbox_password:

                break;
            case R.id.checkbox_password_affirm:

                break;
        }
    }
}
