package com.jhl.encourage.apis;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;


public interface ImageReportService {

    @FormUrlEncoded
    @POST("/hwservice/postReportWizardData.php")
    void postReport(
    		@Field("operationName") String operationName,
    		@Field("dtl_rwzimge_date[dtl_rwzimge_date]") String dtl_rwzimge_date,
    		@Field("dtl_rwzimge_date[data_type]") String data_type,
    		@Field("dtl_rwzimge_date[data_field_label]") String data_field_label,
    		@Field("dtl_rwzimge_date[data_type_code]") String data_type_code,
    		@Field("dtl_rwzimge_date[sequence]") String sequence,
    		@Field("dtl_rwzimge_date[record_uuid]") String record_uuid,
    		@Field("dtl_rwzimge_name[dtl_rwzimge_name]") String dtl_rwzimge_name,
    		@Field("dtl_rwzimge_name[data_type]") String data_type2,
    		@Field("dtl_rwzimge_name[data_field_label]") String data_field_label2,
    		@Field("dtl_rwzimge_name[data_type_code]") String data_type_code2,
    		@Field("dtl_rwzimge_name[sequence]") String sequence2,
    		@Field("dtl_rwzimge_name[record_uuid]") String record_uuid2,
    		@Field("form") String form,
    		@Field("event_type") String event_type,
    		@Field("inputfile_field") String inputfile_field,
    		@Field("inform_carecircle") String inform_carecircle,
    		@Field("datetime") String datetime,
    		@Field("timezone") String timezone,
    		@Field("token") String token,
    		@Field("doaction") String doaction,
    		@Field("blob_upload_file") String blob_upload_file,
    		Callback<SpocResponse> callback);

}
