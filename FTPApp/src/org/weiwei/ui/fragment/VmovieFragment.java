package org.weiwei.ui.fragment;

import java.util.List;

import org.weiwei.ftpapp.R;
import org.weiwei.model.Video;
import org.weiwei.utils.MediaUtils;
import org.weiwei.utils.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class VmovieFragment extends Fragment{

	private MediaUtils mUtils;
	private List<Video> mVideos;
	private Context context;
	private View v;
	private ListView listView;

	public VmovieFragment(Context context) {
		this.context = context;
		mUtils = new MediaUtils(context);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("TAST", "onCreateView");
		v = inflater.inflate(R.layout.fragment_video_movie, container,false);
		return v;
	}

	@Override
	public void onResume() {
		Log.i("TAST", "onResume");
		super.onResume();
		initView();	
	}
	
	private void initView() {
		listView = (ListView) v.findViewById(R.id.id_fragment_video_movie_lv);
		mVideos = mUtils.getVideo(mHandler);
	}

	//用来更新UI
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			listView.setAdapter(new MovieAdapter(context));
		}
		
	};
	
	private class MovieAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		
		public MovieAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return mVideos.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if(convertView==null){
				convertView = mInflater.inflate(R.layout.listitem_fragment_video_movie, parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView.findViewById(R.id.id_listitem_fragment_video_movie_image);
				holder.movieName=(TextView) convertView.findViewById(R.id.id_listitem_fragment_video_movie_name);
				holder.movieSize = (TextView) convertView.findViewById(R.id.id_listitem_fragment_video_movie_size);
				holder.movieDur = (TextView) convertView.findViewById(R.id.id_listitem_fragment_video_movie_duration);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			Video video = mVideos.get(position);
			holder.image.setImageResource(R.drawable.ic_easytransfer_video);
			String title = video.getTitle();
			if(title.length()>20){
				title = title.substring(0, 20)+"..";
			}
			holder.movieName.setText(title);
			holder.movieSize.setText(StringUtils.formatByte(video.getSize()));
			holder.movieDur.setText(StringUtils.getTime(video.getDuration()));
			
			return convertView;
		}

	}

	private final class ViewHolder {
		ImageView image;
		TextView movieName;
		TextView movieSize;
		TextView movieDur;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i("TAST", "onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		Log.i("TAST", "onAttach");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i("TAST", "onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		Log.i("TAST", "onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		Log.i("TAST", "onDetach");
		super.onDetach();
	}

	@Override
	public void onPause() {
		Log.i("TAST", "onPause");
		super.onPause();
	}



	@Override
	public void onStart() {
		Log.i("TAST", "onStart");
		super.onStart();
	}

	@Override
	public void onStop() {
		Log.i("TAST", "onStop");
		super.onStop();
	}
	
	
}
