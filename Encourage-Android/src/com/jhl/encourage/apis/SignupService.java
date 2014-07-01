package com.jhl.encourage.apis;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by nidhin on 28/5/14.
 */
public interface SignupService {
    @FormUrlEncoded
    @POST("/hwservice/postPersonDetails.php")
    void signUpUser(
    		@Field("operationName") String operationName, 
    		@Field("fname") String fname, 
    		@Field("lname") String lname, 
    		@Field("email_address") String emailAddress, 
    		
    		@Field("datetime") String datetime, 
			@Field("timezone") String timezone,
			@Field("latitude") String latitude,
    		@Field("longitude") String longitude,
    		
    		Callback<SpocResponse> callback);

}
