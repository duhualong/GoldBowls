package haowei.computer.goldbowl;

import android.app.Activity;
import android.widget.TextView;

import com.example.library.BandCardEditText;

import butterknife.BindView;
import haowei.computer.goldbowl.base.BaseActivity;

/**
 * Created by Administrator on 2017/01/06.
 */

public class TestBankActivity extends BaseActivity {
    @BindView(R.id.text)TextView textView;
    @BindView(R.id.bankEt)BandCardEditText bank;
    @Override
    protected int getContentView() {
        return R.layout.test_bank_format;
    }

    @Override
    protected void updateUI() {


        bank.setBankCardListener(new BandCardEditText.BankCardListener() {
            @Override
            public void success(String name) {
                textView.setText("所属银行：" + name);
            }

            @Override
            public void failure() {
                textView.setText("所属银行：");
            }
        });

    }
}
