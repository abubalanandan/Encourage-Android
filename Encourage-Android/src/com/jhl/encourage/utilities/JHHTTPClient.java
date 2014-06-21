package com.jhl.encourage.utilities;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;

import android.util.Log;

//import java.io.ByteArrayOutputStream;
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.http.client.HttpClient;
//import org.apache.http.impl.client.DefaultHttpClient;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Bitmap.CompressFormat;
//import android.util.Log;
//import org.apache.http.entity.mime.content.FileBody;

public class JHHTTPClient {
	
//	 public String uploadFile(String sourceFileUri, String uploadUrl, Map<String, String> parameters, String fileName) {
//		 try {
//			 	url = uploadUrl;
//				connectForMultipart();
//				
//				Set<String> keys = parameters.keySet();
//				Iterator<String> i =  keys.iterator();
//				while(i.hasNext()) {
//					String name = i.next();
//					String value = parameters.get(name);
//					writeParamData(name, value);
//				}
//				
//				Bitmap b = BitmapFactory.decodeFile(sourceFileUri);
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				b.compress(CompressFormat.PNG, 0, baos);
//				
//				addFilePart("file", fileName, baos.toByteArray());
//				finishMultipart();
//				String data = getResponse();
//				Log.d(JHConstants.LOG_TAG, "response "+data);
//				return data;
//			}
//			catch(Throwable t) {
//				t.printStackTrace();
//			}
//		 return null;
//     }
//	
//	
//	private String url;
//    private HttpURLConnection con;
//    private OutputStream os;
//    
//    private String delimiter = "--";
//    private String boundary =  "JH"+Long.toString(System.currentTimeMillis())+"JH";
//	
//	public void connectForMultipart() throws Exception {
//		con = (HttpURLConnection) ( new URL(url)).openConnection();
//		con.setRequestMethod("POST");
//		con.setDoInput(true);
//		con.setDoOutput(true);
//		con.setRequestProperty("Connection", "Keep-Alive");
//		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
//		con.connect();
//		os = con.getOutputStream();
//	}
//	
//	public void addFilePart(String paramName, String fileName, byte[] data) throws Exception {
//		os.write( (delimiter + boundary + "\r\n").getBytes());
//		os.write( ("Content-Disposition: form-data; name=\"" + paramName +  "\"; filename=\"" + fileName + "\"\r\n"  ).getBytes());
//		os.write( ("Content-Type: application/octet-stream\r\n"  ).getBytes());
//		os.write( ("Content-Transfer-Encoding: binary\r\n"  ).getBytes());
//		os.write("\r\n".getBytes());
//   
//		os.write(data);
//		
//		os.write("\r\n".getBytes());
//	}
//	
//	public void finishMultipart() throws Exception {
//		os.write( (delimiter + boundary + delimiter + "\r\n").getBytes());
//		os.flush();
//		os.close();
//	}
//	
//	public String getResponse() throws Exception {
//		InputStream is = con.getInputStream();
//		byte[] b1 = new byte[2048];
//		StringBuffer buffer = new StringBuffer();
//		
//		while ( is.read(b1) != -1)
//			buffer.append(new String(b1));
//		
//		con.disconnect();
//		
//		return buffer.toString();
//	}
//	
//	private void writeParamData(String paramName, String value) throws Exception {
//		
//		
//		os.write( (delimiter + boundary + "\r\n").getBytes());
//		os.write( "Content-Type: text/plain\r\n".getBytes());
//		os.write( ("Content-Disposition: form-data; name=\"" + paramName + "\"\r\n").getBytes());;
//		os.write( ("\r\n" + value + "\r\n").getBytes());
//			
//		
//	}
	
	
	public String uploadFile(String url, File file, Map<String, String> parameters) {
		
		String responseBody = "";
		HttpClient httpclient = new DefaultHttpClient();
		try {
			
		    FileBody bin = new FileBody(file);

		    MultipartEntity reqEntity = new MultipartEntity();
		    reqEntity.addPart("images", bin);
		    
			Set<String> keys = parameters.keySet();
			Iterator<String> i =  keys.iterator();
			while(i.hasNext()) {
				String name = i.next();
				String value = parameters.get(name);
				reqEntity.addPart(name, new StringBody(value));
			}
		    
		    
		    HttpPost httppost = new HttpPost(url);
		    httppost.setEntity(reqEntity);

		    Log.d(JHConstants.LOG_TAG, "Requesting : " + httppost.getRequestLine());
		    
		   
		    ResponseHandler<String> responseHandler = new BasicResponseHandler();
		    
		    Header[] headers =  httppost.getAllHeaders();
		    for(Header header : headers){
		    	 Log.d(JHConstants.LOG_TAG, header + " : " + httppost.getFirstHeader(header.getName()) );
		    }
		    
		    Log.d(JHConstants.LOG_TAG, "params " + httppost.getParams());
		    
		    HttpParams params = httppost.getParams();
		    
		   
		    
		    responseBody = httpclient.execute(httppost, responseHandler);
		    
		    

		    System.out.println("responseBody : " + responseBody);

		    } catch (UnsupportedEncodingException e) {
		      e.printStackTrace();
		    } catch (ClientProtocolException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    } finally {
		      httpclient.getConnectionManager().shutdown();
		    }
		return responseBody;
	}
	
	public String post(String url, Map<String, String> parameters) {
		String responseBody = "";
		HttpClient httpclient = new DefaultHttpClient();
		
		
	    HttpPost httppost = new HttpPost(url);
	    try {
	      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	      
	      Set<String> keys = parameters.keySet();
	      Iterator<String> i =  keys.iterator();
	      while(i.hasNext()) {
	    	  String name = i.next();
	    	  String value = parameters.get(name);
	    	  nameValuePairs.add(new BasicNameValuePair(name, value));
	      }
	      
	     
	      httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	 
	      ResponseHandler<String> responseHandler = new BasicResponseHandler();
		    
//		   Header[] headers =  httppost.getAllHeaders();
//		    for(Header header : headers){
//		    	 Log.d(JHConstants.LOG_TAG, header + " : " + httppost.getFirstHeader(header.getName()) );
//		    }
//		    
//		    Log.d(JHConstants.LOG_TAG, "params " + httppost.getParams());
//		    
//		    HttpParams params = httppost.getParams();
//		    
		   
		    
		    responseBody = httpclient.execute(httppost, responseHandler);

	    } catch (IOException e) {
	      e.printStackTrace();
	    }
		
		return responseBody;
		
	}
		
}

