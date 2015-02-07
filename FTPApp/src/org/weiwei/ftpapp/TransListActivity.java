package org.weiwei.ftpapp;

import org.weiwei.application.MyApplication;
import org.weiwei.service.CoreService;
import org.weiwei.ui.adapter.TransListAdapter;
import org.weiwei.ui.view.TopBar;
import org.weiwei.ui.view.TopBar.topbarClickListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 传输列表
 * @author weiwei
 *
 */

public class TransListActivity extends Activity{

	//如果这个还没创建怎么办呢？没有创建那么Handler都还没有得到，如何更新？
	//Activity没创建，而handler也还没有，那么如何更新？
	//在没有UI界面的时候，就调用UI进行刷新，在有UI的情况下就进行界面更新？
	//也就是说在要通知线程
	
	private ListView mListView; //
	private TopBar mTopBar;
	
	private TransListAdapter mTransListAdapter = null;
	
	//application
	private MyApplication myApp;
	private CoreService coreService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trans_list);
		myApp = (MyApplication) getApplication();
		coreService = myApp.getCoreService();
		initView();
	}

	public void initView(){
		//listview
		mListView = (ListView) findViewById(R.id.id_activity_trans_list_lv);
		mTransListAdapter = new TransListAdapter(this, coreService.getmTasks(),coreService);
		mTransListAdapter.setListView(mListView);
		mTransListAdapter.setHandler(); //set handler 
		mListView.setAdapter(mTransListAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//点击暂停
			}
		});
		
		
		
		//topbar
		mTopBar = (TopBar) findViewById(R.id.id_activity_trans_list_topbar);
		mTopBar.setButtonVisable(false, TopBar.RIGHT_BUTTON);
		mTopBar.setOnTopBarClickListener(new topbarClickListener() {
			@Override
			public void rightClick() {
			}		
			@Override
			public void leftClick() {
				TransListActivity.this.finish();
			}
		});
	}
	

}
