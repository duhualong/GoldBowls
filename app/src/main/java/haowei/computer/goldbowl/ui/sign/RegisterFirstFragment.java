package haowei.computer.goldbowl.ui.sign;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import haowei.computer.goldbowl.util.EditTextHintUtils;
import haowei.computer.goldbowl.util.MyUtils;
import haowei.computer.goldbowl.util.RxUtils;
import rx.Single;

import static haowei.computer.goldbowl.util.Constants.COUNT_UNIT;
import static haowei.computer.goldbowl.util.Constants.NUM_COUNTDOWN;

/**
 * Created by Administrator on 2016/12/21.
 * 注册第一步
 */

public class RegisterFirstFragment extends BaseFragment {
    public static int yellow=0xffFFC000;

    public static final String PHONE="phone";
    private static final String TAG = "RegisterFirstFragment";
    private String mPhone;
    private CountDownTimer timer;
    private boolean isCounting;

    @BindView(R.id.tv_input_information)
    TextView inputInfo;
    @BindView(R.id.checkbox_user_agreement)
    CheckBox checked;
    @BindView(R.id.root_view)
    View rootView;
    @BindView(R.id.input_phone)
    EditText inputPhone;
    @BindView(R.id.input_code)
    EditText inputCode;
    @BindView(R.id.input_password)
    EditText inputPsd;
    @BindView(R.id.input_password_affirm)
    EditText inputAffirmPsd;
    @BindView(R.id.bt_send_code)
    Button btnSendCaptcha;
    @BindView(R.id.checkbox_password)
    CheckBox checkBoxPwd;
    @BindView(R.id.checkbox_password_affirm)
    CheckBox checkBoxAffPwd;


    @Override
    protected int getContentView() {
        return R.layout.fragment_register_first;
    }

    @Override
    protected void updateUI() {

        MyUtils.setTextStyle(inputInfo, getActivity());
        initUI();


    }

    public static RegisterFirstFragment newInstance(String phone) {
        RegisterFirstFragment fragment = new RegisterFirstFragment();
        if (!TextUtils.isEmpty(phone)) {
            Bundle args = new Bundle();
            args.putString(PHONE,phone);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            mPhone = getArguments().getString(PHONE);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initUI() {
        EditTextHintUtils.setPasswordErrorHint(inputPsd, getActivity());
        EditTextHintUtils.setAffPasswordHint(inputAffirmPsd, inputPsd, getActivity());
        MyUtils.setCheckBox(checkBoxPwd,inputPsd);
        MyUtils.setCheckBox(checkBoxAffPwd,inputAffirmPsd);

    }

    @OnClick({R.id.back_left, R.id.bt_register, R.id.tv_register_two, R.id.bt_send_code})
    public void onClick(View view) {
        String phone = EditTextHintUtils.getString(inputPhone, getActivity());
        String password = EditTextHintUtils.getString(inputPsd, getActivity());
        String affPassword = EditTextHintUtils.getString(inputAffirmPsd, getActivity());
        String captcha = EditTextHintUtils.getString(inputCode, getActivity());

        switch (view.getId()) {

            case R.id.back_left:
                onBackPressed();
                break;
            case R.id.bt_send_code:

                boolean result = checkedInputPhone(phone);
                if (result) {
                    TimeDown();
                }

                break;
            case R.id.bt_register:

                //校验输入的内容是否符合要求
                boolean isChecked = checkedInput(phone, captcha, password, affPassword);
                if (isChecked) {
                    //注册第2步
                    RegisterSecondFragment fragment = RegisterSecondFragment.newInstance(phone, password,captcha);
                    fragmentMgr.beginTransaction()
                            .addToBackStack(TAG)
                            .replace(R.id.fragment_login_container, fragment)
                            .commit();
                }
                break;
            case R.id.tv_register_two:
                //跳转到用户注册协议网页那里


                break;

        }
    }

    private boolean checkedInput(String phone, String captcha, String password, String affPassword) {
        boolean isResult = true;

        if (TextUtils.isEmpty(phone)) {
            EditTextHintUtils.showInputError(inputPhone, getActivity(), R.string.input_phone_show);
            isResult = false;
        }
        if (isResult && !MyUtils.isMobile(phone)) {
            EditTextHintUtils.showInputError(inputPhone, getActivity(), R.string.phone_format_show);
            isResult = false;
        }
        if (isResult && TextUtils.isEmpty(captcha)) {
            EditTextHintUtils.showInputError(inputCode, getActivity(), R.string.hint_captcha_error);
            isResult = false;
        }
        if (isResult&&captcha.length()<4||captcha.length()>6){
            EditTextHintUtils.showInputError(inputCode,getActivity(),R.string.hint_captcha_format_error);
            isResult=false;

        }
        if (isResult&&TextUtils.isEmpty(password)){
            MyUtils.showSnackbar(rootView,R.string.input_password_empty_show);
            isResult=false;
        }
        if (isResult&&password.length()>20||(password.length()<6&&password.length()>0)){
            MyUtils.showSnackbar(rootView,R.string.input_password_format_show);
            isResult=false;
        }
        if (isResult&&TextUtils.isEmpty(affPassword)){
            MyUtils.showSnackbar(rootView,R.string.input_affirm_password_empty_show);
            isResult=false;
        }
        if (isResult&&!affPassword.equals(password)){
            MyUtils.showSnackbar(rootView,R.string.hint_password_same_twice_error);
            isResult=false;
        }
        //调用接口验证手机号和验证码是否一致


        return isResult;
    }


    //检验输入的是否是手机号
    private boolean checkedInputPhone(String phone) {
        boolean checked = true;
        if (TextUtils.isEmpty(phone)) {
            MyUtils.showSnackbar(rootView, R.string.input_phone_show);
            checked = false;
        }
        if (checked && !MyUtils.isMobile(phone)) {
            MyUtils.showSnackbar(rootView, R.string.phone_format_show);
            checked = false;
        }
        //调接口判断手机号是否注册，检验是否注册的接口
        if (checked) {
            checked = false;
            MyUtils.showSnackbar(rootView, R.string.phone_register_show);
            LoginFragment loginFragment = LoginFragment.newInstance(phone);
            Single.just("").delay(2, TimeUnit.SECONDS).compose(RxUtils.applySchedulers()).subscribe(s -> {
                fragmentMgr.beginTransaction()
                        .addToBackStack(TAG)
                        .replace(R.id.fragment_login_container, loginFragment)
                        .commit();

            });


        }
        return checked;
    }

    //倒计时
    private void TimeDown() {
        //倒计时开始
        timer = new CountDownTimer(NUM_COUNTDOWN, COUNT_UNIT) {
            @Override
            public void onTick(long l) {
                String info = "重新发送" + l / 1000 + "S";
                @SuppressLint("StringFormatMatches") String count = getString(R.string.find_passwd_send_countdown, l / 1000);
                if (btnSendCaptcha != null && !TextUtils.isEmpty(info)) {
                    btnSendCaptcha.setText(count);
                }
            }

            @Override
            public void onFinish() {
                btnSendCaptcha.setText(R.string.btn_send_code);
                btnSendCaptcha.setEnabled(true);
                isCounting = false;
            }
        };
        timer.start();
        isCounting = true;
        btnSendCaptcha.setEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timer != null) {
            timer.cancel();
        }
        isCounting = false;
    }





}
