package haowei.computer.goldbowl.ui.sign;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import haowei.computer.goldbowl.R;
import haowei.computer.goldbowl.base.BaseFragment;

/**
 * Created by Administrator on 2016/12/22.
 */

public class RegisterThirdFragment extends BaseFragment {
    @BindView(R.id.tv_register_title)TextView title;
    @BindView(R.id.tv_register_success)TextView registerSuccess;
    @BindView(R.id.img_upload_front)ImageView uploadFront;
    @BindView(R.id.img_upload_reverse)ImageView ploadReverse;
    @Override
    protected int getContentView() {
        return R.layout.fragment_register_third;
    }

    @Override
    protected void updateUI() {
        title.setText(R.string.tv_identity_verify);
        registerSuccess.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));

    }
    @OnClick({R.id.back_left,R.id.btn_register_success,R.id.img_upload_front,R.id.img_upload_reverse,R.id.tv_skip})public void onClick(View view){
        switch (view.getId()){
            case R.id.back_left:
                onBackPressed();
                break;
            case R.id.img_upload_front:

                break;
            case R.id.img_upload_reverse:

                break;
            case R.id.tv_skip:

                break;
            case R.id.btn_register_success:
                System.out.println("打印"+Math.sqrt(2016));

                break;
        }
    }
}
