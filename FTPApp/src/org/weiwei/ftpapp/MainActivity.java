package org.weiwei.ftpapp;

import java.util.ArrayList;
import java.util.List;

import org.weiwei.adapter.MainAdapter;
import org.weiwei.application.MyApplication;
import org.weiwei.service.CoreService;
import org.weiwei.service.CoreService.MyBinder;
import org.weiwei.ui.fragment.AppFragment;
import org.weiwei.ui.fragment.FileFragment;
import org.weiwei.ui.fragment.PictureFragment;
import org.weiwei.ui.fragment.VideoFragment;
import org.weiwei.ui.view.MainViewPager;
import org.weiwei.ui.view.SlidingMenuLayout;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	private MainViewPager mViewPager;
	private MainAdapter mAdapter;
	private List<Fragment> mDatas = new ArrayList<Fragment>();
	private TextView mAppTextView, mPiectureTextView, mVideoTextView,
			mFileTextView;
	private ImageView mTabLine;
	private int mScreen1_4;
	
	//底部菜单
	private ImageView BottomLeftImage,BottomCenterImage,BottomRightImage;

	// 动画
	private int[] res = { R.id.id_icon_a, R.id.id_icon_b, R.id.id_icon_c,
			R.id.id_icon_d, R.id.id_icon_e };
	private List<ImageView> imageViewList = new ArrayList<ImageView>();
	private boolean open = false;

	// Service
	private CoreService mCoreService = null;

	// application
	private MyApplication myApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		SlidingMenu menu = new SlidingMenu(this);
//        menu.setMode(SlidingMenu.LEFT);
//        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//        menu.setShadowWidthRes(R.dimen.shadow_width);
////        menu.setShadowDrawable(R.drawable.shadow);
//        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
//        menu.setFadeDegree(0.35f);
//        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
//        menu.setMenu(R.layout.sliding_menu);
		

		
		myApp = (MyApplication) getApplication();
		initService();
		initTabLine();
		initView();
//		initImageView();
	}

	// 启动服务
	private void initService() {
		Intent service = new Intent(MainActivity.this, CoreService.class);
		bindService(service, conn, Context.BIND_AUTO_CREATE);
	}

	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// 绑定时调用
			MyBinder binder = (MyBinder) service;
			mCoreService = binder.getService(); //
			myApp.setCoreService(mCoreService);
		}
	};

	// 初始化tabline
	private void initTabLine() {
		mTabLine = (ImageView) findViewById(R.id.id_tabline);
		// 获取当前屏幕宽度
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);
		mScreen1_4 = outMetrics.widthPixels / 4;
		// 将tabline宽度设为屏幕宽度的1/4
		LayoutParams lp = (LayoutParams) mTabLine.getLayoutParams();
		lp.width = mScreen1_4;
		mTabLine.setLayoutParams(lp);
	}

	// 初始化界面
	private void initView() {
		mAppTextView = (TextView) findViewById(R.id.id_title_tv_app);
		mAppTextView.setTextColor(getResources().getColor(R.color.my_green));
		mPiectureTextView = (TextView) findViewById(R.id.id_title_tv_picture);
		mVideoTextView = (TextView) findViewById(R.id.id_title_tv_video);
		mFileTextView = (TextView) findViewById(R.id.id_title_tv_file);
		mViewPager = (MainViewPager) findViewById(R.id.id_main_viewpager);
		
		AppFragment app = new AppFragment();
		PictureFragment picture = new PictureFragment();
		VideoFragment video = new VideoFragment();
		FileFragment file = new FileFragment();
		mDatas.add(app);
		mDatas.add(picture);
		mDatas.add(video);
		mDatas.add(file);
		mAdapter = new MainAdapter(getSupportFragmentManager(), mDatas);
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOffscreenPageLimit(4);
		mViewPager.setOnPageChangeListener(new TabListener());
		
		//bottom
		BottomLeftImage = (ImageView) findViewById(R.id.id_activity_main_bottom_left_image);
		BottomRightImage = (ImageView) findViewById(R.id.id_activity_main_bottom_right_image);
		BottomCenterImage = (ImageView)findViewById(R.id.id_activity_main_bottom_center_image);

//		menu.setOnClickListener(new BottomClickListener());
		BottomLeftImage.setOnClickListener(new BottomClickListener());
		BottomRightImage.setOnClickListener(new BottomClickListener());
		BottomCenterImage.setOnClickListener(new BottomClickListener());
	}

	//
	protected class BottomClickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.id_activity_main_bottom_left_image:
