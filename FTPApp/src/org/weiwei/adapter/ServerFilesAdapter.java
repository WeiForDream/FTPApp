package org.weiwei.adapter;

import it.sauronsoftware.ftp4j.FTPFile;

import org.weiwei.ftpapp.R;
import org.weiwei.utils.StringUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ServerFilesAdapter extends BaseAdapter{

	private FTPFile[] mDatas;
	private LayoutInflater mInflater;
	
	public ServerFilesAdapter(Context context,FTPFile[] datas){
		mInflater = LayoutInflater.from(context);
		mDatas = datas;
	}
	
	@Override
	public int getCount() {
		if(mDatas==null){
			return 0;
		}
		return mDatas.length;
	}

	@Override
	public Object getItem(int arg0) {

		return null;
	}

	@Override
	public long getItemId(int arg0) {

		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ServerFilesViewHolder holder = null;
		if(convertView==null){
			holder = new ServerFilesViewHolder();
			convertView = mInflater.inflate(R.layout.listitem_server_files, parent,false);
			holder.image = (ImageView) convertView.findViewById(R.id.id_listitem_server_file_image);
			holder.filename = (TextView) convertView.findViewById(R.id.id_listitem_server_file_name);
			holder.filesize = (TextView) convertView.findViewById(R.id.id_listitem_server_file_size);
			convertView.setTag(holder);
		}else{
			holder = (ServerFilesViewHolder) convertView.getTag();
		}

		holder.filename.setText(mDatas[position].getName());

		
		int type = mDatas[position].getType();
		if(type==FTPFile.TYPE_FILE){
			holder.image.setImageResource(R.drawable.ic_easytransfer_file);
			holder.filesize.setText(StringUtils.formatByte(mDatas[position].getSize()));
		}else if(type==FTPFile.TYPE_DIRECTORY){
			holder.image.setImageResource(R.drawable.ic_easytransfer_folder);
			holder.filesize.setText("");
		}
		
		return convertView;
	}

	public FTPFile[] getmDatas() {
		return mDatas;
	}

	public void setmDatas(FTPFile[] mDatas) {
		this.mDatas = mDatas;
	}

	public LayoutInflater getmInflater() {
		return mInflater;
	}

	public void setmInflater(LayoutInflater mInflater) {
		this.mInflater = mInflater;
	}
	
	public FTPFile getFTPFile(int position){
		FTPFile file = mDatas[position];
		if(file!=null){
			return file;
		}
		return null;
	}
	
}
