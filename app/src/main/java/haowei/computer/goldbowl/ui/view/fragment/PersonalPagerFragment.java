package haowei.computer.goldbowl.ui.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.yalantis.ucrop.UCrop;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import haowei.computer.goldbowl.R;
import haowei.computer.goldbowl.base.BaseSupportFragment;
import haowei.computer.goldbowl.test.MainActivity;
import haowei.computer.goldbowl.test.PasswordView;
import haowei.computer.goldbowl.util.MyUtils;
import haowei.computer.goldbowl.util.PermissionManager;
import me.drakeet.materialdialog.MaterialDialog;
import rx.Single;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/01/03.
 */

public class PersonalPagerFragment extends BaseSupportFragment implements OnItemClickListener {
    private static final int REQUEST_PHONE_CALL_CODE = 0xff;

    @BindView(R.id.tv_account)
    TextView accountInfo;
    @BindView(R.id.tv_sign_in)
    TextView signIn;
    @BindView(R.id.personal_center_avatar)
    CircleImageView avatarPhoto;
    @BindView(R.id.tv_accumulated_income)
    TextView totalIncome;
    @BindView(R.id.tv_available_money)
    TextView availableBalance;
    @BindView(R.id.tv_withdrawal_cash)
    TextView withdrawalCash;
    @BindView(R.id.tv_freeze_cash)
    TextView freezeCash;
    @BindView(R.id.btn_red_packet)
    Button redPacket;
    @BindView(R.id.root_view)
    View rootView;
    private AlertView mAlertView;//窗口拓展例子
    private PopupWindow mPopupWindow;
    private AlertView mAlertPhoneView;
    private View popView;
    private AlertView mAlertViewExt;//窗口拓展例子
    private AlertView mAlertAvatarView;//头像的弹出框

    @Override
    protected int getContentView() {
        return R.layout.fragment_personal_pager;
    }

    @Override
    protected void updateUI() {
        //初始化AlertView
        mAlertViewExt = new AlertView(null, null, null, null, null, context, AlertView.Style.Alert, null).setCancelable(true);
        ViewGroup extView = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.popup_window_sign_in, null);
        mAlertViewExt.addExtView(extView);
    }

    @OnClick({R.id.tv_login_out, R.id.img_push_notification, R.id.tv_sign_in,
            R.id.personal_center_avatar, R.id.tv_recharge, R.id.tv_withdraw, R.id.tv_trading_record,
            R.id.rl_red_packet, R.id.rl_investment, R.id.rl_experience, R.id.rl_my_code, R.id.rl_invite,
            R.id.rl_certification, R.id.rl_bind, R.id.rl_account, R.id.rl_address, R.id.rl_bank, R.id.rl_activity,
            R.id.rl_help, R.id.rl_customer
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_out:
                //退出登录
                loginOut();
                break;
            case R.id.img_push_notification:
                //消息推送

                break;
            case R.id.tv_sign_in:
                //签到
                mAlertViewExt.show();
                // showPopupWindow(view);


                break;
            case R.id.personal_center_avatar:
                //头像
                updateAvatar();


                break;
            case R.id.tv_recharge:
                //充值

                break;
            case R.id.tv_withdraw:
                //提现


                break;
            case R.id.tv_trading_record:
                //交易记录


                break;
            case R.id.rl_red_packet:
                //现金红包

                break;
            case R.id.rl_investment:
                //投资记录


                break;
            case R.id.rl_experience:
                //体验金投资

                break;
            case R.id.rl_my_code:
                //我的积分


                break;
            case R.id.rl_invite:
                //邀请分享

                break;

            case R.id.rl_certification:
                //实名认证

                break;
            case R.id.rl_bind:
                //绑定手机

                break;
            case R.id.rl_account:
                //账户安全

                break;
            case R.id.rl_address:
                //收货地址

                break;
            case R.id.rl_bank:
                //银行卡管理

                break;

            case R.id.rl_activity:
                //活动管理

                break;
            case R.id.rl_help:
                //帮助中心

                break;
            case R.id.rl_customer:
                //我的客服
                checkPhoneCallPermission();
                break;


        }
    }
    //更改头像
    private void updateAvatar() {
       mAlertAvatarView=new AlertView(null, null, "取消", null,
                new String[]{"拍照", "从相册中选择"},
                context, AlertView.Style.ActionSheet,this)
                .setCancelable(true);
        mAlertAvatarView.show();
    }


    //退出登录
    private void loginOut() {
        //或者builder模式创建
        mAlertView = new AlertView(getString(R.string.tv_login_out),
                getString(R.string.login_out_dialog), getString(R.string.cancel), new String[]{
                getString(R.string.confirm_dialog)}, null, context,
                AlertView.Style.Alert, this)
                .setCancelable(true);
        mAlertView.show();


    }

    private void callPhoneNumber() {
      mAlertPhoneView=  new AlertView.Builder().setContext(context)
                .setStyle(AlertView.Style.ActionSheet)
                .setTitle(getString(R.string.tv_my_customer_service))
                .setMessage(null)
                .setCancelText(getString(R.string.confirm_dialog))
                .setDestructive(getString(R.string.call_phone_alert))
                .setOthers(null)
                .setOnItemClickListener(this)
                .build();
        mAlertPhoneView.setCancelable(true);
//        mAlertPhoneView = new AlertView(getString(R.string.tv_my_customer_service),
//                getString(R.string.call_phone_alert), null,
//                new String[]{getString(R.string.confirm_dialog)},
//                null, context, AlertView.Style.ActionSheet, this)
//                .setCancelable(true);
        mAlertPhoneView.show();


    }


    @Override
    public void onItemClick(Object o, int position) {

        if (o == mAlertView && position != AlertView.CANCELPOSITION) {
            //在这里写退出登录的操作事件
            Toast.makeText(context, "点击事件！", Toast.LENGTH_LONG).show();
        }
        if (o == mAlertPhoneView && position == AlertView.CANCELPOSITION) {
            //在这做拨打电话权限的操作

            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:18817772486"));
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(intent);
        }
        if (o==mAlertAvatarView&&position!=AlertView.CANCELPOSITION){
            switch (position){
                case 0:


                    System.out.println("posotion:0");
                    break;
                case 1:
                    System.out.println("posotion:1");

                    break;
            }

        }

    }

    //检查电话权限
    private void checkPhoneCallPermission() {
        boolean isCouldCall = PermissionManager.checkPhoneCall(context);
        if (isCouldCall){
            callPhoneNumber();
        }else {
            PermissionManager.requestPhonesCall(PersonalPagerFragment.this, REQUEST_PHONE_CALL_CODE);
        }
    }
    /**
     * 权限申请回调
     *
     * @param requestCode 请求码
     * @param permissions 请求权限
     * @param grantResults 请求结果
     */
    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        switch (requestCode) {

            case REQUEST_PHONE_CALL_CODE:
                if (Manifest.permission.CALL_PHONE.equals(permissions[0])
                        && PackageManager.PERMISSION_GRANTED == grantResults[0]) {
                    callPhoneNumber();
                }
                break;

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }




}
