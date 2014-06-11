package com.jhl.encourage.adapters;

import com.jhl.encourage.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class JHSicknessButtonsAdapter extends BaseAdapter {
	
	private Context context;
	
	Integer[] imageIDs = {
		    R.drawable.sore_throat,R.drawable.tired, R.drawable.back_pain, 
		    R.drawable.dizziness, R.drawable.cant_sleep, R.drawable.joint_pain,
		    R.drawable.dry_skin, R.drawable.nosebleed, R.drawable.shortness_of_breath, 
		    R.drawable.breathless, R.drawable.tingling_sensation,  R.drawable.other
	};
	
	
	public JHSicknessButtonsAdapter(Context context) {
		this.context = context;
	}
	
	@Override
	public Object getItem(int position) {
		 return position;
	}
	
	@Override
	public long getItemId(int position) {
		 return position;
	}
	@Override
	public int getCount() {
		return imageIDs.length;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
        if (convertView == null) {
        imageView = new ImageView(context);
        imageView.setLayoutParams(new
        GridView.LayoutParams(140, 140));
        imageView.setScaleType(
        ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(20, 20, 20, 20);
        } else {
        imageView = (ImageView) convertView;
        }
        imageView.setImageResource(imageIDs[position]);
        return imageView;
	}
}
