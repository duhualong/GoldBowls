package haowei.computer.goldbowl.util;

import android.text.Editable;
import android.text.Selection;
import android.widget.EditText;

/**
 * Created by Administrator on 2016/12/21.
 */

public class MyUtils {
    //设置光标在尾部位置
    public static void setSelection(EditText editText){
        Editable etext = editText.getText();
        Selection.setSelection(etext, etext.length());
    }
}
