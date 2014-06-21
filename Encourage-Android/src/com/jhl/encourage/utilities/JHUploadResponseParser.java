package com.jhl.encourage.utilities;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jhl.encourage.model.JHUploadResponse;


public class JHUploadResponseParser {
	
	public static JHUploadResponse parse(String str){
		JHUploadResponse response = new JHUploadResponse();
		
		JsonParser parser = new JsonParser();
		JsonObject obj = (JsonObject)parser.parse(str);
		JsonElement status = obj.get("status");
		JsonElement fileName = obj.get("blob_upload_file");
		String jsnString = fileName.toString();
		obj = (JsonObject)parser.parse(jsnString);
		
		fileName = obj.get("blob_upload_file");
		
		response.setStatus(status.toString());
		response.setFileName(fileName.toString());
		
		return response;
	}
	
	public static JHUploadResponse imageReportStatusParse(String str){
		JHUploadResponse response = new JHUploadResponse();
		
		JsonParser parser = new JsonParser();
		JsonObject obj = (JsonObject)parser.parse(str);
		JsonElement status = obj.get("status");
		
		response.setStatus(status.toString());
		
		return response;
	}

	public static void main(String[] args) {
		
		
	}
//	
//	public Map<String, String> parse(String response){
//		
//		Document doc = getDomElement(response);
//		
//
//		
//		NodeList nl = doc.getElementsByTagName(JHConstants.NOT_XML_TAG_RESPONSE);
//		Log.d(JHConstants.LOG_TAG, "nl.getLength() "+nl.getLength());
//		// looping through all item nodes 
//		
//		Map<String, String> entryMap = new HashMap<String, String>();
//		
//		for (int i = 0; i < nl.getLength(); i++) {
//			Element e = (Element) nl.item(i);
//			NodeList entries = e.getElementsByTagName(JHConstants.NOT_XML_TAG_ENTRY);
//			
//			for (int j = 0; j < entries.getLength(); j++) {
//				Element e1 = (Element) entries.item(j);
//				Log.d(JHConstants.LOG_TAG, "e1.getAttribute(JHConstants.NOT_XML_TAG_ATRIB_KEY) "+e1.getAttribute(JHConstants.NOT_XML_TAG_ATRIB_KEY));
//				Log.d(JHConstants.LOG_TAG, "e1.getTextContent() "+e1.getTextContent());
//				entryMap.put(e1.getAttribute(JHConstants.NOT_XML_TAG_ATRIB_KEY), e1.getTextContent());
//			}
//		}
//		
//		
//		
//		return entryMap;
//	}
//	
//	
//	private Document getDomElement(String xml){
//		Document doc = null;
//		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//		try {
//
//			DocumentBuilder db = dbf.newDocumentBuilder();
//
//			InputSource is = new InputSource();
//		        is.setCharacterStream(new StringReader(xml));
//		        doc = db.parse(is); 
//
//			} catch (ParserConfigurationException e) {
//				Log.e(JHConstants.LOG_TAG, e.getMessage());
//				return null;
//			} catch (SAXException e) {
//				Log.e(JHConstants.LOG_TAG , e.getMessage());
//	            return null;
//			} catch (IOException e) {
//				Log.e(JHConstants.LOG_TAG, e.getMessage());
//				return null;
//			}
//
//	        return doc;
//	}
//	
//	/**
//	  * Getting node value
//	  * @param Element node
//	  * @param key string
//	  * */
//	 private String getValue(Element item, String str) {		
//			NodeList n = item.getElementsByTagName(str);		
//			return this.getElementValue(n.item(0));
//	}
//	 
//	 /** Getting node value
//	  * @param elem element
//	  */
//	 private final String getElementValue( Node elem ) {
//	     Node child;
//	     if( elem != null){
//	         if (elem.hasChildNodes()){
//	             for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
//	                 if( child.getNodeType() == Node.TEXT_NODE  ){
//	                     return child.getNodeValue();
//	                 }
//	             }
//	         }
//	     }
//	     return "";
//	 }
}
