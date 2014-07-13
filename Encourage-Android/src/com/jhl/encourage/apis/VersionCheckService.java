package com.jhl.encourage.apis;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;


public interface VersionCheckService {

    @FormUrlEncoded
    @POST("/hwservice/getAppLatestVersion.php")
    void checkVersion(
    			@Field("ostype") String ostype,    
    			Callback<SpocResponse> callback);

}
