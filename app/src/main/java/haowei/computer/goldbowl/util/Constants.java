package haowei.computer.goldbowl.util;

import android.content.SharedPreferences;
import android.text.TextUtils;

import haowei.computer.goldbowl.base.App;

/**
 * Created by Administrator on 2016/12/21.
 * SharePre保存常用的字段
 */

public interface Constants {
    String UID="uid";
    String IS_LOGIN="is_login";
    long COUNT_UNIT = 1000L;
    long NUM_COUNTDOWN = 60 * COUNT_UNIT;
    String APIKEY="1487926783d73345a03d10d4039e4071fa667639e8";
    String APISECURITY="65c39778019d6affba4f68be641feba2";
    String SALT_KEY="20170212_api.jfwlicai.com_chuangyankeji";
    String CAPTCHA=MyUtils.newTimeToStamp();
    String TIMES=MyUtils.newTimeToStamp();
    String TIME="time";
    String STIME=App.getPrefsHelper().getString(Constants.TIME,"");
    String SIGN=Encrypt.md5("");



}
