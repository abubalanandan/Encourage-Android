package com.jhl.encourage.views;

import java.util.Collections;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.widget.ListView;

import com.jhl.encourage.R;
import com.jhl.encourage.adapters.JHCareTasksAdapter;
import com.jhl.encourage.model.Notification;
import com.jhl.encourage.utilities.JHAppStateVariables;
import com.jhl.encourage.utilities.JHConstants;


public class JHCareTasksDialog extends Dialog {

	private JHCareTasksAdapter careTasksAdapter;

	private List<Notification> careTasks;
	public JHCareTasksDialog(Context context) {
		super(context, R.style.ThemeDialogCustom);
		
		setContentView(R.layout.caretasks);
		ListView listView = (ListView)findViewById(R.id.careTaksList);		
		careTasks = JHAppStateVariables.getUnreadNotifications(JHConstants.NOT_TYPE_CARE_TASK);		
		Collections.sort(this.careTasks);
		careTasksAdapter = new JHCareTasksAdapter(context, this, R.layout.caretask, careTasks);
		listView.setAdapter(careTasksAdapter);
	}
}
