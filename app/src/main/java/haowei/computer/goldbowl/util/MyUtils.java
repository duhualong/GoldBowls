package haowei.computer.goldbowl.util;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.wx.pwd.CheckStrength;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import haowei.computer.goldbowl.R;
import rx.Single;

/**
 * Created by Administrator on 2016/12/21.
 */

public class MyUtils {

    //对CheckBox状态监听
    public static void setCheckBox(CheckBox checkBox,EditText editText){
        checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }else {
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            setSelection(editText);

        });
    }
    //根据boole值设置密码是否显示
    public static void setShowHide(CheckBox checkBox, EditText editText){
        if (checkBox.isChecked()){
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }else {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        setSelection(editText);
    }
    //设置光标在尾部位置
    public static void setSelection(EditText editText){
        Editable etext = editText.getText();
        Selection.setSelection(etext, etext.length());
    }
    //设置TextView颜色和图片
    public static void setTextStyle(TextView text, Activity activity){
        text.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_right_orange, 0);
    }
    //Snackbar 弹出提示信息
    public static void showSnackbar(View view,int id){

        Snackbar.make(view,view.getResources().getString(id),Snackbar.LENGTH_SHORT).show();
    }
    //  手机号正则表达式

    public static boolean isMobile(String phone) {
        Pattern p ;
        Matcher m ;
        boolean b ;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(phone);
        b = m.matches();
        return b;
    }
    //得到EditText的输入内容
    public static String getString(EditText editText){
        return editText.getText().toString().trim();
    }
    //Snackbar 弹出框设置样式
    private static void showSnackBar(View view,int id,Activity activity){
        Snackbar snackbar=Snackbar.make(view,view.getResources().getString(id),Snackbar.LENGTH_LONG);
        View view1=snackbar.getView();
        TextView tv = (TextView) view1.findViewById(R.id.snackbar_text);
        view1.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        tv.setTextColor(Color.BLACK);
        tv.setGravity(Gravity.CENTER);
        snackbar.show();
    }

    /**
     * 获取手机品牌信息
     *
     * @return
     */
    public static String getPhoneBrand() {
        return android.os.Build.BRAND;
    }

    /**检测设置密码的强度
     *
     * @param password
     * @return
     *  //检测设置的密码强度
    //        0-3 : [easy]
    //        4-6 : [midium]
    //        7-9 : [strong]
    //        10-12 : [very strong]
    //        >12 : [extremely strong]
     */

    public static int checkPassword(String password){
        return CheckStrength.checkPasswordStrength(password);
    }



    /**
     * 设置是否显示错误信息
     * @param textView
     * @param textValue  文字的值
     *
     */


    public static void  errorShow(TextView textView,int textValue, Button button){
        textView.setVisibility(View.VISIBLE);
        textView.setText(textValue);
        button.setClickable(false);
        button.setBackgroundResource(R.drawable.bg_round_color_gray_deep);
        Single.just("").delay(3, TimeUnit.SECONDS).compose(RxUtils.applySchedulers()).subscribe(s -> {
            textView.setVisibility(View.GONE);
            button.setClickable(true);
            button.setBackgroundResource(R.drawable.bg_round_color_primary);
        });
    }
}
