package haowei.computer.goldbowl.ui.sign;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import haowei.computer.goldbowl.R;
import haowei.computer.goldbowl.base.BaseFragment;
import haowei.computer.goldbowl.util.EditTextHintUtils;
import haowei.computer.goldbowl.util.MyUtils;
import haowei.computer.goldbowl.util.RxUtils;
import rx.Single;

/**
 * Created by Administrator on 2016/12/21.
 */

public class ForgetPasswordSecondFragment extends BaseFragment {
    public static final String PHONE="phone";
    public static final String CAPTCHA="captcha";
    public static final String IDENTITY="identity";
    private static final String TAG ="ForgetPasswordSecondFragment" ;
    private String mPhone;
    private String mCaptcha;
    private String mIdentity;
    @BindView(R.id.root_view)View rootView;
    @BindView(R.id.input_password)EditText inputPsd;
    @BindView(R.id.input_password_affirm)EditText inputAffPsd;
    @Override
    protected int getContentView() {
        return R.layout.fragment_forget_password_second;
    }

    @Override
    protected void updateUI() {
        //监听密码输入框
        EditTextHintUtils.setPasswordErrorHint(inputPsd, getActivity());
        EditTextHintUtils.setAffPasswordHint(inputAffPsd, inputPsd, getActivity());

    }

    public static ForgetPasswordSecondFragment newInstance(String phone,String captcha,String identity) {

        ForgetPasswordSecondFragment fragment = new ForgetPasswordSecondFragment();
        if (!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(captcha)&&!TextUtils.isEmpty(identity)) {
            Bundle args = new Bundle();
            args.putString(PHONE,phone);
            args.putString(CAPTCHA,captcha);
            args.putString(IDENTITY,identity);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            mPhone = getArguments().getString(PHONE);
            mCaptcha=getArguments().getString(CAPTCHA);
            mIdentity=getArguments().getString(IDENTITY);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @OnClick({R.id.back_left,R.id.btn_affirm})public void onClick(View view){
        String password= MyUtils.getString(inputPsd);
        String affPassword=MyUtils.getString(inputAffPsd);
        switch (view.getId()){
            case R.id.back_left:
                onBackPressed();
                break;
            case R.id.btn_affirm:
             boolean isResult= checkInputStyle(password,affPassword);
                if (isResult){
                    //调接口把账号密码验证码身份证传到后台进行校验

                    //检验成果后跳转到登录界面，把账号传到登录界面
                    MyUtils.showSnackbar(rootView,R.string.modify_success_password_show);
                    LoginFragment fragment=LoginFragment.newInstance(mPhone);
                    Single.just("").delay(2, TimeUnit.SECONDS).compose(RxUtils.applySchedulers()).subscribe(s -> {
                        fragmentMgr.beginTransaction()
                                .addToBackStack(TAG)
                                .replace(R.id.fragment_login_container, fragment)
                                .commit();

                    });

                }




                break;
        }

    }

    private boolean checkInputStyle(String password, String affPassword) {
        boolean isChecked=true;
        if (TextUtils.isEmpty(password)){
            MyUtils.showSnackbar(rootView,R.string.input_password_empty_show);
            isChecked=false;

        }
        if (isChecked&&password.length()>20||password.length()<6){
            MyUtils.showSnackbar(rootView,R.string.input_password_format_show);
            isChecked=false;
        }
        if (isChecked&&TextUtils.isEmpty(affPassword)){
            MyUtils.showSnackbar(rootView,R.string.input_affirm_password_empty_show);
            isChecked=false;
        }
        if (isChecked&&!affPassword.equals(password)){
            MyUtils.showSnackbar(rootView,R.string.hint_password_same_twice_error);
            inputAffPsd.setText("");
            isChecked=false;
        }

        return isChecked;

    }

}
