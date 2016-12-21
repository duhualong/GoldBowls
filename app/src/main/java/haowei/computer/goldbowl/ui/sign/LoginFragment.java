package haowei.computer.goldbowl.ui.sign;

import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import haowei.computer.goldbowl.R;
import haowei.computer.goldbowl.base.BaseFragment;
import haowei.computer.goldbowl.util.MyUtils;

/**
 * Created by Administrator on 2016/12/21.
 * 登录页面
 */

public class LoginFragment extends BaseFragment {
    private static final String TAG = "LoginFragment";
    @BindView(R.id.root_view)
    View rootView;
    @BindView(R.id.input_phone)
    EditText inputPhone;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.checkbox_password)CheckBox checkPwd;

    @Override
    protected int getContentView() {
        return R.layout.fragment_login;
    }

    @Override
    protected void updateUI() {

    }

    @OnClick({R.id.checkbox_password, R.id.btn_login, R.id.tv_forget_password, R.id.tv_register_now})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkbox_password:
                if (checkPwd.isChecked()){
                    inputPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else {
                    inputPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                MyUtils.setSelection(inputPassword);
                break;
            case R.id.btn_login:

                break;
            case R.id.tv_forget_password:
                fragmentMgr.beginTransaction().addToBackStack(TAG)
                        .replace(R.id.fragment_login_container, new ForgetPasswordFirstFragment())
                        .commit();
                break;
            case R.id.tv_register_now:
                //注册第一步
                fragmentMgr.beginTransaction()
                        .addToBackStack(TAG)
                        .replace(R.id.fragment_login_container, new RegisterFirstFragment())
                        .commit();
                break;

        }
    }
}
