package haowei.computer.goldbowl.di.module;

import android.app.Application;
import android.content.Context;
import android.webkit.WebView;
import dagger.Module;
import dagger.Provides;
import haowei.computer.goldbowl.data.local.PreferencesHelper;
import haowei.computer.goldbowl.data.local.UserDao;
import haowei.computer.goldbowl.data.realm.RealmController;
import haowei.computer.goldbowl.data.remote.HttpClient;
import haowei.computer.goldbowl.data.remote.RemoteService;
import haowei.computer.goldbowl.di.ApplicationContext;

import javax.inject.Singleton;

/**
 * Application Module
 */
@Module public class ApplicationModule {

  protected Application application;

  public ApplicationModule(@ApplicationContext Application application) {
    this.application = application;
  }

  @Provides @ApplicationContext Context provideContext() {
    return application;
  }

  @Provides @Singleton
  RemoteService provideWebService() {
    return new RemoteService.Creator().createService();
  }

  @Provides @Singleton
  HttpClient provideHttpClient() {
    return new HttpClient();
  }

  @Provides @Singleton
  PreferencesHelper providePrefsHelper() {
    return new PreferencesHelper(application);
  }

  @Provides @Singleton
  UserDao provideUserDao() {
    return new UserDao(application);
  }

  @Provides @Singleton
  RealmController provideRealmController() {
    return new RealmController(application);
  }

  @Provides @Singleton WebView provideWebView(@ApplicationContext Context context) {
    return new WebView(context);
  }
}
