package com.jhl.encourage.adapters;

import java.util.ArrayList;
import java.util.List;

import com.jhl.encourage.R;
import com.jhl.encourage.adapters.JHSicknessButtonsAdapter.ButtonClickListener;
import com.jhl.encourage.model.ReportButton;
import com.jhl.encourage.utilities.JHAppStateVariables;
import com.jhl.encourage.utilities.JHConstants;
import com.jhl.encourage.utilities.JHUtility;

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
			R.drawable.sad, R.drawable.happy, R.drawable.restless,R.drawable.cant_sleep
		     
	};
	
	Integer[] selectedImageIDs = {
			R.drawable.worried_hover,R.drawable.anxious_hover,R.drawable.depresed_hover,R.drawable.angry_hover, 
			R.drawable.sad_hover, R.drawable.happy_hover, R.drawable.restless_hover,R.drawable.cant_sleep_hover
		     
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
	
	private void clearSelections(){
		for(ReportButton button:buttons){
			button.setState(JHConstants.BUTTON_UNSELECTED);
			button.getImageView().setImageResource(imageIDs[button.getPosition()]);
		}
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
	
	public void clearButtonSelections() {
		selectedButtons = new ArrayList<ReportButton>();
		clearSelections();
//		setButtons();
//		this.notifyDataSetChanged();
		JHAppStateVariables.clearReport();
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
					imageView.setImageResource(selectedImageIDs[button.getPosition()]);
					String emotional = getEmotionalByButtonPosition(button.getPosition());
					JHAppStateVariables.addEmotionals(emotional);
				}else {
					selectedButtons.remove(button);
					button.setState(JHConstants.BUTTON_UNSELECTED);
					imageView.setImageResource(imageIDs[button.getPosition()]);
					String emotional = getEmotionalByButtonPosition(button.getPosition());
					JHAppStateVariables.removeEmotionals(emotional);
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
		
		private String getEmotionalByButtonPosition(int position){
			switch (position) {
			case 0:
				return JHConstants.EMO_TEXT_worried;
			case 1:
				return JHConstants.EMO_TEXT_Anxious;
			case 2:
				return JHConstants.EMO_TEXT_Depressed;
			case 3:
				return JHConstants.EMO_TEXT_Angry;
			case 4:
				return JHConstants.EMO_TEXT_Sad;
			case 5:
				return JHConstants.EMO_TEXT_Happy;
			case 6:
				return JHConstants.EMO_TEXT_Restless;
			case 7:
				return JHConstants.EMO_TEXT_cant_sleep;
				
			}
			return null;
		}
	}
}
