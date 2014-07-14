package com.jhl.encourage.adapters;

import java.util.ArrayList;
import java.util.List;

import com.jhl.encourage.R;
import com.jhl.encourage.model.ReportButton;
import com.jhl.encourage.utilities.JHAppStateVariables;
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
	
	Integer[] selectedImageIDs = {
		    R.drawable.sore_throat_hover,R.drawable.tired_hover, R.drawable.back_pain_hover, 
		    R.drawable.dizziness_hover, R.drawable.cant_sleep_hover, R.drawable.joint_pain_hover,
		    R.drawable.dry_skin_hover, R.drawable.nosebleed_hover, R.drawable.shortness_of_breath_hover, 
		    R.drawable.breathless_hover, R.drawable.tingling_sensation_hover,  R.drawable.other_hover
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
		        GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		        imageView.setScaleType(
		        ImageView.ScaleType.CENTER_CROP);
		        imageView.setPadding(20, 20, 20, 20);
		imageView.setImageResource(imageId);
        //imageView.setBackground(null);
		imageView.setOnClickListener(new ButtonClickListener());       
		return imageView;
	}
	
	
	public void clearButtonSelections() {
		selectedButtons = new ArrayList<ReportButton>();
		JHAppStateVariables.clearReport();
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
					imageView.setImageResource(selectedImageIDs[button.getPosition()]);
					String sickness = getSicknessByButtonPosition(button.getPosition());
					JHAppStateVariables.addSickenss(sickness);
				}else {
					selectedButtons.remove(button);
					button.setState(JHConstants.BUTTON_UNSELECTED);
					
					imageView.setImageResource(imageIDs[button.getPosition()]);
					String sickness = getSicknessByButtonPosition(button.getPosition());
					JHAppStateVariables.removeSickenss(sickness);
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
		
		private String getSicknessByButtonPosition(int position){
			switch (position) {
			case 0:
				return JHConstants.SICKNESS_TEXT_sore_throat;
			case 1:
				return JHConstants.SICKNESS_TEXT_tired;
			case 2:
				return JHConstants.SICKNESS_TEXT_back_pain;
			case 3:
				return JHConstants.SICKNESS_TEXT_dizziness;
			case 4:
				return JHConstants.SICKNESS_TEXT_cant_sleep;
			case 5:
				return JHConstants.SICKNESS_TEXT_joint_pain;
			case 6:
				return JHConstants.SICKNESS_TEXT_dry_skin;
			case 7:
				return JHConstants.SICKNESS_TEXT_nosebleed;
			case 8:
				return JHConstants.SICKNESS_TEXT_shortness_of_breath;
			case 9:
				return JHConstants.SICKNESS_TEXT_breathless;
			case 10:
				return JHConstants.SICKNESS_TEXT_tingling_sensation;
			case 11:
				return JHConstants.SICKNESS_TEXT_other;
				
			}
			return null;
		}
	}
}
