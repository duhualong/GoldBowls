package haowei.computer.goldbowl.ui.sign;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
 */

public class ForgetPasswordFirstFragment extends BaseFragment {
    @BindView(R.id.input_phone)EditText inputPhone;
    @BindView(R.id.et_input_code)EditText inputCaptcha;
    @BindView(R.id.input_identity_card)EditText inputIdentity;
    @BindView(R.id.btn_get_code)Button btnSendCaptcha;
    private CountDownTimer timer;
    private boolean isCounting;


    private static final String TAG ="ForgetPasswordFirstFragment";

    @Override
    protected int getContentView() {
        return R.layout.fragment_forget_password_first;
    }

    @Override
    protected void updateUI() {

    }
    @OnClick({R.id.back_left,R.id.btn_get_code,R.id.btn_next})public void onClick(View view){
        String phone= MyUtils.getString(inputPhone);
        String captcha=MyUtils.getString(inputCaptcha);
        String identity=MyUtils.getString(inputIdentity);

        switch (view.getId()){
            case R.id.back_left:
                onBackPressed();
                break;
            case R.id.btn_get_code:
                //发送验证码，调用短信接口
                checkedInputPhone(phone);


                break;
            case R.id.btn_next:

                ForgetPasswordSecondFragment fragment=ForgetPasswordSecondFragment.newInstance(phone,captcha,identity);
                fragmentMgr.beginTransaction()
                        .addToBackStack(TAG)
                        .replace(R.id.fragment_login_container, fragment)
                        .commit();
                break;
        }
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
            //未注册跳转到注册页面
            checked = false;
            MyUtils.showSnackbar(rootView, R.string.phone_register_show);
            RegisterFirstFragment fragment = RegisterFirstFragment.newInstance(phone);
            MyUtils.showSnackbar(rootView,R.string.unregister_phone_show);
            Single.just("").delay(2, TimeUnit.SECONDS).compose(RxUtils.applySchedulers()).subscribe(s -> {
                fragmentMgr.beginTransaction()
                        .addToBackStack(TAG)
                        .replace(R.id.fragment_login_container, fragment)
                        .commit();
            });
            //注册调短信接口并倒计时
            if (checked){
                TimeDown();
            }





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
