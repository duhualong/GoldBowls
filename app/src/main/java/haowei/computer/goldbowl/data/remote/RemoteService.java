package haowei.computer.goldbowl.data.remote;


import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Single;

/**
 * Remote service api module

 */
@Singleton public interface RemoteService {

  String QINIU_CDN = "http://oa4zsx1he.bkt.clouddn.com/";
  String DOMAIN = " http://120.26.56.100/";






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
