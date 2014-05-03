package com.NASB.TuitionMedia;


import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;


/**
 * This Class process all information displaying 
 * in google map as marker . pressing each marker 
 * we get tutor info
 * 
 * @author      Saiful Islam Azad
 * @author      Sadi mohammad zaman
 */



public class GMap extends FragmentActivity {
	
	
	//URL to get JSON Array
	private static String tutor_add_all_info = "http://nasb.herobo.com/tutor_data.php";
		
	
	SupportMapFragment map;
	private GoogleMap googlemap;
	Editor editot;
	Context context;
	
	
	ArrayList<String> gurdian_info_key = new ArrayList<String>();
	ArrayList<String> gurdian_info_value = new ArrayList<String>();
	
	
	//JSON Node Names 
	private static final String json_head_tutor = "TutorAdd_info";

	String gurdianphone ;  // hold gurdian phone number as it will helpful for query 
	// at serversite 

	JSONArray tutor_info = null; // hold basic info about 
	// teacher as latitude longitude name phone no
	
	
	// hash map string and Tutor object 
	// String for title and object assositated 
	private HashMap<String, Tutor> markers= new HashMap<String, Tutor>();

	//key ,value pair marker assigned for a tutor location
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        setContentView(R.layout.map);
        Intent intent=getIntent();
		gurdianphone = intent.getExtras().getString("phn");
		
		// value pair for Http post
		
		gurdian_info_key.add("phone");
		
		gurdian_info_value.add(gurdianphone);
		
        googlemap= ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        new JSONParse().execute();
   
		 if (getIntent().getBooleanExtra("EXIT", false)) {
			    finish();  
			}
 
		 googlemap.setInfoWindowAdapter(new PopupAdapter(getLayoutInflater()));  
    
    }
	
    private class JSONParse extends AsyncTask<String, String, JSONObject> {
    	 private ProgressDialog pDialog;
    	@Override
        protected void onPreExecute() {
            super.onPreExecute();
            
            pDialog = new ProgressDialog(GMap.this);
            pDialog.setMessage("Getting Data ...");
          
            pDialog.setCancelable(true);
            pDialog.show();
    	}
    	
    	@Override
        protected JSONObject doInBackground(String... args) {
    		
    		PostData postVal = new PostData();
    		// Getting JSON from URL
       		JSONObject json = postVal.postData(tutor_add_all_info, 
       				gurdian_info_key, gurdian_info_value , 1);
       		return json;
    	}
    	 @Override
         protected void onPostExecute(JSONObject json) {
    		 pDialog.dismiss();
    		 try {
 				// Getting JSON Array from URL
 				tutor_info = json.getJSONArray(json_head_tutor);
 				//JSON sent 1st element as total number of tutor add  
 				// 
 				final JSONObject p = tutor_info.getJSONObject(0); // 1st element 
 				String count = p.getString("count");  // catch it
 				
	    		int len  = Integer.parseInt(count); // parse it 
	    		int i;
	    		// loop for processing those 
 				for(i = 1; i <= len; i++) 
				{
 						// catch it
	    				final JSONObject c = tutor_info.getJSONObject(i);
	    				
	    				// Oh google makes it easy
	    				
	    				Gson gson=new Gson(); 
	    				String Json=c.toString();  // convert as String
 	    			
	    				Tutor ob; // new object

	    				// parse it as tutor object
	    				ob = (Tutor) gson.fromJson(Json, Tutor.class);
	    				
	    				// function call for printing maker on map 
	    				
	    				addMarker(googlemap,ob.getlatitude(),ob.getlongitite(),
	    						"NAME : "+ob.getName(),ob);
	    				  			  		        				   				
				}
 				 // this methode works when a click will occurred in a map marker 
				 googlemap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
				        @Override
				        public void onInfoWindowClick(Marker marker) {
				           
				        	// catch a marker info (tutor object)
				        	Tutor tutor_info = (Tutor) markers.get(marker.getId());
				        	// call intent
				        	Intent intent = new Intent(GMap.this,Otheractivity.class);
				        	// pass to OtherActivity as onject
				        	intent.putExtra("tutor_info", tutor_info);
				        	// call activity
				        	startActivity(intent);
				           

				        }
				    });
    
    		} catch (JSONException e) {
    			e.printStackTrace();
    		}

    		 
    	 }
    		
    }

	// function shows marker on map
    /**
     * A description of what the method does
     *
     * @param       map    Google map reference 
     * @param       lat    latitude   
     * @param       lon    longitude  for map marker
     * @param       title  Contains Phone number of marker object  
     * @param       info   tutor whole information
     *
     * @return              void
     *
     * @exception   name    description
     * @exception   name    description
                     .
                     .
     * @exception   name    description
     */
    
	private void addMarker(GoogleMap map, double lat, double lon,
	            String title, Tutor info)
	{
		MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(lat, lon))
	            .title(title)
	            .snippet(info.getPh())
	            .icon(BitmapDescriptorFactory.fromResource(R.drawable.school));
		
		
		markers.put(map.addMarker(markerOptions).getId(),info);
	
	}
   @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
      
}
