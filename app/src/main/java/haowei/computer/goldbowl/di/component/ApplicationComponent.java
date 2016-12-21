package haowei.computer.goldbowl.di.component;

import android.content.Context;
import android.webkit.WebView;


import dagger.Component;
import haowei.computer.goldbowl.base.App;
import haowei.computer.goldbowl.base.BaseActivity;
import haowei.computer.goldbowl.base.BaseFragment;
import haowei.computer.goldbowl.base.BaseSupportFragment;
import haowei.computer.goldbowl.data.local.PreferencesHelper;
import haowei.computer.goldbowl.data.local.UserDao;
import haowei.computer.goldbowl.data.realm.RealmController;
import haowei.computer.goldbowl.data.remote.HttpClient;
import haowei.computer.goldbowl.data.remote.RemoteService;
import haowei.computer.goldbowl.di.ApplicationContext;
import haowei.computer.goldbowl.di.module.ApplicationModule;

import javax.inject.Singleton;

/**
 * Application component
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
  void inject(App app);
  void inject(BaseActivity baseActivity);
  void inject(BaseFragment baseFragment);
  void inject(BaseSupportFragment supportFragment);
  @ApplicationContext
  Context context();
  RemoteService webService();
  PreferencesHelper prefsHelper();
  HttpClient httpClient();
  UserDao userDao();
  RealmController realmController();
  WebView webView();

}
