package com.NASB.TuitionMedia;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Tutor_Addmap extends FragmentActivity {
	SupportMapFragment map;
	private GoogleMap googlemap;
    String Ph = "";
    String url = "http://nasb.herobo.com/tutor_add.php";
    private int i =0; 
	static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    String[] a = new String[8];
    private Marker marker;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addmap);
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

		//Ph =getIntent().getExtras().getString("ALL");
		
		//a =all.Add_Split(Ph);
		
		
		//Log.d("BAL", all.getall().toString());
		//Bundle data = getIntent().getExtras();
	//	Utility all = data.getParcelable("ALL");
		TutorS nt=(TutorS) getIntent().getSerializableExtra("All");
		
		a[0] = nt.t.getPh();
		a[3] = nt.t.getMin();
		a[4] = nt.t.getMax();
		a[2] = nt.t.getInst();
		a[1] = nt.getSub();
		a[5]=  nt.t.getState();
		//val = all.getall();
	//	Toast.makeText(getBaseContext(),Ph , Toast.LENGTH_LONG).show();
		//Ph =getIntent().getExtras().getString("phno");
		if(googlemap == null)
		{
			googlemap= ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		//	googlemap.onResume();
			CameraUpdate center=
			        CameraUpdateFactory.newLatLng(new LatLng(23.684994000000000000,
			        		90.356330999999950000));
			    CameraUpdate zoom=CameraUpdateFactory.zoomTo(10);

			    googlemap.moveCamera(center);
			    googlemap.animateCamera(zoom);
			    
			googlemap.clear();
			
			
		}
		
	
	//	MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lng)).title("Hello Maps ");
		//googlemap.addMarker(marker);
		
		
		googlemap.setOnMapLongClickListener(new OnMapLongClickListener() {				
			@Override
			public void onMapLongClick(final LatLng point) {
			
				
				googlemap.clear();
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.
						Builder(Tutor_Addmap.this);				
				alertDialog.setTitle("Confirmation..");
				
				alertDialogBuilder.setMessage("Are You Sure To give this Add in this location?")
						.setCancelable(false)
						.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								double lat = point.latitude;
								double lng  = point.longitude;
								new MyAsyncTask().execute(a[0],Double.toString(lat),
										Double.toString(lng),a[1],a[2], a[3], a[4],a[5]);
								marker=googlemap.addMarker(new MarkerOptions().position(point));
							}
						})
						.setNegativeButton("No", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
								dialog.cancel();
							//	marker.remove();
							}
						});
				
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();												
			}
		});
		
	}
	private class MyAsyncTask extends AsyncTask<String, Integer, Double>{
		 
		@Override
		
		protected Double doInBackground(String... params) {
			// TODO Auto-generated method stub
			postData(params[0], params[1], params[2],params[3], params[4], params[5], params[6],params[7]);
			return null;
		}
 
		protected void onPostExecute(Double result){
			if(i == 1)
			{
				Toast.makeText(Tutor_Addmap.this, "Add is published Successfully", Toast.LENGTH_LONG)
				.show();
				
			}
			else
				Toast.makeText(getApplicationContext(), "Add is not published", Toast.LENGTH_LONG).show();
		
		}
		protected void onProgressUpdate(Integer... progress){
			//pb.setProgress(progress[0]);
		}
 
		public void postData(String ph, String lat , String lng ,
				String subs, String salmax , String salmin , String inst, String state) {
			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
 
			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("ph", ph));
				nameValuePairs.add(new BasicNameValuePair("lat", lat));
				nameValuePairs.add(new BasicNameValuePair("lng", lng));
				nameValuePairs.add(new BasicNameValuePair("subs", subs));
				nameValuePairs.add(new BasicNameValuePair("salmin", salmin));
				nameValuePairs.add(new BasicNameValuePair("salmax", salmax));
				nameValuePairs.add(new BasicNameValuePair("inst", inst));
				nameValuePairs.add(new BasicNameValuePair("state", state));
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
	                sb.append(line + "n");
	            }
	            is.close();
	            json = sb.toString();
	            
	        } catch (Exception e) {
	            Log.e("Buffer Error", "Error converting result " + e.toString());
	        }
	 
	        // try parse the string to a JSON object
	        try {
	            jObj = new JSONObject(json);   
	            
	            String St = jObj.toString();
	            if(St.contains("Incorrect"))
	            {
	            	//onPostExecute("Incorrect");
	            	i=0;
	            	Log.e("JSON", "Incorrect");
	            }
	            else
	            {
	            	Log.e("JSON", "Correct");
	            	i =1;
	            }
	            
	            
	        } catch (JSONException e) {
	            Log.e("JSON Parser", "Error parsing data " + e.toString());
	        }
	 
		}
 
	}
}