package com.jhl.encourage.apis;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by nidhin on 29/5/14.
 */
public interface LoginService {

    @FormUrlEncoded
    @POST("/hwservice/userLogin.php")
    void loginUser(
    			@Field("operationName") String operationName,  
    			@Field("email_address") String emailAddress, 
    			@Field("password") String password, 
    			@Field("gcmRegistrationId") String gcmRegistrationId ,
    			Callback<SpocResponse> callback);

}
