package com.jhl.encourage.apis;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;


public interface SickEmotionReportService {

    @FormUrlEncoded
    @POST("/hwservice/postReportWizardData.php")
    void postReport(
    		@Field("operationName") String operationName,
    		@Field("dtl_rwzevent_date[dtl_rwzevent_date]") String dtl_rwzevent_date,
    		@Field("dtl_rwzevent_date[data_type]") String data_type,
    		@Field("dtl_rwzevent_date[data_field_label]") String data_field_label,
    		@Field("dtl_rwzevent_date[data_type_code]") String data_type_code,
    		@Field("dtl_rwzevent_date[sequence]") String sequence,
    		@Field("dtl_rwzevent_date[record_uuid]") String record_uuid,
    		@Field("dtl_rwzevent_data[dtl_rwzevent_data]") String dtl_rwzevent_data,
    		@Field("dtl_rwzevent_data[data_type]") String data_type2,
    		@Field("dtl_rwzevent_data[data_field_label]") String data_field_label2,
    		@Field("dtl_rwzevent_data[data_type_code]") String data_type_code2,
    		@Field("dtl_rwzevent_data[sequence]") String sequence2,
    		@Field("dtl_rwzevent_data[record_uuid]") String record_uuid2,
    		@Field("text_rwzevent_description[text_rwzevent_description]") String text_rwzevent_description,
    		@Field("text_rwzevent_description[data_type]") String data_type3,
    		@Field("text_rwzevent_description[data_field_label]") String data_field_label3,
    		@Field("text_rwzevent_description[data_type_code]") String data_type_code3,
    		@Field("text_rwzevent_description[sequence]") String sequence3,
    		@Field("text_rwzevent_description[record_uuid]") String record_uuid3,
    		@Field("form") String form,
    		@Field("event_type") String event_type,
    		@Field("inform_carecircle") String inform_carecircle,
    		@Field("datetime") String datetime,
    		@Field("timezone") String timezone,
    		@Field("token") String token,
    		@Field("doaction") String doaction,
    		
    		@Field("nimyc_persons") String nimyc_persons,
    		@Field("nimyc_mails") String nimyc_mails, 
    		@Field("add_to_myccs") String add_to_myccs,
			
			@Field("latitude") String latitude,
    		@Field("longitude") String longitude,
			
    		Callback<SpocResponse> callback);

}
