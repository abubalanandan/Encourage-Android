package com.jhl.encourage.activities;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.jhl.encourage.EncourageApplication;
import com.jhl.encourage.R;
import com.jhl.encourage.adapters.JHTimelineAdapter;
import com.jhl.encourage.apis.SpocObject;
import com.jhl.encourage.apis.SpocResponse;
import com.jhl.encourage.apis.TimeLineService;

public class JHTimelineActivity extends Activity {

	private ListView timelineView;
	private JHTimelineAdapter adapter;
	private ArrayList<String> list = new ArrayList<>(3);

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline);
		for (int i = 0; i < 9; i++) {
			list.add("lala");
		}
		initViews();
	}

	private void initViews() {
		timelineView = (ListView) findViewById(R.id.timeLineView);

		ProgressBar dialog = new ProgressBar(this);
		dialog.setProgressDrawable(getResources().getDrawable(
				android.R.drawable.progress_indeterminate_horizontal));
		timelineView.addFooterView(dialog);
		adapter = new JHTimelineAdapter(this, list, false);
		timelineView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	private void invokeTimelineDetailsApi(String careTargetId, String dateTime,
			String timeZone) {

		TimeLineService service = EncourageApplication.getRestAdapter().create(
				TimeLineService.class);
		String token = null;

		service.getTimeLineDetails("getTimelineDetails", token, careTargetId,
				dateTime, timeZone, new Callback<SpocResponse>() {
					@Override
					public void success(SpocResponse spocResponse,
							Response response) {

						ArrayList<SpocObject> responseList = spocResponse
								.getSpocObjects();
						for (SpocObject spocObject : responseList) {
							if (spocObject.getResultTypeCode()
									.equalsIgnoreCase("STATUS")) {
								HashMap<String, String> map = spocObject
										.getMap();
								String success = map.get("success");
								if (success.equalsIgnoreCase("true")) {
									System.out.println("success");

								} else {
									System.out.println("error");

								}

							}
						}

					}

					@Override
					public void failure(RetrofitError retrofitError) {

						System.out.println("Retro error");

					}
				});

	}
}
