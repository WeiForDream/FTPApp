package org.weiwei.adapter;

import java.util.List;

import org.weiwei.ftpapp.R;
import org.weiwei.model.Task;
import org.weiwei.service.CoreService;
import org.weiwei.ui.view.CirProgressLayout;
import org.weiwei.ui.view.CirProgressLayout.CirProgressClickListener;
import org.weiwei.ui.view.SectionedBaseAdapter;
import org.weiwei.utils.StringUtils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TransListAdapter extends SectionedBaseAdapter {

	private LayoutInflater mInflater;
	private List<Task> mDatas = null; 
	private CoreService coreService = null;
	private ListView listView;
	private List<Task> DoneList = null; //完成队列
	private String[] header = new String[]{"正在下载","下载完成"};
	private Context context;

	//首先获得的数据要进行处理
	
	//即分出正在下载的和下载完成的,那么这个状态如何获取呢？
	//每次下载有几个动作？
	//下载中，下载完成后从队列中取出,并将任务添加到完成队列中去
	
	
	public TransListAdapter(Context context, List<Task> datas) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
		mDatas = datas;	
	}
	
	public TransListAdapter(Context context,List<Task> datas,CoreService service){
		this(context, datas);
		this.context = context;
		coreService = service;
		DoneList = coreService.getDoneList();
	}

	public void setListView(ListView l){
		listView = l;
	}

	@Override
	public Object getItem(int section, int position) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public long getItemId(int section, int position) {
		// TODO Auto-generated method stub
		return 0;
	}


	//获取表头的数目
	@Override
	public int getSectionCount() {
		// TODO Auto-generated method stub
		return 2;
	}


	//获取对应表头的数目
	@Override
	public int getCountForSection(int section) {
		int result = 0;
		switch(section){
		case 0:
			result = mDatas.size(); //未完成队列
			break;
		case 1:
			result = DoneList.size(); //已完成队列
			break;
		}
		return result;
	}


	//绘制section里面的item 
	//要获取正确的position即根据传入的section和position建立正确的数据
	@Override
	public View getItemView(int section, int position, View convertView,
			ViewGroup parent) {
		TransListViewHolder holder = null;
		if(convertView==null){
			convertView = mInflater.inflate(R.layout.listitem_trans_list, parent, false);
			holder = new TransListViewHolder();
			holder.filename = (TextView) convertView.findViewById(R.id.id_listitem_trans_list_filename);
			holder.filesize = (TextView) convertView.findViewById(R.id.id_listitem_trans_list_filesize);
			holder.state = (TextView) convertView.findViewById(R.id.id_listitem_trans_list_state);
			holder.image = (ImageView) convertView.findViewById(R.id.id_listitem_trans_list_image);
//			holder.progress = (MasterLayout) convertView.findViewById(R.id.id_listitem_trans_list_progressbar);
			holder.progress = (CirProgressLayout) convertView.findViewById(R.id.id_listitem_trans_list_progressbar);
			convertView.setTag(holder);
		}else{
			holder = (TransListViewHolder) convertView.getTag();
		}
		if(section==0){
			holder.filename.setText(mDatas.get(position).getTaskName());
			holder.filesize.setText(StringUtils.formatByte(mDatas.get(position).getFilesize()));
			holder.progress.setProgress(mDatas.get(position).getProgressValue());
			holder.progress.setCProListener(new CirProgressClickListener() {
				
				@Override
				public void click() {
					// TODO Auto-generated method stub
					
				}
			});
		}else if(section==1){
			holder.filename.setText(DoneList.get(position).getTaskName());
			holder.filesize.setText(StringUtils.formatByte(DoneList.get(position).getFilesize()));
			holder.progress.setVisibility(View.INVISIBLE);
		}

		
		return convertView;
	}



	//绘制section 的header
	@Override
	public View getSectionHeaderView(int section, View convertView,
			ViewGroup parent) {
		HeaderViewHolder holder = null;
		if(convertView==null){
			convertView = mInflater.inflate(R.layout.listitem_trans_list_header, parent, false);
			holder = new HeaderViewHolder();
			holder.tx = (TextView) convertView.findViewById(R.id.id_listitem_trans_list_header_tv);
			convertView.setTag(holder);
		}else{
			holder = (HeaderViewHolder) convertView.getTag();
		}
		holder.tx.setText(header[section]);
		return convertView;
	}
	
	protected final class HeaderViewHolder{
		TextView tx;
	}
	
	
	
	// 要更新就要把这个handler对象交给其他线程
	private Handler mHandler = new Handler(){
		
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case 1:
				Task task = (Task) msg.obj;   //获取当前task的引用
				updateItem(task);//利用当前task更新对应的数据
				break;
			}

		}
		
	};
	

	// 更新某个UI,只有在可见时更新
	private  void updateItem(Task task) {
		int visiblePos = listView.getFirstVisiblePosition();//获取当前可见的第一个item
		int index = mDatas.indexOf(task);//获取当前task在mDatas中的位置(关键)
		if(index==-1) return;
//		Log.i("TAG","firstVisible  "+visiblePos);
		int offset = getTaskPositon(index) - visiblePos; //计算偏移量,这个偏移量是否是正确的？
//		Log.e("TAG", "index=" + index + "  visiblePos=" + visiblePos + "  offset="
//				+ offset);
		// 只有在可见区域才更新
		int progressValue = task.getProgressValue();
		if (offset < 0 || offset >= listView.getChildCount()){
			if(progressValue>=100){
				DoneList.add(task);//将完成的任务移动到完成队列
				mDatas.remove(task);//从下载队列中移除

				notifyDataSetChanged();
			}
			return;
		}

		// Log.i("TAG", offset+"   "+listView.getChildCount());

		View view = listView.getChildAt(offset); // 要注意判断越界错误

		TransListViewHolder holder = (TransListViewHolder) view.getTag();
		holder.filename.setText(task.getTaskName());
		holder.filesize.setText(StringUtils.formatByte(task.getFilesize()));

//		holder.progress.cusview.setupprogress(progressValue);//设置进度条
		holder.progress.setProgress(progressValue);
		if(progressValue>=100){
			DoneList.add(task);//将完成的任务移动到完成队列
			mDatas.remove(task);//从下载队列中移除

			notifyDataSetChanged();
		}

	
	}

	
	private int getTaskPositon(int index){
		return index+1; //在listview中item的位置
	}
	
	//给线程们传递handler
	public void setHandler(){
		if(coreService!=null){
			Log.i("TAG", "adpate is set handler");
			coreService.setHandler(mHandler); 
		}
	}
	

	public void removeHandler(){
		if(coreService!=null){
			coreService.removeHandler();
		}
	}



}
