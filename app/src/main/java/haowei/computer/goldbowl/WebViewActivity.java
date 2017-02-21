package haowei.computer.goldbowl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;


import butterknife.BindView;
import butterknife.OnClick;
import haowei.computer.goldbowl.base.BaseActivity;
import com.ips.p2p.StartPluginTools;
/**
 * Created by Administrator on 2017/02/08.
 */

public class WebViewActivity extends BaseActivity {
    private Bundle bundle;
    @BindView(R.id.mainWebView)WebView mWebView;
    @Override
    protected int getContentView() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void updateUI() {
        //检测网络状态
        checkInternetState();


    }

    private void checkInternetState() {
      


    }
@OnClick(R.id.button_test) public void onClick(View view){
    switch (view.getId()){
        case R.id.button_test:


            StartPluginTools.start_p2p_plugin(StartPluginTools.RECHARGE,WebViewActivity.this,bundle,1);


            break;
    }
}

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}
