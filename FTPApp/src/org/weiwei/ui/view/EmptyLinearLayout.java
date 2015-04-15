package org.weiwei.ui.view;

import org.weiwei.ui.activity.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
/**
 * 空界面
 * @author weiwei
 *
 */
public class EmptyLinearLayout extends LinearLayout{

	private Context context;
	
	public EmptyLinearLayout(Context context) {
		this(context,null);
		this.context = context;
		initView();
	}

	public EmptyLinearLayout(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}
	
	public EmptyLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);		
	}

	private void initView(){
		Log.i("EMPTY","TEST");
		LayoutInflater.from(context).inflate(R.layout.custom_layout_empty, null, false);
	}
	
	//是否显示该界面
	public void showEmpty(boolean isShow){
		setVisibility(isShow?View.VISIBLE:View.GONE); 
	}

	
	

}
