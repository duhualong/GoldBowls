package haowei.computer.goldbowl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register_second);
//        //设置不同字体不同颜色
//        TextView tv= (TextView) findViewById(R.id.textView);
//        String str="测试字体<font color='#FF0000'><font>颜色</font></font>";
//        tv.setText(Html.fromHtml(str));
    }
}
