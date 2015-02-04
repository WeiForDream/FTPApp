package org.weiwei.ui.fragment;

import java.util.List;

import org.weiwei.ftpapp.R;
import org.weiwei.model.AppInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AppFragmentAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<AppInfo> AppInfoList;

	public AppFragmentAdapter(Context context, List<AppInfo> AppInfoList) {
		mInflater = LayoutInflater.from(context);
		this.AppInfoList = AppInfoList;

	}

	@Override
	public int getCount() {
		return AppInfoList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AppViewHolder holder = null;
		if (convertView == null) {
			holder = new AppViewHolder();
			convertView = mInflater.inflate(R.layout.griditem_app_fragment,
					parent, false);
			holder.appIcon = (ImageView) convertView
					.findViewById(R.id.id_griditem_app_fragment_app_icon);
			holder.checkOn = (ImageView) convertView
					.findViewById(R.id.id_griditem_app_fragment_selected);
			holder.appName = (TextView) convertView
					.findViewById(R.id.id_griditem_app_fragment_app_name);
			holder.appSize = (TextView) convertView
					.findViewById(R.id.id_griditem_app_fragment_app_size);
			convertView.setTag(holder);
		} else {
			holder = (AppViewHolder) convertView.getTag();
		}
		AppInfo appInfo = AppInfoList.get(position);
		holder.appIcon.setImageDrawable(appInfo.getAppicon());

		String appname = appInfo.getAppname();
		if (appname.length() > 5) {
			appname = appname.substring(0, 5) + "..";
		}
		holder.appName.setText(appname);
		// holder.appSize.setText(appInfo.getAppname());
		if (appInfo.isIschaked()) {
			holder.checkOn.setVisibility(View.VISIBLE);
		} else {
			holder.checkOn.setVisibility(View.GONE);
		}
		return convertView;
	}

	// 更新被选择的状态
	public void updateChecked(int position, View view) {
		AppInfo appInfo = AppInfoList.get(position);
		ImageView image = (ImageView) view
				.findViewById(R.id.id_griditem_app_fragment_selected);
		boolean ischecked = appInfo.isIschaked();
		if (ischecked) {
			appInfo.setIschaked(false);
			image.setVisibility(View.GONE);
		} else {
			appInfo.setIschaked(true);
			image.setVisibility(View.VISIBLE);
		}
	}

	public List<AppInfo> getAppInfoList() {
		return AppInfoList;
	}

	public void setAppInfoList(List<AppInfo> appInfoList) {
		AppInfoList = appInfoList;
	}

	private final class AppViewHolder {

		public ImageView appIcon;
		public ImageView checkOn;
		public TextView appName;
		public TextView appSize;
	}
}
