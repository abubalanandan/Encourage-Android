package com.jhl.encourage.apis;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;


public interface MarkCareTaskService {

    @FormUrlEncoded
    @POST("/hwservice/updateCareTaskStatus.php")
    void updateCareTask(
    			@Field("operationName") String operationName,  
    			@Field("caretaskid") String caretaskid, 
    			@Field("status") String status, 
    			@Field("token") String token, 
    			@Field("datetime") String datetime ,
    			@Field("timezone") String timezone ,
    			@Field("latitude") String latitude ,
    			@Field("longitude") String longitude ,
    			Callback<SpocResponse> callback);

}
