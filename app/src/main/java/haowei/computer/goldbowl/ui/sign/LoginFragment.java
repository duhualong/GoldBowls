package haowei.computer.goldbowl.ui.sign;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import haowei.computer.goldbowl.R;
import haowei.computer.goldbowl.base.BaseFragment;

import haowei.computer.goldbowl.test.MainActivity;
import haowei.computer.goldbowl.ui.SplashActivity;
import haowei.computer.goldbowl.ui.view.activity.MainContainerActivity;
import haowei.computer.goldbowl.util.Constants;
import haowei.computer.goldbowl.util.MyUtils;
import haowei.computer.goldbowl.util.RxUtils;
import rx.Single;

/**
 * Created by Administrator on 2016/12/21.
 * 登录页面
 */

public class LoginFragment extends BaseFragment {
    private static final String ACCOUNT_REGISTER = "account_register";
    private static final String TAG = "LoginFragment";
    private String account;
    @BindView(R.id.root_view)
    View rootView;
    @BindView(R.id.input_phone)
    EditText inputPhone;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.checkbox_password)
    CheckBox checkPwd;
    @BindView(R.id.input_error_tip)
    TextView errorTip;
    @BindView(R.id.btn_login)Button btnLogin;

    @Override
    protected int getContentView() {
        return R.layout.fragment_login;
    }

    @Override
    protected void updateUI() {

        if (!TextUtils.isEmpty(account)) {
            inputPhone.setText(account);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            account = getArguments().getString(ACCOUNT_REGISTER);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public static LoginFragment newInstance(String account) {
        LoginFragment fragment = new LoginFragment();
        if (!TextUtils.isEmpty(account)) {
            Bundle args = new Bundle();
            args.putString(ACCOUNT_REGISTER, account);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @OnClick({R.id.checkbox_password, R.id.btn_login, R.id.tv_forget_password, R.id.tv_register_now})
    public void onClick(View view) {
        String mPhone = MyUtils.getString(inputPhone);
        String mPassword = MyUtils.getString(inputPassword);
        switch (view.getId()) {
            case R.id.checkbox_password:
                MyUtils.setShowHide(checkPwd, inputPassword);

                break;
            case R.id.btn_login:
                //验证账号密码是否正确
                boolean result = checkInputContent(mPhone, mPassword);
                if (result) {
                    startActivity(new Intent(getActivity(), MainContainerActivity.class));
                    getActivity().finish();
                }
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

    private boolean checkInputContent(String phone, String password) {
        boolean checked = true;
        if (TextUtils.isEmpty(phone)) {
            checked=false;
            MyUtils.errorShow(errorTip, R.string.input_phone_show, btnLogin);

        }
        if (checked&&!MyUtils.isMobile(phone)){
            checked=false;
            MyUtils.errorShow(errorTip, R.string.phone_format_show, btnLogin);
        }
        if (checked&&TextUtils.isEmpty(password)){
            checked=false;
            MyUtils.errorShow(errorTip, R.string.error_password_empty, btnLogin);
        }
        if (checked&&password.length()>20||(password.length()>0&&password.length()<6)){
            checked=false;
            MyUtils.errorShow(errorTip, R.string.error_password_format, btnLogin);
        }
        //调接口检验根据返回的错误信息进行展示



        return checked;
    }


}
