package haowei.computer.goldbowl.core;


import com.alibaba.fastjson.JSON;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;



import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * OkHttpClient encapsulate class
 * Created by zac on 12/18/15.
 */
public class HttpClient {

  public static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");

  private static final OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();

  static {
    httpBuilder.connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS);
  }

  /**
   * 发送json数据
   *
   * @param url 网页连接
   * @param json json数据
   * @param isEncrypt json数据是否加密
   * @param responseCallback 网页回调函数
   */
  public static void postJsonData(String url, String json, boolean isEncrypt,
                                  Callback responseCallback) {

    String sendData;

    if (isEncrypt) { // 加密
      json = Encrypts.encrypt("5f8d77aad1c2c537a11562ac1f8d0a15", json);
      Data data = new Data(json);
      sendData = JSON.toJSONString(data);

    } else { // 未加密
      sendData = json;

    }

    RequestBody body = RequestBody.create(JSON_TYPE, sendData);
    System.out.println("sendData: " +sendData);
    Request request = new Request.Builder().url(url).post(body).build();
    httpBuilder.build().newCall(request).enqueue(responseCallback);
  }

  /**
   * Get json data from server
   *
   * @param url data url
   * @param responseCallback server response callback
   */
  public static void getJsonData(String url, Callback responseCallback) {
    Request request = new Request.Builder().url(url).get().build();
    httpBuilder.build().newCall(request).enqueue(responseCallback);
  }

  /**
   * 加密数据封装类
   */
  static class Data {
    private String data;

    public Data(String data) {
      this.data = data;
    }

    public String getData() {
      return data;
    }
  }
}
