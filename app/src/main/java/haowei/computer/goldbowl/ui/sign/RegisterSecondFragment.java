package haowei.computer.goldbowl.ui.sign;

import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import haowei.computer.goldbowl.R;
import haowei.computer.goldbowl.base.BaseFragment;
import haowei.computer.goldbowl.util.EditCheckUtil;
import haowei.computer.goldbowl.util.MyUtils;

/**
 * Created by Administrator on 2016/12/22.
 */

public class RegisterSecondFragment extends BaseFragment {
    private static final String TAG = "RegisterSecondFragment";
    @BindView(R.id.tv_register_title)
    TextView title;
    @BindView(R.id.tv_authentication)
    TextView authentication;
    @BindView(R.id.input_real_name)
    EditText realName;
    @BindView(R.id.input_identity_card)
    EditText identityCard;
    @BindView(R.id.root_view)
    View rootView;

    @Override
    protected int getContentView() {
        return R.layout.fragment_register_second;
    }

    @Override
    protected void updateUI() {
        title.setText(R.string.tv_identity_verify);
        MyUtils.setTextStyle(authentication, getActivity());


    }

    @OnClick({R.id.back_left, R.id.bt_submit})
    public void OnClick(View view) {
        String name = MyUtils.getString(realName);
        String identity = MyUtils.getString(identityCard);

        switch (view.getId()) {
            case R.id.back_left:
                onBackPressed();
                break;
            case R.id.bt_submit:
                boolean result = true;
                if (TextUtils.isEmpty(name)) {
                    MyUtils.showSnackbar(rootView,R.string.name_empty_show);
                    result = false;
                }
                if (result && !name.matches("[\u4e00-\u9fa5]+")) {
                    realName.setText("");
                    MyUtils.showSnackbar(rootView, R.string.name_not_standard_show);
                    result = false;
                }
                if (result && name.length() == 1 || name.length() > 5) {
                    MyUtils.showSnackbar(rootView,R.string.name_not_length_show);
                    result = false;
                }
                if (result) {
                    result = EditCheckUtil.IDCardValidate(identity, getActivity());
                    if (result) {
                //注册第2步
                fragmentMgr.beginTransaction()
                        .addToBackStack(TAG)
                        .replace(R.id.fragment_login_container, new RegisterThirdFragment())
                        .commit();
                    }
                }


                break;
        }
    }
}