//				menu.openMenu();
//				mViewPager.setCurrentItem(0);
				Intent intentL = new Intent(MainActivity.this,MoreOperation.class);
				startActivity(intentL);
				break;
			case R.id.id_activity_main_bottom_right_image:
				Intent intent = new Intent(MainActivity.this,
						ServerListActivity.class);
				startActivity(intent);
				break;
			case R.id.id_activity_main_bottom_center_image:
				
				break;
				
			}
			
		}
		
	}
	
	
	// tab指示器
	protected class TabListener implements ViewPager.OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int position) {

//			Log.i("TAG", position + "");
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPx) {
			LayoutParams lp = (LayoutParams) mTabLine.getLayoutParams();
			// 不断设置Tabline与左侧的距离
			lp.leftMargin = (int) (position * mScreen1_4 + positionOffset
					* mScreen1_4);
			mTabLine.setLayoutParams(lp);
		}

		@Override
		public void onPageSelected(int position) {
			resetTextColor();
			switch (position) {
			case 0:
				mAppTextView.setTextColor(getResources().getColor(R.color.my_green));
				break;
			case 1:
				mPiectureTextView.setTextColor(getResources().getColor(R.color.my_green));
				break;
			case 2:
				mVideoTextView.setTextColor(getResources().getColor(R.color.my_green));
				break;
			case 3:
				mFileTextView.setTextColor(getResources().getColor(R.color.my_green));
				break;
			}

		}

	}

	// 重新设置页面颜色
	public void resetTextColor() {
		mAppTextView.setTextColor(Color.BLACK);
		mPiectureTextView.setTextColor(Color.BLACK);
		mVideoTextView.setTextColor(Color.BLACK);
		mFileTextView.setTextColor(Color.BLACK);
	}

	// Tab监听
	protected class TabClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub

		}

	}

	// 初始化imagerview
	private void initImageView() {
		for (int i = 0; i < res.length; i++) {
			ImageView imageView = (ImageView) findViewById(res[i]);
			imageView.setOnClickListener(new ImageViewClick());
			imageViewList.add(imageView);
		}

	}

	// 动画菜单
	protected class ImageViewClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.id_icon_a:
				if (open)
					closeAnim();
				else
					startAnim();
				break;
			case R.id.id_icon_b:
				Intent intent = new Intent(MainActivity.this,
						ServerListActivity.class);
				startActivity(intent);
				break;
			case R.id.id_icon_c:
				Toast.makeText(MainActivity.this, "R.id.id_icon_c",
						Toast.LENGTH_LONG).show();
				break;
			case R.id.id_icon_d:
				Toast.makeText(MainActivity.this, "R.id.id_icon_d",
						Toast.LENGTH_LONG).show();
				break;
			case R.id.id_icon_e:
				Toast.makeText(MainActivity.this, "R.id.id_icon_e",
						Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}

		}

	}

	// 打开菜单
	private void startAnim() {
		for (int i = 1; i < res.length; i++) {
			ObjectAnimator animator = ObjectAnimator.ofFloat(
					imageViewList.get(i), "translationX", 0F, -150F * i);
			animator.setDuration(300);
			animator.setStartDelay(i * 200);
			animator.start();
			open = true;
		}
	}

	// 关闭菜单
	private void closeAnim() {
		for (int i = 1; i < res.length; i++) {
			ObjectAnimator animator = ObjectAnimator.ofFloat(
					imageViewList.get(i), "translationX", -150F * i, 0F);
			animator.setDuration(300);
			animator.setStartDelay(i * 200);
			animator.start();
			open = false;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			moveTaskToBack(true);
			
		}
		return false;
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		Toast.makeText(this, "hello", Toast.LENGTH_LONG).show();
	}

	
	
	
}
