package haowei.computer.goldbowl.core;

import org.json.JSONException;

import haowei.computer.goldbowl.model.ApiResponse;

/**
 * Created by Administrator on 2017/03/27.
 */

public interface AppAction {
    /**
     * 注册发送验证码
     * mobile 手机号
     */

     void registerCaptcha(String mobile, ActionCallbackListener<ApiResponse>listener) throws JSONException;

}
