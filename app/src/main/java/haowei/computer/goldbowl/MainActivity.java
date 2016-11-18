package haowei.computer.goldbowl;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
	private Button button;
	private PopupWindow mPopupWindow;
	private View popView;
	private PasswordView mPsdView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/************* 第一种用法————开始 ***************/
		setContentView(R.layout.activity_main1);
		button=(Button) findViewById(R.id.button);
		button.setOnClickListener(this);




	}
	public void backgroundAlpha(float bgAlpha)
	{
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = bgAlpha; //0.0-1.0
		getWindow().setAttributes(lp);
	}
	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.button:
				showPopupWindow(view);
				break;
		}

	}

	private void showPopupWindow(View view ) {
		// 初始化弹窗
		popView = View.inflate(this, R.layout.layout_pop_window, null);
		mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		mPsdView = (PasswordView) popView.findViewById(R.id.mPsdView);


		mPopupWindow.setTouchable(true);

		mPopupWindow.setOutsideTouchable(true);
		mPsdView.setOnFinishInput(new OnPasswordInputFinish() {
			@Override
			public void inputFinish() {
				// 输入完成后我们简单显示一下输入的密码
				// 也就是说——>实现你的交易逻辑什么的在这里写
				Toast.makeText(MainActivity.this, mPsdView.getStrPassword(),
						Toast.LENGTH_SHORT).show();
			}
		});
		mPsdView.getCancelImageView().setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(MainActivity.this, "返回键",
								Toast.LENGTH_SHORT).show();
					}
				});
		mPsdView.getForgetTextView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(MainActivity.this, "跳转到忘记密码的页面！",
						Toast.LENGTH_SHORT).show();
			}
		});
		mPopupWindow.setTouchInterceptor(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				Log.i("mengdd", "onTouch : ");

				return false;
				// 这里如果返回true的话，touch事件将被拦截
				// 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
			}
		});
		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		// 我觉得这里是API的一个bug
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x50000000));
		//透明度50%
		backgroundAlpha(0.5f);
		mPopupWindow.setOnDismissListener(new poponDismissListener());

		// 设置好参数之后再show(我这里在底部弹出)
		mPopupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
	}

	class poponDismissListener implements PopupWindow.OnDismissListener {

		@Override
		public void onDismiss() {
			// TODO Auto-generated method stub
			//Log.v("List_noteTypeActivity:", "我是关闭事件");
			backgroundAlpha(1f);
		}

	}

}
