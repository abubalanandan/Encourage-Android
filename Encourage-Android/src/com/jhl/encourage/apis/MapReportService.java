package com.jhl.encourage.apis;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;


public interface MapReportService {

    @FormUrlEncoded
    @POST("/hwservice/postReportWizardData.php")
    void postReport(
    		@Field("operationName") String operationName,
    		@Field("dtl_rwzmap_date[dtl_rwzmap_date]") String dtl_rwzmap_date,
    		@Field("dtl_rwzmap_date[data_type]") String data_type,
    		@Field("dtl_rwzmap_date[data_field_label]") String data_field_label,
    		@Field("dtl_rwzmap_date[data_type_code]") String data_type_code,
    		@Field("dtl_rwzmap_date[sequence]") String sequence,
    		@Field("dtl_rwzmap_date[record_uuid]") String record_uuid,
    		@Field("dtl_mapevent_name[dtl_mapevent_name]") String dtl_mapevent_name,
    		@Field("dtl_mapevent_name[data_type]") String data_type2,
    		@Field("dtl_mapevent_name[data_field_label]") String data_field_label2,
    		@Field("dtl_mapevent_name[data_type_code]") String data_type_code2,
    		@Field("dtl_mapevent_name[sequence]") String sequence2,
    		@Field("dtl_mapevent_name[record_uuid]") String record_uuid2,
    		@Field("txt_mapevent_address[txt_mapevent_address]") String txt_mapevent_address, 
    		@Field("txt_mapevent_address[data_type]") String data_type3,
    		@Field("txt_mapevent_address[data_field_label]") String data_field_label3,
    		@Field("txt_mapevent_address[data_type_code]") String data_type_code3,
    		@Field("txt_mapevent_address[sequence]") String sequence3,
    		@Field("txt_mapevent_address[record_uuid]") String record_uuid3,
    		@Field("txt_mapevent_desc[txt_mapevent_desc]") String txt_mapevent_desc,
    		@Field("txt_mapevent_desc[data_type]") String data_type4,
    		@Field("txt_mapevent_desc[data_field_label]") String data_field_label4,
    		@Field("txt_mapevent_desc[data_type_code]") String data_type_code4,
    		@Field("txt_mapevent_desc[sequence]") String sequence4, 
    		@Field("txt_mapevent_desc[record_uuid]") String record_uuid4,
    		@Field("nimyc_person1") String nimyc_person1,
    		@Field("nimyc_mail1") String nimyc_mail1,
    		@Field("nimyc_person2") String nimyc_person2,
    		@Field("nimyc_mail2") String nimyc_mail2,
    		@Field("form") String form,
    		@Field("event_type") String event_type,
    		@Field("inform_carecircle") String inform_carecircle,
    		@Field("datetime") String datetime,
    		@Field("timezone") String timezone,
    		@Field("token") String token,
    		@Field("doaction") String doaction,
    		@Field("add_to_mycc1") String add_to_mycc1,
    		@Field("add_to_mycc2") String add_to_mycc2,
    		Callback<SpocResponse> callback);

}
