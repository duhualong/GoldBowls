package haowei.computer.goldbowl.ui.sign;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import haowei.computer.goldbowl.R;
import haowei.computer.goldbowl.base.BaseFragment;
import haowei.computer.goldbowl.util.MyUtils;

/**
 * Created by Administrator on 2016/12/22.
 */

public class RegisterSecondFragment extends BaseFragment {
    private static final String TAG ="RegisterSecondFragment" ;
    @BindView(R.id.tv_register_title)TextView title;
    @BindView(R.id.tv_authentication)TextView authentication;
    @BindView(R.id.input_real_name)EditText realName;
    @BindView(R.id.input_identity_card)EditText identityCard;

    @Override
    protected int getContentView() {
        return R.layout.fragment_register_second;
    }

    @Override
    protected void updateUI() {
        title.setText(R.string.tv_identity_verify);
        MyUtils.setTextStyle(authentication,getActivity());


    }

    @OnClick({R.id.back_left, R.id.bt_submit})
    public void OnClick(View view) {
        String name=MyUtils.getString(realName);
        String identity=MyUtils.getString(identityCard);

        switch (view.getId()) {
            case R.id.back_left:
                onBackPressed();
                break;
            case R.id.bt_submit:
                //注册第2步
                fragmentMgr.beginTransaction()
                        .addToBackStack(TAG)
                        .replace(R.id.fragment_login_container, new RegisterThirdFragment())
                        .commit();
                break;
        }
    }
}
