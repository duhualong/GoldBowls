package haowei.computer.goldbowl.data.remote;


import android.content.SharedPreferences;
import android.text.TextUtils;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import haowei.computer.goldbowl.base.App;
import haowei.computer.goldbowl.model.ApiResponse;
import haowei.computer.goldbowl.model.request.RegisterCaptcha;
import haowei.computer.goldbowl.util.Constant;
import haowei.computer.goldbowl.util.Constants;
import haowei.computer.goldbowl.util.MyUtils;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import rx.Single;

/**
 * Remote service api module

 */
@Singleton public interface RemoteService {

  String QINIU_CDN = "http://oa4zsx1he.bkt.clouddn.com/";
  String DOMAIN = "http://app.jfwlicai.com/api.php/";
  //注册发送验证码

  //@POST("v1/index/getSignupCode?"+Constants.CAPTCHA)Single<ApiResponse> login(@Body RegisterCaptcha registerCaptcha);




  class Creator {

    @Inject public RemoteService createService() {
      OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
          .writeTimeout(10, TimeUnit.SECONDS)
          .readTimeout(30, TimeUnit.SECONDS)
          .addNetworkInterceptor(new StethoInterceptor())
          .build();

      Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

      Retrofit retrofit = new Retrofit.Builder().baseUrl(DOMAIN)
          .client(client)
          .addConverterFactory(GsonConverterFactory.create(gson))
          .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
          .build();

      return retrofit.create(RemoteService.class);
    }

  }

}
