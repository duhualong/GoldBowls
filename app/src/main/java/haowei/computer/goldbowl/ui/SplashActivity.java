package haowei.computer.goldbowl.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.wx.pwd.CheckStrength;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import haowei.computer.goldbowl.R;
import haowei.computer.goldbowl.WebViewActivity;
import haowei.computer.goldbowl.base.BaseActivity;
import haowei.computer.goldbowl.data.local.PreferencesHelper;
import haowei.computer.goldbowl.data.remote.HttpClient;
import haowei.computer.goldbowl.test.MainActivity;
import haowei.computer.goldbowl.ui.sign.LoginActivity;
import haowei.computer.goldbowl.util.Constants;
import haowei.computer.goldbowl.util.Encrypt;
import haowei.computer.goldbowl.util.MyUtils;
import haowei.computer.goldbowl.util.RxUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.Single;

/**
 * Created by Administrator on 2016/12/19.
 * 引导页
 */

public class SplashActivity extends BaseActivity{
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void updateUI() {


        System.out.println("时间戳："+MyUtils.newTimeToStamp());
        System.out.println("密文："+Encrypt.md5("14891278881475454521225445221254411226554121881777248612345620170212_api.jfwlicai.com_chuangyankeji"));




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            hideSystemUI();
        }

        mProgressBar.setVisibility(View.VISIBLE);
        Single.just("").delay(2, TimeUnit.SECONDS).compose(RxUtils.applySchedulers()).subscribe(s -> {
            mProgressBar.setVisibility(View.GONE);
            //根据本地的登录记录值判断用户是否登录过 如果是true直接进入首页 否则进入登录页面
            if (mPrefsHelper.getPrefs().getBoolean(Constants.IS_LOGIN,false)){
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }else {
               startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
            SplashActivity.this.finish();
        });
    }


//设置全屏显示
    @TargetApi(Build.VERSION_CODES.KITKAT) private void hideSystemUI() {
        getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

}
