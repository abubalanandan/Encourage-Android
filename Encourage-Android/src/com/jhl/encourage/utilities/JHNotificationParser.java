package com.jhl.encourage.utilities;

import java.io.IOException;
import java.io.StringReader;


import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;










import com.jhl.encourage.model.Alert;
import com.jhl.encourage.model.CareTask;
import com.jhl.encourage.model.Notification;

import android.util.Log;

public class JHNotificationParser {

	public static void main(String[] args) {
		
		
	}
	
	public Notification parse(String xml){
		Alert alert = null;
		CareTask careTask = null ; 
		Document doc = getDomElement(xml);
		
		
		NodeList nl = doc.getElementsByTagName(JHConstants.NOT_XML_TAG_RESPONSE);
		Log.d(JHConstants.LOG_TAG, "nl.getLength() "+nl.getLength());
		// looping through all item nodes 
		for (int i = 0; i < nl.getLength(); i++) {
			Element e = (Element) nl.item(i);
			NodeList entries = e.getElementsByTagName(JHConstants.NOT_XML_TAG_ENTRY);
			Map<String, String> entryMap = new HashMap<String, String>();
			for (int j = 0; j < entries.getLength(); j++) {
				Element e1 = (Element) entries.item(j);
				Log.d(JHConstants.LOG_TAG, "e1.getAttribute(JHConstants.NOT_XML_TAG_ATRIB_KEY) "+e1.getAttribute(JHConstants.NOT_XML_TAG_ATRIB_KEY));
				Log.d(JHConstants.LOG_TAG, "e1.getTextContent() "+e1.getTextContent());
				entryMap.put(e1.getAttribute(JHConstants.NOT_XML_TAG_ATRIB_KEY), e1.getTextContent());
			}
			
			String type = entryMap.get(JHConstants.NOT_XML_KEY_TYPE);
			if(type.equals(JHConstants.NOT_TYPE_ALERT)){
				alert = new Alert();
				alert.setId(entryMap.get(JHConstants.NOT_XML_KEY_ALERT_KEY));
				alert.setNotificationType(entryMap.get(JHConstants.NOT_XML_KEY_TYPE));
				alert.setAuthorName(entryMap.get(JHConstants.NOT_XML_KEY_AUTHOR_NAME));
				alert.setContenType(entryMap.get(JHConstants.NOT_XML_KEY_CONTENT_TYPE));
				alert.setDateTime(entryMap.get(JHConstants.NOT_XML_KEY_DATE_TIME));
				alert.setDateTimeDiff(entryMap.get(JHConstants.NOT_XML_KEY_DATE_TIME_DIFF));
				alert.setDetails(entryMap.get(JHConstants.NOT_XML_KEY_DETAILS));
				alert.setNotificationType(entryMap.get(JHConstants.NOT_XML_KEY_TYPE));
				alert.setReadStatus(entryMap.get(JHConstants.NOT_XML_KEY_READ_STATUS));
				alert.setTitle(entryMap.get(JHConstants.NOT_XML_KEY_TITLE));
				
				Log.d(JHConstants.LOG_TAG, alert.toString());
				
				return (Notification)alert;
				
			}else if (type.equals(JHConstants.NOT_TYPE_CARE_TASK)) {
				
				careTask = new CareTask();
				careTask.setId(entryMap.get(JHConstants.NOT_XML_KEY_CARETASK_KEY));
				careTask.setNotificationType(entryMap.get(JHConstants.NOT_XML_KEY_TYPE));
				careTask.setCareTaskType(entryMap.get(JHConstants.NOT_XML_KEY_CARETASK_TYPE));
				careTask.setDateTime(entryMap.get(JHConstants.NOT_XML_KEY_CARETASK_DATE_TIME));
				careTask.setProviderName(entryMap.get(JHConstants.NOT_XML_KEY_PRPVIDER_NAE));
				careTask.setCareplanName(entryMap.get(JHConstants.NOT_XML_KEY_CAREPLAN_NAME));
				careTask.setCpDetails(entryMap.get(JHConstants.NOT_XML_KEY_CP_DETAILS));
				
				return (Notification)careTask;
			}
		}
		
		return null;
	}
	
	
	
	private Document getDomElement(String xml){
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource is = new InputSource();
		        is.setCharacterStream(new StringReader(xml));
		        doc = db.parse(is); 

			} catch (ParserConfigurationException e) {
				Log.e(JHConstants.LOG_TAG, e.getMessage());
				return null;
			} catch (SAXException e) {
				Log.e(JHConstants.LOG_TAG , e.getMessage());
	            return null;
			} catch (IOException e) {
				Log.e(JHConstants.LOG_TAG, e.getMessage());
				return null;
			}

	        return doc;
	}
	
	/**
	  * Getting node value
	  * @param Element node
	  * @param key string
	  * */
	 private String getValue(Element item, String str) {		
			NodeList n = item.getElementsByTagName(str);		
			return this.getElementValue(n.item(0));
	}
	 
	 /** Getting node value
	  * @param elem element
	  */
	 private final String getElementValue( Node elem ) {
	     Node child;
	     if( elem != null){
	         if (elem.hasChildNodes()){
	             for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
	                 if( child.getNodeType() == Node.TEXT_NODE  ){
	                     return child.getNodeValue();
	                 }
	             }
	         }
	     }
	     return "";
	 }
}
