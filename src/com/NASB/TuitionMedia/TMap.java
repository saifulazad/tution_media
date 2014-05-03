package com.NASB.TuitionMedia;
import java.util.HashMap;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;






public class TMap extends FragmentActivity {
	
	
	private static String url = "http://nasb.herobo.com/gur_data.php";
	
	//JSON Node Names 
	private static final String TAG_OS = "TutorAdd_info";
	SupportMapFragment map;
	private GoogleMap googlemap;
	Editor editot;
	Context context;
	//final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
	//URL to get JSON Array
	String tpon ;
	
	// Json array hold all gurdian informations 
    
	JSONArray gurdian_info = null;
	private HashMap<String, Guardian> markers= new HashMap<String, Guardian>();


	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        setContentView(R.layout.map);
        
        Intent intent=getIntent();
		tpon = intent.getExtras().getString("phn");
	
     //   oslist = new ArrayList<HashMap<String, String>>();
        googlemap= ((SupportMapFragment) getSupportFragmentManager().
        		findFragmentById(R.id.map)).getMap();
			
		
		//LatLng latLng = new LatLng(24.0167, 89.8667);
     if(serviceOK())
     {
		 new JSONParse().execute();
     }
     else
    	 Toast.makeText(getApplicationContext(), "You need to Install Google" +
    	 		"Play Service ",Toast.LENGTH_LONG).show();
		 if (getIntent().getBooleanExtra("EXIT", false)) {
			    finish();  
			}

		
		 googlemap.setInfoWindowAdapter(new PopupAdapter(getLayoutInflater()));  
		 
		 
        
    }

	void OnClickListener(MarkerOptions mark,String n,String s,String i)
	{
		/*System.out.println("name:" + n);
		System.out.println("name:" + s);
		System.out.println("name:" + i);*/
		
		
		
	}
    
    private class JSONParse extends AsyncTask<String, String, JSONObject> {
    	 private ProgressDialog pDialog;
    	@Override
        protected void onPreExecute() {
            super.onPreExecute();
			// name = (TextView)findViewById(R.id.name);
		//	 api = (TextView)findViewById(R.id.api);
            pDialog = new ProgressDialog(TMap.this);
            pDialog.setMessage("Getting Data ...");          
            pDialog.setCancelable(true);
            pDialog.show();
            
            
            
    	}
    	
    	@Override
        protected JSONObject doInBackground(String... args) {
    		
    		JSONParser jParser = new JSONParser();
    		// Getting JSON from URL
    		JSONObject json = jParser.getJSONFromUrl(url,tpon);
    		return json;
    	}
    	 @Override
         protected void onPostExecute(JSONObject json) {
    		 pDialog.dismiss();
    		 
    		 		 try {
    				// Getting JSON Array from URL
    		 		gurdian_info = json.getJSONArray(TAG_OS);
    				final JSONObject p = gurdian_info.getJSONObject(0);
    				String count = p.getString("count");
		    		Log.d("COUNT", count.toString());
		    		int len  = Integer.parseInt(count);
		    		int i;
		    		for(i = 1; i <= len; i++) 
					{
	 						// catch it
		    				final JSONObject c = gurdian_info.getJSONObject(i);
		    				
		    				// Oh google makes it easy
		    				
		    				Gson gson=new Gson(); 
		    				String Json=c.toString();  // convert as String
	 	    			
		    				Guardian ob; // new object

		    				// parse it as tutor object
		    				ob = (Guardian) gson.fromJson(Json, Guardian.class);
		    				
		    				// function call for printing maker on map 
		    				
		    				addMarker(googlemap,ob.getlatitude(),ob.getlongitite(),
		    						"NAME : "+ob.getName(),ob);
		    				  			  		        				   				
					}
    		/*		for(i = 1; i <= len; i++)
    				{
		    				final JSONObject c = android.getJSONObject(i);
		    				
		    				// Storing  JSON item in a Variable
		    				String phone = c.getString(phn);
		    				String Sub = c.getString(sub);
		    				String minn = c.getString(min);
		    				String maxx = c.getString(max);
		    				
		    	
		    				String name=c.getString(Name);
		    				
		    				double apii = Double.parseDouble((c.getString(lng))) ;//.to;
		    				double api = Double.parseDouble((c.getString(lat))) ;
		    				String item ;
		    				System.out.print(",");
		    				System.out.print(api);
		    				System.out.print(",");
		    				System.out.print(apii);
		    				System.out.print(",");
		    				item   = "Salary : " + minn +" - " + maxx +"\n"
		    						+"Subject : " +Sub+"\n";
		    					    				
		    				addMarker(googlemap,api,apii,"NAME : "+name,phone,item);
		    				Log.d("JSSSOON", c.toString());
		    				  			  		        
    				   				
    				}
    				for(; i < android.length(); i++)
    				{
		    				final JSONObject c = android.getJSONObject(i);
		    				
		    				String Sub = c.getString(sub);
		    				String minn = c.getString(min);
		    				String maxx = c.getString(max);
		    				
		    	
		    				double apii = Double.parseDouble((c.getString(lng))) ;//.to;
		    				double api = Double.parseDouble((c.getString(lat))) ;
		    				String item ;
		    						
		    				item   = "Salary : " + minn +" - " + maxx +"\n"
		    						+"Subject : " +Sub+"\n";
		    					    				
		    				addMarker1(googlemap,api,apii,"You",item);
		    				Log.d("JSSSOON", c.toString());
		    				googlemap.moveCamera( CameraUpdateFactory.
		    						newLatLngZoom(new LatLng(api,apii) , 8f) );
		    				  			  		           				   				
    				}*/
    			
    				googlemap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
    					
    					@Override
    					public void onInfoWindowClick(Marker marker) {
    						// TODO Auto-generated method stub
    						// catch a marker info (tutor object)
				        	Guardian gurdian_info = (Guardian) markers.get(marker.getId());
				        	// call intent
				        	Intent intent = new Intent(TMap.this,Otheractivity.class);
				        	// pass to OtherActivity as onject
				        	intent.putExtra("gurdian_info", gurdian_info);
				        	// call activity
				        	startActivity(intent);
							
    					}
    				});
    			
    
    		} catch (JSONException e) {
    			e.printStackTrace();
    		}
    	}
 			
    		 
    	 
    }  
    private boolean serviceOK() {
		int isAvailable = GooglePlayServicesUtil
			.isGooglePlayServicesAvailable(this);

			if (isAvailable == ConnectionResult.SUCCESS) {
				return true;
			} else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
			Dialog d = GooglePlayServicesUtil.getErrorDialog(isAvailable, this,
				9001);
				d.show();
			} else {
				Toast.makeText(getApplicationContext(),
				"Cannot connected to Google Play Service",
				Toast.LENGTH_SHORT).show();
			}
		return false;
	}
    
    
    
    private void addMarker(GoogleMap map, double lat, double lon,
            String title, Guardian info)
	{
		MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(lat, lon))
	            .title(title)
	            .snippet(info.getPh())
	            .icon(BitmapDescriptorFactory.fromResource(R.drawable.school));
		
		
		markers.put(map.addMarker(markerOptions).getId(),info);
	
	}
    
  /*  private void addMarker1(GoogleMap map, double lat, double lon,
            String title,  String item)
    {
    	MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(lat, lon))
                .title((title))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.male));
;
				markers.put(map.addMarker(markerOptions).getId(), item);

  	
    }*/
    
    
    public void alert(final String phn_no,String item)
    {
    	final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.
				Builder(TMap.this);				
		alertDialog.setTitle("Details..");
		
		/*alertDialogBuilder.setMessage("Subject:"+sub+"\n"+"Institute:"+inst+
				"Salary:"+min+"-"+max)*/
		alertDialogBuilder.setMessage(item).setCancelable(false)
				.setPositiveButton("Phone", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					 	try {
		            	    Intent my_callIntent = new Intent(Intent.ACTION_CALL);
		            	    my_callIntent.setData(Uri.parse("tel:"+phn_no));
		            	    //here the word 'tel' is important for making a call...
		            	    startActivity(my_callIntent);
		            	} catch (ActivityNotFoundException e) {
		            	    Toast.makeText(getApplicationContext(), "Error in your phone call"+e.getMessage(), Toast.LENGTH_LONG).show();
		            	}
		
					
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
		
		AlertDialog alertDialog2 = alertDialogBuilder.create();
		alertDialog2.show();
	}
    

        

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
    
       
}
