package com.jhl.encourage.model;

import android.widget.ImageView;

public class ReportButton {
	
	private int sickness;
	private ImageView imageView;
	private int state;
	private int position;
	
	public ReportButton(ImageView imageView, int sickcnes, int state, int position) {
		this.imageView = imageView;
		this.sickness = sickcnes;
		this.state = state;
		this.position = position;
	}
	
	public int getSickness() {
		return sickness;
	}
	public void setSickness(int sickness) {
		this.sickness = sickness;
	}
	public ImageView getImageView() {
		return imageView;
	}
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	
}
