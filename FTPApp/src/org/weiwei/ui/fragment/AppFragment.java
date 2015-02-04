package org.weiwei.ui.fragment;

import org.weiwei.ftpapp.OperationActivity;
import org.weiwei.ftpapp.R;
import org.weiwei.service.AppInfoProvider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;

public class AppFragment extends Fragment {

	private GridView mGridView;
	private AppFragmentAdapter mAppAdapter;
	private AppInfoProvider provider;
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_app, container, false);
		initView();
		return view;
	}

	private void initView() {
		mGridView = (GridView) view.findViewById(R.id.id_fragment_app_gridview);
		provider = new AppInfoProvider(getActivity());
		mAppAdapter = new AppFragmentAdapter(getActivity(),
				provider.getInstalledApps());
		mGridView.setAdapter(mAppAdapter);

		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mAppAdapter.updateChecked(position, view);
			}
		});
		
		mGridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(),OperationActivity.class);
				startActivityForResult(intent, 2);
				return false;
			}
		});
	}



}
