package com.jhl.encourage.utilities;

import java.io.IOException;
import java.io.StringReader;


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
	
	
	public Notification parse(String xml){
		Notification notification = null;
		Document doc = getDomElement(xml);
		
		NodeList nl = doc.getElementsByTagName(JHConstants.NOT_XML_KEY_TAG);
		Log.d(JHConstants.LOG_TAG, "nl.getLength() "+nl.getLength());
		
        // looping through all item nodes <item>
        for (int i = 0; i < nl.getLength(); i++) {
        	
            Element e = (Element) nl.item(i);
            String type = getValue(e, JHConstants.NOT_XML_KEY_TYPE);
            
            Log.d(JHConstants.LOG_TAG, "type "+type);
            
            
            if (type.equals(JHConstants.NOT_TYPE_ALERT)){
            	notification = new Alert();
            	notification = setValues(notification, e);
            	
            	
            }else if (type.equals(JHConstants.NOT_TYPE_CARE_TASK)){
            	notification = new CareTask();
            	notification = setValues(notification, e);
            }
                       
        }
		
		return notification;
	}
	
	private Notification setValues(Notification n , Element e){
		n.setId(getValue(e, JHConstants.NOT_XML_KEY_ID));
		n.setMessage(getValue(e, JHConstants.NOT_XML_KEY_MESSAGE));
		return n;
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
