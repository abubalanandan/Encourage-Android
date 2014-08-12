package com.jhl.encourage.apis;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;


public interface AlertsService {

    @FormUrlEncoded
    @POST("/hwservice/getUserAlertsDetails.php")
    void getAlertDetails(
    			@Field("operationName") String operationName,  
    			@Field("token") String emailAddress, 
    			@Field("datetime") String datetime, 
    			@Field("timezone") String timezone,
    			@Field("latitude") String latitude,
    			@Field("longitude") String longitude,
    			@Field("start") String start,
    			Callback<SpocResponse> callback);

}
