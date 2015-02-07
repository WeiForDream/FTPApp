package org.weiwei.ftpapp;

import it.sauronsoftware.ftp4j.FTPFile;

import org.weiwei.application.MyApplication;
import org.weiwei.ftp.FTPClient;
import org.weiwei.model.ConServer;
import org.weiwei.model.Task;
import org.weiwei.ui.adapter.ServerFilesAdapter;
import org.weiwei.ui.view.TopBar;
import org.weiwei.ui.view.TopBar.topbarClickListener;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 浏览服务器端的文件,并对文件进行操作,进入该页面后，加载服务器页面，或者进行下载
 * 
 * @author weiwei
 * 
 */

public class ServerFilesActivity extends Activity {

	private TopBar mTopBar;
	private ConServer currentServer;
	private int currentServerPosition;
	private ListView mFileList;
	private ServerFilesAdapter mFileAdapter;
	
	//application
	private MyApplication myApp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server_files);
		myApp = (MyApplication) getApplication();
		initServer();
		initView();
		initListView();
		new ListTask().execute("currentDir");

	}

	// 初始化服务
	public void initServer() {
		currentServerPosition = this.getIntent().getIntExtra(
				"currentServerPosition", -1);
		currentServer = ServerListActivity.mServerList
				.get(currentServerPosition);// 获取点击当前服务器
	}

	// 初始化界面
	public void initView() {
		mTopBar = (TopBar) findViewById(R.id.id_server_files_topbar);
		mTopBar.setTitle(currentServer.getServerName());
		mTopBar.setOnTopBarClickListener(new topbarClickListener() {
			@Override
			public void rightClick() {
				Intent intent = new Intent(ServerFilesActivity.this,
						TransListActivity.class);
				startActivity(intent);//
			}

			@Override
			public void leftClick() {
				ServerFilesActivity.this.finish();
			}
		});
	}

	// 初始化列表
	public void initListView() {
		mFileList = (ListView) findViewById(R.id.id_activity_server_files_lv);
		mFileAdapter = new ServerFilesAdapter(this, null);
		mFileList.setAdapter(mFileAdapter);
		mFileList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				FTPFile file = mFileAdapter.getFTPFile(position);
				if (file != null && file.getType() == FTPFile.TYPE_DIRECTORY) {
					new ListTask().execute(file.getName());
				}
			}
		});
		mFileList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Task task = new Task();//最开始的任务
				FTPFile file = mFileAdapter.getFTPFile(position);
				task.setTaskName(file.getName());
				task.setRemoteFileName(file.getName());
				showMessage(file.getName());
				task.setLocalFilename(file.getName());
				task.setFilesize(file.getSize());
				if(myApp!=null){
					myApp.getCoreService().addTask(task,currentServer.getmClient());//将任务添加到列表
				}
				showMessage("已经加入下载列表");
				return false;
			}
		});
	}

	// 获取当前服务器的文件列表
	private class ListTask extends AsyncTask<String, Void, FTPFile[]> {

		@Override
		protected FTPFile[] doInBackground(String... params) {
			FTPFile[] files = null;
			if (currentServer != null) {
				FTPClient mClient = currentServer.getmClient();
				if (mClient != null) {
					if (params[0] == "currentDir") {
						files = mClient.list(); // 获取服务器列表
					} else {
						files = mClient.changeDir(params[0]);
					}
				}
			}
			return files;
		}

		@Override
		protected void onPostExecute(FTPFile[] result) {
			super.onPostExecute(result);
			mFileAdapter.setmDatas(result);
			mFileAdapter.notifyDataSetChanged();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i("TAG", "onPause()");

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i("TAG", "onDestroy()");
	}

	private void showMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

}
