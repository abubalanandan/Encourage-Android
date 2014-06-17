package com.jhl.encourage.apis;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;


public interface MarkAlertReadService {

    @FormUrlEncoded
    @POST("/hwservice/updateUnreadAlertStatus.php")
    void updateAlertRead(
    			@Field("operationName") String operationName,  
    			@Field("token") String token, 
    			@Field("alertkeys") String alertkeys, 
    			@Field("datetime") String datetime ,
    			@Field("timezone") String timezone ,
    			@Field("latitude") String latitude ,
    			@Field("longitude") String longitude ,
    			Callback<SpocResponse> callback);

}
