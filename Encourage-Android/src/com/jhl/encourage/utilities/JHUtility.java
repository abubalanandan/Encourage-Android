package com.jhl.encourage.utilities;



import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;


public class JHUtility {
	
	
	
	public static void showDialogOk(String title, String message, Activity activity){
		new AlertDialog.Builder(activity).setTitle(title).setMessage(message).setPositiveButton("Ok", null).setCancelable(false).create().show();
	}
	
	
	 public static void CopyStream(InputStream is, OutputStream os)
	    {
	        final int buffer_size=1024;
	        try
	        {
	        	
	            byte[] bytes=new byte[buffer_size];
	            for(;;)
	            {
	              //Read byte from input stream
	            	
	              int count=is.read(bytes, 0, buffer_size);
	              if(count==-1)
	                  break;
	              
	              //Write byte from output stream
	              os.write(bytes, 0, count);
	            }
	        }
	        catch(Exception ex){}
	    }

}
