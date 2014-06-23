package com.jhl.encourage.adapters;

import java.util.ArrayList;
import java.util.List;

import com.jhl.encourage.R;
import com.jhl.encourage.adapters.JHSicknessButtonsAdapter.ButtonClickListener;
import com.jhl.encourage.model.ReportButton;
import com.jhl.encourage.utilities.JHConstants;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class JHEmotonalButtonsAdapter extends BaseAdapter {
	
	private Context context;
	List<ReportButton> buttons;
	
	List<ReportButton> selectedButtons = new ArrayList<ReportButton>();
	
	Integer[] imageIDs = {
			R.drawable.worried,R.drawable.anxious,R.drawable.depressed,R.drawable.angry, 
			R.drawable.sad, R.drawable.happy, R.drawable.restless,R.drawable.cant_sleep_e
		     
	};
	int[] sickenesses = {JHConstants.EMO_worried, JHConstants.EMO_Anxious ,	JHConstants.EMO_Depressed,JHConstants.EMO_Angry,
			JHConstants.EMO_Sad,	JHConstants.EMO_Happy,	JHConstants.EMO_Restless,	JHConstants.EMO_cant_sleep	
			};
	
	public JHEmotonalButtonsAdapter(Context context) {
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
		        GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
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
