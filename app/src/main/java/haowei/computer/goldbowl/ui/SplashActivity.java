package haowei.computer.goldbowl.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import haowei.computer.goldbowl.R;
import haowei.computer.goldbowl.base.BaseActivity;
import haowei.computer.goldbowl.data.local.PreferencesHelper;
import haowei.computer.goldbowl.test.MainActivity;
import haowei.computer.goldbowl.ui.sign.LoginActivity;
import haowei.computer.goldbowl.util.Constants;
import haowei.computer.goldbowl.util.RxUtils;
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
