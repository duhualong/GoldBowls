package haowei.computer.goldbowl.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DBHelper
 * Created by Zac on 2016/6/12.
 */
public class DBHelper extends SQLiteOpenHelper {

  private static final String DB_NAME = "sql.db";
  public static final String TABLE_USER = "goldbowl";

  public static final String TABLE_COLLECTION="collection";

  public static final String FIELD_UID = "user_id";
  public static final String FIELD_PHONE = "mobile";
  public static final String FIELD_EMAIL = "email";
  public static final String FIELD_NAME = "user_name";
  public static final String FIELD_SEX = "sex";
  public static final String FIELD_SIGN = "sign"; // 个性签名
  public static final String FIELD_WEIXIN_NUM = "weChat_num"; // 微信号
  public static final String FIELD_AVATER = "avater"; // 个人介绍
  public static final String FIELD_LONGITUDE = "longitude"; // 经度
  public static final String FIELD_LATITUDE = "latitude"; // 纬度
  public static final String FIELD_LANDTIME = "landtime"; //最后登陆时间
  public static final String FIELD_TOKEN = "token"; // 容云token
  public static final String FIELD_BIRTHDAY = "birthday"; //出生日期
  public static final String FIELD_FOCUS_COUNTS = "focus_counts"; //关注人数
  public static final String FIELD_FOCUSD_COUNTS = "focusd_counts"; //粉丝人数
  public static final String FIELD_CHARM = "charm"; //魅力值
  public static final String FIELD_HEIGHT = "height"; //身高
  public static final String FIELD_WEIGHT = "weight"; //体重
  public static final String FIELD_WANT = "want"; //我找的是 0想聊天 1求约会 2找朋友 3谈恋爱 4找玩伴 5不限
  public static final String FIELD_MARRY_STATE = "marry_state"; //感情状况:0未婚1已婚2保密
  public static final String FIELD_RACE = "race"; //种族 0亚洲人 1黑人 2拉美人 3中东人 4混血 5白人 6南  亚 7不限
  public static final String FIELD_LIVING_ADDRESS = "living_address"; //现居
  public static final String FIELD_BIRTH_ADDRESS = "birth_address"; //故乡
  public static final String FIELD_JOB_CATEGORY = "job_category";//职业
  public static final String FIELD_INCOME = "income";//收入 0 2000以下1 2000-4000 2 4000-6000 3 6000-8000 4 8000-10000 5 10000以上
  public static final String FIELD_PHOTO="photo";//相册
  public static final String FIELD_GOLD="gold";//金币
  public static final String FIELD_RIGHT="right";// 0未认证1已认证2驳回3待审核
  public static final String FIELD_GROUP_ID="group_id";//会员级别 默认为0 0普通 1VIP
  public static final String FIELD_MOBILECHECK="mobile_check";//是否绑定手机号  0是未绑定 1是绑定

  //邀约列表
  public static final String ID="id";//邀约列表的id
  public static final String TITLE="title";//发布邀约的标题
  public static final String COST="cost";//费用
  public static final String TARGET="target";//目标人群
  public static final String USER_ID="user_id";//发表邀约用户的id
  public static final String SHOP_NAME="shop_name";//关联商店名称
  public static final String PEOPLE_NUM="people_num";//活动人数
  public static final String PEOPLE_PARTICIPATE="people_participate";//参加人数
  public static final String TYPE="type";//聚会类别
  public static final String CREATE_TIME="create_time";//发布时间
  public static final String TIME="time";//活动时间
  public static final String COVER="cover";//活动封面
  public static final String CHAT_ID="chat_id";//群聊号
  public static final String EXTRA="extra";//补充说明
  public static final String STATECOLLECT="statecollect";//是否收藏
  public static final String COUNTPRA="countpra";//点赞人数
  public static final String STATE="state";//是否点赞
  public static final String ISFORM="isform";//是否还有剩余数据
  public static final String REMARK="remark";//用户在该群聊中的备注内容







  //public static final String SHOP_ID="shop_id";
  //public static final String ACTIVITY_TIME="activity_time";
  //
  //
  //public static final String SEND="send";//接送
  //public static final String TIPS="tips";//红包
  //
  //
  //public static final String AREA_ID="area_id";//地区id;











  private static final int DB_VERSION = 1;

  public DBHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USER +
        "(" + FIELD_UID + " INTEGER PRIMARY KEY," +
        FIELD_PHONE + " TEXT," +
        FIELD_EMAIL + " TEXT," +
        FIELD_NAME + " TEXT, " +
        FIELD_SEX + " TEXT, " +
        FIELD_SIGN + " TEXT, " +
        FIELD_WEIXIN_NUM + " TEXT, " +
        FIELD_AVATER + " TEXT, " +
        FIELD_LONGITUDE + " TEXT, " +
        FIELD_LATITUDE + " TEXT, " +
        FIELD_LANDTIME + " TEXT, " +
        FIELD_TOKEN + " TEXT, " +
        FIELD_BIRTHDAY  + " TEXT, " +
        FIELD_FOCUS_COUNTS + " TEXT, " +
        FIELD_FOCUSD_COUNTS + " TEXT, " +
        FIELD_CHARM + " TEXT, " +
        FIELD_HEIGHT + " TEXT, " +
        FIELD_WEIGHT + " TEXT, " +
        FIELD_WANT + " TEXT, " +
        FIELD_MARRY_STATE + " TEXT, " +
        FIELD_RACE + " TEXT, " +
        FIELD_LIVING_ADDRESS + " TEXT, " +
        FIELD_BIRTH_ADDRESS + " TEXT, " +
        FIELD_JOB_CATEGORY + " TEXT, " +
        FIELD_INCOME + " TEXT, " +
        FIELD_PHOTO + " TEXT, " +
        FIELD_GOLD + " TEXT, " +
        FIELD_RIGHT + " TEXT, " +
        FIELD_GROUP_ID + " TEXT, " +
        FIELD_MOBILECHECK + " TEXT);");

    db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_COLLECTION+
        "(" + ID + " INTEGER PRIMARY KEY," +
        TITLE +" TEXT, "+
        COST + " TEXT, " +
        TARGET + " TEXT, " +
        USER_ID + " TEXT, " +
        SHOP_NAME + " TEXT, " +
        PEOPLE_NUM + " TEXT, " +
        PEOPLE_PARTICIPATE + " TEXT, " +
        TYPE + " TEXT, " +
        CREATE_TIME + " TEXT, " +
        TIME + " TEXT, " +
        CHAT_ID + " TEXT, " +
        COVER + " TEXT, " +
        EXTRA + " TEXT, " +
        STATECOLLECT + " TEXT, " +
        COUNTPRA + " TEXT, " +
        STATE + " TEXT, " +
        ISFORM+ " TEXT, " +
        REMARK + " TEXT);");

  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
    db.execSQL("DROP TABLE IF EXISTS "+TABLE_COLLECTION);
  }
}
