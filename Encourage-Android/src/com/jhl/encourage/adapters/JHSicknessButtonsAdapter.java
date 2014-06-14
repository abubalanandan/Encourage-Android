package com.jhl.encourage.adapters;

import java.util.ArrayList;
import java.util.List;

import com.jhl.encourage.R;
import com.jhl.encourage.model.ReportButton;
import com.jhl.encourage.utilities.JHConstants;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class JHSicknessButtonsAdapter extends BaseAdapter {
	
	private Context context;
	List<ReportButton> buttons;
	
	List<ReportButton> selectedButtons = new ArrayList<ReportButton>();
	
	Integer[] imageIDs = {
		    R.drawable.sore_throat,R.drawable.tired, R.drawable.back_pain, 
		    R.drawable.dizziness, R.drawable.cant_sleep, R.drawable.joint_pain,
		    R.drawable.dry_skin, R.drawable.nosebleed, R.drawable.shortness_of_breath, 
		    R.drawable.breathless, R.drawable.tingling_sensation,  R.drawable.other
	};
	int[] sickenesses = {JHConstants.SICKNESS_sore_throat, JHConstants.SICKNESS_tired ,	JHConstants.SICKNESS_back_pain,JHConstants.SICKNESS_dizziness,
			JHConstants.SICKNESS_cant_sleep,	JHConstants.SICKNESS_joint_pain,	JHConstants.SICKNESS_dry_skin,	JHConstants.SICKNESS_nosebleed,	
			JHConstants.SICKNESS_shortness_of_breath,
			JHConstants.SICKNESS_breathless ,	JHConstants.SICKNESS_tingling_sensation,	JHConstants.SICKNESS_other};
	
	public JHSicknessButtonsAdapter(Context context) {
		this.context = context;
		setButtons();
	}
	
	private void setButtons(){
		List<ReportButton> buttons = new ArrayList<ReportButton>();
		
		ReportButton button;
		
		for(int i = 0; i< imageIDs.length; i++ ){
			button = new ReportButton(getButtonImageView(imageIDs[i]), sickenesses[i], JHConstants.BUTTON_UNSELECTED, i);
			buttons.add(button);
			
		}
		this.buttons = buttons;
	}
	
	private ImageView getButtonImageView (int imageId) {
		ImageView imageView = new ImageView(context);
		imageView.setLayoutParams(new
		        GridView.LayoutParams(140, 140));
		        imageView.setScaleType(
		        ImageView.ScaleType.CENTER_CROP);
		        imageView.setPadding(20, 20, 20, 20);
		imageView.setImageResource(imageId);
		imageView.setOnClickListener(new ButtonClickListener());       
		return imageView;
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
		ReportButton button = buttons.get(position);
		ImageView imageView = button.getImageView();
        return imageView;
	}
	
	
	class ButtonClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			ReportButton button = getButtonByView(v);
			ImageView imageView = button.getImageView();
			if ( button != null ){
				if (button.getState() == JHConstants.BUTTON_UNSELECTED){
					selectedButtons.add(button);
					button.setState(JHConstants.BUTTON_SELECTED);
					imageView.setImageResource(R.drawable.ic_action_add_group);
				}else {
					selectedButtons.remove(button);
					button.setState(JHConstants.BUTTON_UNSELECTED);
					imageView.setImageResource(imageIDs[button.getPosition()]);
				}
			}
		}
		private ReportButton getButtonByView(View v){
			ImageView imageView = (ImageView)v;
			for(ReportButton button : buttons){
				if (imageView == button.getImageView()){
					return button;
				}
			}
			return null;
		}
	}
}
