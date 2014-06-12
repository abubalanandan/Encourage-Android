package com.jhl.encourage.adapters;

import java.util.ArrayList;
import java.util.List;

import com.jhl.encourage.R;
import com.jhl.encourage.model.Contact;

import com.jhl.encourage.utilities.JHConstants;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


public class JHContactAdapter extends ArrayAdapter<Contact> {
	private List<Contact> contacts;
	private Context context;
	
	public JHContactAdapter(Context context, int textViewResourceId, List<Contact> contacts) {
		   super(context, textViewResourceId, contacts);
		   this.contacts = new ArrayList<Contact>();
		   this.contacts.addAll(contacts);
		   this.context = context;
	}
	
	private class ViewHolder {
		TextView code;
		CheckBox name;
	}
	
	public List<Contact>  getContacts() {
		return contacts;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	 
	   ViewHolder holder = null;
	   Log.d(JHConstants.LOG_TAG, String.valueOf(position));
	 
	   if (convertView == null) {
		   LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		   convertView = vi.inflate(R.layout.contactitem, null);
	 
		   holder = new ViewHolder();
		   //holder.code = (TextView) convertView.findViewById(R.id.code);
		   holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
		   convertView.setTag(holder);
	 
		   holder.name.setOnClickListener( new View.OnClickListener() {
			   public void onClick(View v) { 
				   CheckBox cb = (CheckBox) v ; 
				   Contact contact = (Contact) cb.getTag(); 
//				   Toast.makeText(context,"Clicked on Checkbox: " + cb.getText() +" is " + cb.isChecked(),Toast.LENGTH_LONG).show();
				   contact.setSelected(cb.isChecked());
			   } 
		   }); 
	   }
	   else {
		   holder = (ViewHolder) convertView.getTag();
	   }
	 
	   Contact contact = contacts.get(position);
	   //holder.code.setText(" (" +  contact.getId() + ")");
	   holder.name.setText(contact.getName());
	   holder.name.setChecked(contact.isSelected());
	   holder.name.setTag(contact);
	 
	   return convertView;
	 
	  }
	
}
