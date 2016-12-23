package haowei.computer.goldbowl.ui.sign;


import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import haowei.computer.goldbowl.R;
import haowei.computer.goldbowl.base.BaseFragment;
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
    private static final String TAG = "RegisterFirstFragment";
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
    @BindView(R.id.bt_send_code)Button btnSendCaptcha;

    @Override
    protected int getContentView() {
        return R.layout.fragment_register_first;
    }

    @Override
    protected void updateUI() {
        MyUtils.setTextStyle(inputInfo, getActivity());

    }

    @OnClick({R.id.back_left, R.id.bt_register, R.id.tv_register_two, R.id.checkbox_password, R.id.checkbox_password_affirm, R.id.bt_send_code})
    public void onClick(View view) {
        String phone= inputPhone.getText().toString();
        switch (view.getId()) {

            case R.id.back_left:
                onBackPressed();
                break;
            case R.id.bt_send_code:

             boolean result=checkedInputPhone(phone);
                if (result){
                    TimeDown();
                }

                break;
            case R.id.bt_register:

                //注册第2步

                fragmentMgr.beginTransaction()
                        .addToBackStack(TAG)
                        .replace(R.id.fragment_login_container, new RegisterSecondFragment())
                        .commit();
                break;
            case R.id.tv_register_two:

                break;
            case R.id.checkbox_password:

                break;
            case R.id.checkbox_password_affirm:

                break;
        }
    }
    //检验输入的是否是手机号
    private boolean checkedInputPhone(String phone) {
        boolean checked=true;
        if (TextUtils.isEmpty(phone)){
            MyUtils.showSnackbar(rootView,R.string.input_phone_show);
            checked=false;
        }
        if (checked&&!MyUtils.isMobile(phone)){
            MyUtils.showSnackbar(rootView,R.string.phone_format_show);
            checked=false;
        }
        //调接口判断手机号是否注册
        if (checked){
            checked=false;
            MyUtils.showSnackbar(rootView,R.string.phone_register_show);
            LoginFragment loginFragment=LoginFragment.newInstance(phone);
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
    private void TimeDown(){
        //倒计时开始
        timer = new CountDownTimer(NUM_COUNTDOWN, COUNT_UNIT) {
            @Override public void onTick(long l) {
                String info="重新发送"+l/1000+"S";
                @SuppressLint("StringFormatMatches") String count = getString(R.string.find_passwd_send_countdown, l / 1000);
                if(btnSendCaptcha!=null&&!TextUtils.isEmpty(info)) {
                    btnSendCaptcha.setText(count);
                }
            }

            @Override public void onFinish() {
                btnSendCaptcha.setText(R.string.btn_send_code);
                btnSendCaptcha.setEnabled(true);
                isCounting = false;
            }
        };
        timer.start();
        isCounting = true;
        btnSendCaptcha.setEnabled(false);
    }
    @Override public void onDestroyView() {
        super.onDestroyView();
        if (timer != null) {
            timer.cancel();
        }
        isCounting = false;
    }
}
