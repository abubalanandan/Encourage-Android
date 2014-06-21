package com.jhl.encourage.apis;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by nidhin on 30/5/14.
 */
public interface TimeLineService {
    @FormUrlEncoded
    @POST("/hwservice/getTimelineDetails.php")
	void getTimeLineDetails(
			@Field("operationName") String operationName,
			@Field("token") String token,
			@Field("careTargetid") String careTargetId,
			@Field("datetime") String dateTime, 
			@Field("timezone") String timeZone, 
			@Field("lastcount") int lastCount,
			Callback<SpocResponse> callback);
}

