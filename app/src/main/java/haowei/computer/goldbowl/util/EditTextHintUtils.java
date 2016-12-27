package haowei.computer.goldbowl.util;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import haowei.computer.goldbowl.R;

/**
 * Created by Administrator on 2016/12/27.
 */

public class EditTextHintUtils {
    //密码长度规范设置
    public static void setPasswordErrorHint(EditText editText, Activity activity){

        editText.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                temp = s;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (temp.length() > 0) {
                    if (temp.length() < 6) {
                        editText.setError(activity.getString(R.string.hint_password_length_less_error));

                    }
                    if (temp.length() > 20) {
                        editText.setError(activity.getString(R.string.hint_password_length_long_error));


                    }

                }else {
                    editText.setError("设置密码不能为空！");

                }

            }

        });

    }
    //确认密码设置
    public static void setAffPasswordHint(EditText editText,EditText editText1,Activity activity) {


        editText.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                temp = s;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str1;
                String str2;
                str1 = editText.getText().toString();
                str2 = editText1.getText().toString();
                if (str1.equals(str2)) {
                    if (temp.length() >= 3 && temp.length() <= 20) {

                    }
                } else {
                  editText.setError(activity.getString(R.string.hint_password_same_twice_error));
                }


            }

        });
    }
    //获取EditText的值
    public static String getString(EditText editText,Activity activity){
     return    editText.getText().toString();
    }
    //显示错误信息
    public static void showInputError(EditText editText,Activity activity,int id){
        editText.setError(activity.getString(id));
    }

}
