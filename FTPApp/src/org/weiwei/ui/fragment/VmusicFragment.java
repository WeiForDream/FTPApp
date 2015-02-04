package org.weiwei.ui.fragment;

import java.util.List;

import org.weiwei.ftpapp.R;
import org.weiwei.model.Audio;
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

public class VmusicFragment extends Fragment {

	private MediaUtils mUtils;
	private List<Audio> mAudios;
	private Context context;
	private View v;
	private ListView listView;

	public VmusicFragment(Context context) {
		this.context = context;
		mUtils = new MediaUtils(context);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("TAST", "onCreateView"+"---------VmusicFragment");
		v = inflater.inflate(R.layout.fragment_video_music, container,false);
		return v;
	}


	@Override
	public void onResume() {
		Log.i("TAST", "onResume"+"---------VmusicFragment");
		super.onResume();
		initView();
	}
	
	private void initView() {
		listView = (ListView) v.findViewById(R.id.id_fragment_video_music_lv);
		mAudios = mUtils.getAudio(mHandler);

//		Log.i("TAST",mAudios.size()+"  audio");
		
	}
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			listView.setAdapter(new MusicAdapter(context));
		}
		
	};
	

	private class MusicAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		
		public MusicAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return mAudios.size();
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
//			Log.i("TAST",position+" position  audio");
			ViewHolder holder = null;
			if(convertView==null){
				convertView = mInflater.inflate(R.layout.listitem_fragment_video_music, parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView.findViewById(R.id.id_listitem_fragment_video_music_image);
				holder.musicName=(TextView) convertView.findViewById(R.id.id_listitem_fragment_video_music_name);
				holder.musicSize = (TextView) convertView.findViewById(R.id.id_listitem_fragment_video_music_size);
				holder.musicDur = (TextView) convertView.findViewById(R.id.id_listitem_fragment_video_music_duration);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			Audio audio = mAudios.get(position);
			holder.image.setImageResource(R.drawable.ic_easytransfer_music);
			String title = audio.getTitle();
			if(title.length()>20){
				title = title.substring(0, 20)+"..";
			}
			holder.musicName.setText(title);
			holder.musicSize.setText(StringUtils.formatByte(audio.getSize()));
			holder.musicDur.setText(StringUtils.getTime(audio.getDuration()));
			
			return convertView;
		}

	}

	private final class ViewHolder {
		ImageView image;
		TextView musicName;
		TextView musicSize;
		TextView musicDur;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i("TAST", "onActivityCreated"+"---------VmusicFragment");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		Log.i("TAST", "onAttach"+"---------VmusicFragment");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i("TAST", "onCreate"+"---------VmusicFragment");
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		Log.i("TAST", "onDestroyView"+"---------VmusicFragment");
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		Log.i("TAST", "onDetach"+"---------VmusicFragment");
		super.onDetach();
	}

	@Override
	public void onPause() {
		Log.i("TAST", "onPause"+"---------VmusicFragment");
		super.onPause();
	}

	@Override
	public void onStart() {
		Log.i("TAST", "onStart"+"---------VmusicFragment");
		super.onStart();
	}

	@Override
	public void onStop() {
		Log.i("TAST", "onStop"+"---------VmusicFragment");
		super.onStop();
	}
	
	
}
