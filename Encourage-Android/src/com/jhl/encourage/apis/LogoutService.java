package com.jhl.encourage.apis;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface LogoutService {

	 @FormUrlEncoded
	    @POST("/hwservice/UserLogout.php")
	    void logoutUser(
	    			@Field("operationName") String operationName,  
	    			@Field("token") String token,
	    			@Field("datetime") String datetime, 
	    			@Field("timezone") String timezone,
	    			@Field("latitude") String latitude,
	        		@Field("longitude") String longitude,
	    			Callback<SpocResponse> callback);


}
