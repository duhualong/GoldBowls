package haowei.computer.goldbowl.util;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import haowei.computer.goldbowl.R;

/**
 * Created by Administrator on 2016/12/21.
 */

public class MyUtils {
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
}
