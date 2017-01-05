package haowei.computer.goldbowl.ui.view.fragment;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import haowei.computer.goldbowl.R;
import haowei.computer.goldbowl.base.BaseSupportFragment;
import me.drakeet.materialdialog.MaterialDialog;
import rx.Single;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/01/03.
 */

public class PersonalPagerFragment extends BaseSupportFragment {
    @BindView(R.id.tv_account)TextView accountInfo;
    @BindView(R.id.tv_sign_in)TextView signIn;
    @BindView(R.id.personal_center_avatar)CircleImageView avatarPhoto;
    @BindView(R.id.tv_accumulated_income)TextView totalIncome;
    @BindView(R.id.tv_available_money)TextView availableBalance;
    @BindView(R.id.tv_withdrawal_cash)TextView withdrawalCash;
    @BindView(R.id.tv_freeze_cash)TextView freezeCash;
    @BindView(R.id.btn_red_packet)Button redPacket;
    @BindView(R.id.root_view)View rootView;
    MaterialDialog mMaterialDialog;


    @Override
    protected int getContentView() {
        return R.layout.fragment_personal_pager;
    }

    @Override
    protected void updateUI() {

    }
    @OnClick({R.id.tv_login_out,R.id.img_push_notification,R.id.tv_sign_in,
            R.id.personal_center_avatar,R.id.tv_recharge,R.id.tv_withdraw,R.id.tv_trading_record,
            R.id.rl_red_packet,R.id.rl_investment,R.id.rl_experience,R.id.rl_my_code,R.id.rl_invite,
            R.id.rl_certification,R.id.rl_bind,R.id.rl_account,R.id.rl_address,R.id.rl_bank,R.id.rl_activity,
            R.id.rl_help,R.id.rl_customer
    })
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_login_out:
                //退出登录
                loginOut();
                break;
            case R.id.img_push_notification:
                //消息推送

                break;
            case R.id.tv_sign_in:
                //签到

                break;
            case R.id.personal_center_avatar:
                //头像

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

                break;



        }
    }


    //退出登录
    private void loginOut() {
//        View view = View.inflate(context, R.layout.dialog_logout, null);
//        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(view)
//                .setNegativeButton("取消", null)
//                .setPositiveButton("确定", (dialog, which) -> {
//
//                    dialog.dismiss();
//                });
//        builder.create().show();
//
//        mMaterialDialog = new MaterialDialog(context);
//        View view = LayoutInflater.from(context)
//                .inflate(R.layout.dialog_logout,
//                        null);
//        mMaterialDialog.setNegativeButton("取消", view1 -> {
//            mMaterialDialog.dismiss();
//
//        });
//        mMaterialDialog.setPositiveButton("确认", view12 -> {
//            mMaterialDialog.dismiss();
//
//        });
//        mMaterialDialog.setCanceledOnTouchOutside(true);
//        mMaterialDialog.setView(view).show();
//        final MaterialDialog alert = new MaterialDialog(context).setTitle(
//                "MaterialDialog")
//                .setContentView(
//                        R.layout.dialog_logout);
//
//        alert.setPositiveButton("OK", v -> alert.dismiss());
//        alert.setNegativeButton("取消", view -> {
//            alert.dismiss();
//
//        });
//        alert.show();

        MaterialDialog mMaterialDialog=new MaterialDialog(context);

               mMaterialDialog .setTitle("注销")
                .setMessage("是否退出登录")
                .setPositiveButton("确认", v ->

                        mMaterialDialog.dismiss())
                .setNegativeButton("取消", view -> {
                    mMaterialDialog.dismiss();

                });
        mMaterialDialog.setCanceledOnTouchOutside(true);

        mMaterialDialog.show();


    }


}
