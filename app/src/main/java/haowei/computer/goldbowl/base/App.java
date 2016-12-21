package haowei.computer.goldbowl.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.BuildConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.Stack;

import haowei.computer.goldbowl.data.realm.RealmController;
import haowei.computer.goldbowl.di.component.ApplicationComponent;
import haowei.computer.goldbowl.di.component.DaggerApplicationComponent;
import haowei.computer.goldbowl.di.module.ApplicationModule;


public class App extends Application {

  private ApplicationComponent mApplicationComponent;
  private static Application sApplicationContext;
  private static RefWatcher sRefWatcher;
  private static Stack<Activity> sActivityStack;
  private static RealmController realmController;


  public static App get(Context context) {
    return (App)context.getApplicationContext();
  }

  @Override public void onCreate() {
    super.onCreate();

    mApplicationComponent = prepareApplicationComponent().build();
    mApplicationComponent.inject(this);
    Stetho.initializeWithDefaults(this);
    sApplicationContext = this;

    sActivityStack = new Stack<>();
    realmController=new RealmController(getApplicationContext());
    // Facebook toolchain
    if (BuildConfig.DEBUG) { // debug 状态
      Stetho.initializeWithDefaults(this);
    }
    Fresco.initialize(getApplicationContext());
    //sRefWatcher = LeakCanary.install(this);
  }

  protected DaggerApplicationComponent.Builder prepareApplicationComponent() {
    return DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this));
  }

  public ApplicationComponent getApplicationComponent() {
    return mApplicationComponent;
  }

  public static RefWatcher getRefWatcher() {
    if (sRefWatcher == null) {
      sRefWatcher = LeakCanary.install(sApplicationContext);
    }
    return sRefWatcher;
  }

  public static void addActivity(Activity activity) {
    if (activity != null) {
      sActivityStack.add(activity);
    }
  }

  public static RealmController getRealmController() {
    return realmController;
  }

  public static void clearStack() {
    if (sActivityStack != null && !sActivityStack.isEmpty()) {
      for (Activity activity : sActivityStack) {
        activity.finish();
      }
    }
  }
}
