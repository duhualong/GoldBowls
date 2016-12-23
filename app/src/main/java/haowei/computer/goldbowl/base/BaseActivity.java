package haowei.computer.goldbowl.base;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.ButterKnife;
import haowei.computer.goldbowl.R;
import haowei.computer.goldbowl.data.local.PreferencesHelper;
import haowei.computer.goldbowl.data.local.UserDao;
import haowei.computer.goldbowl.data.remote.HttpClient;
import haowei.computer.goldbowl.data.remote.RemoteService;
import rx.Subscription;

/**
 * Base Activity
 */
public abstract class BaseActivity extends AppCompatActivity {
  private static Boolean isExit = false;
  protected abstract int getContentView();

  protected abstract void updateUI();

  private InputMethodManager inputMgr;
  protected FragmentManager fragmentMgr;
  protected android.support.v4.app.FragmentManager supportFragmentMgr;
  protected Context context;

  protected Subscription mSubscription;
  @Inject
  protected UserDao mUserDao;
  private ProgressDialog mProgressDialog;

  @Inject protected RemoteService mRemoteService;
  @Inject protected HttpClient mHttpClient;
  @Inject protected PreferencesHelper mPrefsHelper;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getContentView() != 0) {
      setContentView(getContentView());
    }
    ButterKnife.bind(this);

    App.get(this).getApplicationComponent().inject(this);

    setBaseFeatures();

    App.addActivity(this);

    updateUI();
  }

  @Override protected void onStop() {
    super.onStop();
    hideProgress();
    unSubscribe();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (mProgressDialog != null) {
      mProgressDialog = null;
    }
    //App.getRefWatcher().watch(this);
  }

  /**
   * 点击非EditText区域自动隐藏软键盘
   */
  @Override public boolean onTouchEvent(MotionEvent event) {
    if (inputMgr.isActive() && getCurrentFocus() != null) {
      if (getCurrentFocus().getWindowToken() != null) {
        inputMgr.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS);
      }
    }
    return super.onTouchEvent(event);
  }

  protected void setBaseFeatures() {
    inputMgr = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

    fragmentMgr = getFragmentManager();
    supportFragmentMgr = getSupportFragmentManager();

    context = this;
  }

  /**
   * Add fragment to activity container
   *
   * @param containerId container id
   * @param fragment fragment to add to activity.
   */
  protected void addFragment(int containerId, Fragment fragment) {
    fragmentMgr.beginTransaction().add(containerId, fragment).commit();
  }

  /**
   * Fix fragment addToBackStack() not working
   */
  @Override public void onBackPressed() {
    if (fragmentMgr.getBackStackEntryCount() > 0) {
      fragmentMgr.popBackStack();
    } else {
      super.onBackPressed();
    }
  }

  /**
   * Show Toast
   *
   * @param msg show message in the toast
   */
  protected void showToast(String msg) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
  }

  protected void showProgress(CharSequence message) {
    if (mProgressDialog == null) {
      mProgressDialog = new ProgressDialog(context);
    }
    mProgressDialog.setMessage(message);
    mProgressDialog.show();
  }

  protected void showProgress(int messageRes) {
    if (mProgressDialog == null) {
      mProgressDialog = new ProgressDialog(context);
    }
    if (messageRes != 0) {
      mProgressDialog.setMessage(getString(messageRes));
    }
    mProgressDialog.show();
  }

  protected void hideProgress() {
    if (mProgressDialog != null) {
      mProgressDialog.cancel();
    }
  }

  protected void unSubscribe() {
    if (mSubscription != null && !mSubscription.isUnsubscribed()) {
      mSubscription.unsubscribe();
    }
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    // TODO Auto-generated method stub
    if(keyCode == KeyEvent.KEYCODE_BACK)
    {
      exitBy2Click();      //调用双击退出函数
    }
    return false;
  }
  /**
   * 双击退出函数
   */

  private void exitBy2Click() {
    Timer tExit ;
    if (isExit == false) {
      isExit = true; // 准备退出
      Toast.makeText(this, R.string.exit_app_show, Toast.LENGTH_SHORT).show();
      tExit = new Timer();
      tExit.schedule(new TimerTask() {
        @Override
        public void run() {
          isExit = false; // 取消退出
        }
      }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

    } else {
      finish();
      System.exit(0);
    }
  }

}
