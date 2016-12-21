package haowei.computer.goldbowl.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.Pair;


import javax.inject.Inject;
import javax.inject.Singleton;

import haowei.computer.goldbowl.di.ApplicationContext;


@Singleton public class UserDao {
  private DBHelper mDBHelper;

  @Inject public UserDao(@ApplicationContext Context context) {
    mDBHelper = new DBHelper(context);
  }

  /**
   * 初始化用户数据
   *
   * @return the row ID of the newly inserted row, or -1 if an error occurred
   */

}
