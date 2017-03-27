package haowei.computer.goldbowl.model.request;

/**
 * Created by Administrator on 2017/03/23.
 */

public class RegisterCaptcha {
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public RegisterCaptcha(String mobile) {
        this.mobile = mobile;
    }

}
