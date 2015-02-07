package org.weiwei.ftpapp;

import java.util.ArrayList;
import java.util.List;

import org.weiwei.ftp.FTPClient;
import org.weiwei.model.ConServer;
import org.weiwei.ui.adapter.ServerListAdapter;
import org.weiwei.ui.dialog.LoginDialog;
import org.weiwei.ui.view.TopBar;
import org.weiwei.ui.view.TopBar.topbarClickListener;
import org.weiwei.utils.TimeUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 服务器列表,浏览当前登录的服务器,点击进入服务器可以浏览服务器文件内容
 * 
 * @author weiwei
 * 
 */
public class ServerListActivity extends Activity implements OnClickListener,OnItemClickListener {

	private LinearLayout mLoginLayout, mLogoutLayout;
	private ListView servers;
	private ServerListAdapter mAdapter;
	private LoginDialog mLoginDialog;
	private TopBar topBar;
	
	public static List<ConServer> mServerList = new ArrayList<ConServer>(); // 服务器列表

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server_list);
		initView();

	}

	private void initView() {
		mLoginLayout = (LinearLayout) findViewById(R.id.id_service_list_ll_login);
		mLogoutLayout = (LinearLayout) findViewById(R.id.id_service_list_ll_trans_list);
		mLoginLayout.setOnClickListener(this);
		mLogoutLayout.setOnClickListener(this);
		servers = (ListView) findViewById(R.id.id_service_list_listview);
		mAdapter = new ServerListAdapter(this,mServerList);
		servers.setAdapter(mAdapter);
		servers.setOnItemClickListener(this);
		servers.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				
				return false;
			}
		});
		mLoginDialog = new LoginDialog(this);
		topBar = (TopBar)findViewById(R.id.id_server_list_topbar);
		topBar.setOnTopBarClickListener(new topbarClickListener() {
			
			@Override
			public void rightClick() {
				
			}
			
			@Override
			public void leftClick() {
				ServerListActivity.this.finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_service_list_ll_login:
			mLoginDialog.show();
			break;
		case R.id.id_service_list_ll_trans_list:
			Intent intent = new Intent(this,TransListActivity.class);
			startActivity(intent); //打开activity
			break;
		case R.id.id_dialog_login_bt_yes:
			new LoginTask().execute(mLoginDialog.getHost(),
					mLoginDialog.getPort(), mLoginDialog.getUsername(),
					mLoginDialog.getPassword());
			mLoginDialog.dismiss();
			break;
		case R.id.id_dialog_login_bt_cancel:
			mLoginDialog.dismiss();
			break;
		default:
			break;
		}

	}

	// 登录成功后添加一个
	private class LoginTask extends AsyncTask<String, Void, String> {
		private FTPClient mClient;
		private String host,port;
		@Override
		protected String doInBackground(String... params) {
			mClient = new FTPClient();
			mClient.connect(params[0], Integer.valueOf(params[1])); //连接
			String result = mClient.login(params[2], params[3]);  //登录
			host = params[0];
			port = params[1];
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			// 登录成功后添加到List里面
			if(result.equals("SUCCESS")){
				ConServer cServer = new ConServer();
				cServer.setmClient(mClient);
				cServer.setServerName("我的服务器");
				cServer.setState(true);
				cServer.setHostAndPort(host+":"+port);
				cServer.setLastConnectTime(TimeUtils.getCurrentTime());
				mServerList.add(cServer); //将任务加入到列表中去
				mAdapter.notifyDataSetChanged();//更新数据
			}else{
				Toast.makeText(ServerListActivity.this, "ERROR", Toast.LENGTH_LONG).show();
			}

		}

		@Override
		protected void onProgressUpdate(Void... values) {

			super.onProgressUpdate(values);
		}

	}

	//ListView  Item被点击时进入到下一个页面即
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//进入下一个页面之前要传递参数过去
		Intent intent = new Intent(ServerListActivity.this,ServerFilesActivity.class);
		intent.putExtra("currentServerPosition", position);
		startActivity(intent);
		
	}

	@Override
	protected void onDestroy() {
		//把当前的serverList写入数据库
		super.onDestroy();
	}


	

	
}
