package haowei.computer.goldbowl.data.local;

import android.content.Context;
import android.content.SharedPreferences;



import javax.inject.Inject;
import javax.inject.Singleton;

import haowei.computer.goldbowl.di.ApplicationContext;

/**
 * SharedPreferences Open Helper
 * Created by Zac on 2016/6/12.
 */
@Singleton
public class PreferencesHelper {
  public static final String PREF_FILE_NAME = "pref_file";
  private SharedPreferences prefs;

  @Inject
  public PreferencesHelper(@ApplicationContext Context context) {
    prefs = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
  }
  public SharedPreferences getPrefs() {
    return prefs;
  }
  public void clear() {
    prefs.edit().clear().apply();
  }
}
