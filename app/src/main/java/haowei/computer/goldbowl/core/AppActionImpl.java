package haowei.computer.goldbowl.core;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import haowei.computer.goldbowl.model.ApiResponse;
import haowei.computer.goldbowl.util.Encrypt;
import haowei.computer.goldbowl.util.MyUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static haowei.computer.goldbowl.util.Constants.SALT_KEY;

/**
 * Created by Administrator on 2017/03/27.
 */

public class AppActionImpl implements AppAction {

    String nonce = "147545452122544522125441122655414";
    String parms = "NULL";
    String sign;
    private Gson mGson;

    public AppActionImpl() {
        mGson = new Gson();
    }


    @Override
    public void registerCaptcha(String mobile, ActionCallbackListener<ApiResponse> listener) throws JSONException {
        String timetamp = MyUtils.newTimeToStamp();
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonarray = new JSONArray();//json数组，里面包含的内容为pet的
        String objstr = jsonObject.toString();
        jsonObject.put("mobile", mobile);
        jsonarray.put(jsonObject);
        System.out.println("时间戳1：" + timetamp);
        System.out.println("jsonarray:"+jsonObject);
        sign = Encrypt.md5(timetamp + nonce + jsonObject + SALT_KEY);

        String splitUrl = "timestamp=" + timetamp + "&nonce=" + nonce + "&sign=" + sign;
        System.out.println("时间戳2：" + timetamp);
        String url = Api.REGISTER_CAPTCHA + splitUrl;
        System.out.println("拼接的url:"+url);
        HttpClient.postJsonData(url, objstr, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onActionFailure(RequestEvent.NETWORK_ERROR, "网络连接错误！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if (!response.isSuccessful() || TextUtils.isEmpty(result)) {
                    listener.onActionFailure(RequestEvent.NETWORK_ERROR, "网络连接错误！");
                } else {
                    Type type = new TypeToken<ApiResponse>() {
                    }.getType();
                    Gson gson = new Gson();
                    ApiResponse apiResponse = gson.fromJson(result, type);
                    int state = apiResponse.getCode();
                    System.out.println("打印state:"+state+"??????"+apiResponse.getMessage());
                    if (state == 20000) {
                        listener.onActionSuccess(apiResponse);
                    } else {
                        listener.onActionFailure(RequestEvent.BAD_RESULT, apiResponse.getMessage());
                    }
                }
            }
        });


    }
}
