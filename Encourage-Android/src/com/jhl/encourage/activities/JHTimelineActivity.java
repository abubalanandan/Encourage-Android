package com.jhl.encourage.activities;

import android.app.Activity;
import android.os.Bundle;

import com.jhl.encourage.EncourageApplication;
import com.jhl.encourage.R;
import com.jhl.encourage.apis.SpocObject;
import com.jhl.encourage.apis.SpocResponse;
import com.jhl.encourage.apis.TimeLineService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.HashMap;

public class JHTimelineActivity extends Activity{

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline);
	}


    private void invokeTimelineDetailsApi(String careTargetId, String dateTime, String timeZone){

        TimeLineService service = EncourageApplication.getRestAdapter().create(TimeLineService.class);
        String token = null;

        service.getTimeLineDetails("getTimelineDetails",token,careTargetId,dateTime,timeZone, new Callback<SpocResponse>() {
            @Override
            public void success(SpocResponse spocResponse, Response response) {

                ArrayList<SpocObject> responseList = spocResponse
                        .getSpocObjects();
                for (SpocObject spocObject : responseList) {
                    if (spocObject.getResultTypeCode()
                            .equalsIgnoreCase("STATUS")) {
                        HashMap<String, String> map = spocObject
                                .getMap();
                        String success = map.get("success");
                        if(success.equalsIgnoreCase("true")){
                            System.out.println("success");

                        }else{
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
