package com.jhl.encourage.adapters;

import java.util.ArrayList;
import java.util.List;

import com.jhl.encourage.R;
import com.jhl.encourage.model.Contact;

import com.jhl.encourage.utilities.JHAppStateVariables;
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
		TextView nameTV;
		TextView emailTV;
		CheckBox checkbox;
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
		   holder.checkbox = (CheckBox) convertView.findViewById(R.id.cboxCareCircle);
		   holder.nameTV = (TextView)convertView.findViewById(R.id.contactNameTV);
		   holder.emailTV = (TextView)convertView.findViewById(R.id.contactEmailTV);
		   convertView.setTag(holder);
	 
		   holder.checkbox.setOnClickListener( new View.OnClickListener() {
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
	   holder.nameTV.setText(contact.getName());
	   holder.emailTV.setText(contact.getEmail());
	   
	   boolean checked = ((JHAppStateVariables.getSelectedContacts() != null && JHAppStateVariables.getSelectedContacts().contains(contact)) || contact.isSelected());
	   
	   holder.checkbox.setChecked(checked);
	   holder.checkbox.setTag(contact);
	 
	   return convertView;
	 
	  }
	
}
