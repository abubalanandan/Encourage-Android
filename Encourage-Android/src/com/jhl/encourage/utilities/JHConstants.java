package com.jhl.encourage.utilities;


public interface JHConstants {
	public static final String LOG_TAG = "EncourageLog";
	
	public static final String NOT_TYPE_ALERT = "alert";
	public static final String NOT_TYPE_CARE_TASK = "caretask";
	
	
	public static final String NOT_XML_KEY_ID = "id";
	public static final String NOT_XML_KEY_TYPE = "notification_type";
	public static final String NOT_XML_KEY_ALERT_KEY = "alertkey";
	public static final String NOT_XML_KEY_DATE_TIME = "datetime";
	public static final String NOT_XML_KEY_DATE_TIME_DIFF = "datetimediff";
	public static final String NOT_XML_KEY_CONTENT_TYPE = "contentype";
	public static final String NOT_XML_KEY_READ_STATUS = "read_status";
	public static final String NOT_XML_KEY_READ_STATUS_READ = "read";
	public static final String NOT_XML_KEY_READ_STATUS_UNREAD = "unread";
	public static final String NOT_XML_KEY_AUTHOR_NAME = "authorname";
	public static final String NOT_XML_KEY_TITLE = "title";
	public static final String NOT_XML_KEY_DETAILS = "details";
	public static final String NOT_XML_KEY_CARETASK_KEY = "caretaskid";
	public static final String NOT_XML_KEY_CARETASK_TYPE = "caretask_type";
	public static final String NOT_XML_KEY_CARETASK_DATE_TIME = "caretask_datetime";
	public static final String NOT_XML_KEY_PRPVIDER_NAE = "provider_name";
	public static final String NOT_XML_KEY_CAREPLAN_NAME = "careplan_name";
	public static final String NOT_XML_KEY_CP_DETAILS = "cp_details";
	public static final String NOT_XML_KEY_ALERT_TYPE = "contentype";
	public static final String NOT_XML_KEY_ALERT_TYPE_LINK = "Link";
	public static final String NOT_XML_KEY_ALERT_TYPE_NOTE = "Note";
	public static final String NOT_XML_KEY_ALERT_TYPE_IMAGE = "Image";
	public static final String NOT_XML_KEY_ALERT_TYPE_MAP = "Map";
	
	public static final String NOT_XML_KEY_ALERT_IMAGE_KEY = "documentactualname";
	public static final String NOT_XML_KEY_ALERT_MAP_KEY = "eventaddress";
	public static final String NOT_XML_KEY_ALERT_LINK_KEY = "url";
	
				
	public static final String NOT_XML_KEY_MESSAGE = "message";
	public static final String NOT_XML_TAG_RESPONSE = "SPOCResponse";
	public static final String NOT_XML_TAG_OBJECT = "SpocObject";
	public static final String NOT_XML_TAG_MAP = "map";
	public static final String NOT_XML_TAG_ENTRY = "entry";
	public static final String NOT_XML_TAG_ATRIB_KEY = "key";
	
	public static final String NOT_STATUS_UNREAD = "unread";
	public static final String NOT_STATUS_READ = "read";
	
	public static final int SICKNESS_sore_throat = 1;
	public static final String SICKNESS_TEXT_sore_throat = "Sore Throat";
	public static final int SICKNESS_tired = 2;
	public static final String SICKNESS_TEXT_tired = "Tired";
	public static final int SICKNESS_back_pain = 3; 
	public static final String SICKNESS_TEXT_back_pain = "Back Pain";
	public static final int SICKNESS_dizziness = 4;
	public static final String SICKNESS_TEXT_dizziness = "Dizziness";
	public static final int SICKNESS_cant_sleep = 5;
	public static final String SICKNESS_TEXT_cant_sleep = "Can't sleep";
	public static final int SICKNESS_joint_pain = 6;
	public static final String SICKNESS_TEXT_joint_pain = "Joint pain";
	public static final int SICKNESS_dry_skin = 7;
	public static final String SICKNESS_TEXT_dry_skin = "Dry Skin";
	public static final int SICKNESS_nosebleed = 8;
	public static final String SICKNESS_TEXT_nosebleed = "Nosebleed";
	public static final int SICKNESS_shortness_of_breath = 9;
	public static final String SICKNESS_TEXT_shortness_of_breath = "Shortness of Breath";
	public static final int SICKNESS_breathless = 10; 
	public static final String SICKNESS_TEXT_breathless = "Breathless";
	public static final int SICKNESS_tingling_sensation = 11;
	public static final String SICKNESS_TEXT_tingling_sensation = "Tingling sensation";
	public static final int SICKNESS_other = 12;
	public static final String SICKNESS_TEXT_other = "Other";
	
	
	public static final int EMO_worried = 21;
	public static final String EMO_TEXT_worried = "Worried";
	public static final int EMO_Anxious = 22;
	public static final String EMO_TEXT_Anxious = "Anxious";
	public static final int EMO_Depressed = 23; 
	public static final String EMO_TEXT_Depressed = "Depressed";
	public static final int EMO_Angry = 24;
	public static final String EMO_TEXT_Angry = "Angry";
	public static final int EMO_Sad = 25;
	public static final String EMO_TEXT_Sad = "Sad";
	public static final int EMO_Happy = 26;
	public static final String EMO_TEXT_Happy = "Happy";
	public static final int EMO_Restless = 27;
	public static final String EMO_TEXT_Restless = "Restless";
	public static final int EMO_cant_sleep = 28;
	public static final String EMO_TEXT_cant_sleep = "Can't sleep";

	
	
	public static final int BUTTON_SELECTED = 1;
	public static final int BUTTON_UNSELECTED = 0;
	
	public static final int REQUEST_CODE_CONTACTS = 1;
	public static final int REQUEST_CODE_IMAGE_LIB = 2;
	public static final int REQUEST_CODE_VIDEO_LIB = 3;
	
	public static final int RW_PAGER_FRAG_SICK = 0;
	public static final int RW_PAGER_FRAG_EMO = 1;
	public static final int RW_PAGER_FRAG_IMAGE = 2;
	public static final int RW_PAGER_FRAG_MAP = 3;
	public static final int RW_PAGER_FRAG_VIDEO = 4;

}
