package haowei.computer.goldbowl.ui.view;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindView;
import haowei.computer.goldbowl.BuildConfig;
import haowei.computer.goldbowl.R;
import haowei.computer.goldbowl.base.BaseActivity;
import haowei.computer.goldbowl.test.MainActivity;
import haowei.computer.goldbowl.ui.SplashActivity;

/**
 * Created by Administrator on 2016/12/29.
 */

public class SplashActivitys extends BaseActivity{

    private static final String TAG = "SplashActivity";

    private Handler mHandler = new Handler();

    @BindView(R.id.splash_view)
    SplashView splash_view;
    @BindView(R.id.tv_splash_info)
    TextView tv_splash_info;


    @Override
    protected int getContentView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题
        getWindow().setFlags(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);  //设置全屏
        return R.layout.activity_splash1;
    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected void onStart() {
        super.onStart();

        AssetManager mgr=getAssets();//得到AssetManager
        Typeface tf=Typeface.createFromAsset(mgr, "fonts/rm_albion.ttf");//根据路径得到Typeface
        tv_splash_info.setTypeface(tf);//设置字体
        startLoadingData();
    }

    /**
     * start splash animation
     */
    private void startLoadingData(){
        // finish "loading data" in a random time between 1 and 3 seconds
        Random random = new Random();
        mHandler.postDelayed(this::onLoadingDataEnded, 1000 + random.nextInt(2000));
    }

    private void onLoadingDataEnded(){
        // start the splash animation
        splash_view.splashAndDisappear(new SplashView.ISplashListener(){
            @Override
            public void onStart(){
                // log the animation start event
                if(BuildConfig.DEBUG){
                    Log.d(TAG, "splash started");
                }
            }

            @Override
            public void onUpdate(float completionFraction){
                // log animation update events
                if(BuildConfig.DEBUG){
                    Log.d(TAG, "splash at " + String.format("%.2f", (completionFraction * 100)) + "%");
                }
            }

            @Override
            public void onEnd(){
                // log the animation end event
                if(BuildConfig.DEBUG){
                    Log.d(TAG, "splash ended");
                }
                // free the view so that it turns into garbage
                splash_view = null;
                goToMain();
            }
        });
    }

    public void goToMain() {
        finish();
       startActivity(new Intent(this, MainActivity.class));
    }


}
