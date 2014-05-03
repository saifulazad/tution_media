package com.NASB.TuitionMedia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class PostData {
	
	static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";    
	
    public JSONObject postData(String url, List<String> key , List<String> Value,int l) {
		
		
		
		
		

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			
			
			for (int index =0 ; index <key.size() ; index++)
			{
				nameValuePairs.add(new BasicNameValuePair(key.get(index),Value.get(index)));
				//Log.d("KEY",key.get(index));
				//Log.d("VALUE",(Value.get(index)));
			}
		
			
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			
			
			HttpEntity httpEntity = response.getEntity();
			 is = httpEntity.getContent();
			
			

		} catch (ClientProtocolException e) {
			Log.d("OOOOO","00000000000000");
			// TODO Auto-generated catch block
		} catch (IOException e) {
			Log.d("OOOOO","00000000000000");
			// TODO Auto-generated catch block
		} 
		try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            Log.d("JSIN",json.toString());
            
        } catch (Exception e) {


        	
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
 
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);   	            
   
            
            
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jObj;
	}
    
	public JSONObject postData(String url, List<Map<String, String>> obj , List<String>Values) {
		
		
		
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			
			
			for (int index =0 ; index <obj.size() ; index++)
			{
				Map<String, String> PairValue =obj.get(index);
				nameValuePairs.add(new BasicNameValuePair(Values.get(index),PairValue.get(Values.get(index))));
				//Log.d("KEY",Values.get(index));
				//Log.d("VALUE",PairValue.get(Values.get(index)));
			}
		
			
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			
			
			HttpEntity httpEntity = response.getEntity();
			 is = httpEntity.getContent();
			
			

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		} 
		try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            Log.d("JSIN",json.toString());
            
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
 
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);   	            
   
            
            
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jObj;
	}
	
}
