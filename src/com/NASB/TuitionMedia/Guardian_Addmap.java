package com.NASB.TuitionMedia;


import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Guardian_Addmap extends FragmentActivity {
	SupportMapFragment map;
	private GoogleMap googlemap;
    String gurdian_add_giving_url = "http://nasb.herobo.com/gur_add.php";
    ArrayList<String> gurdian_info_key = new ArrayList<String>();
	ArrayList<String> gurdian_info_value = new ArrayList<String>();
	
    static String json = "";
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addmap);
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

		Guardian info = (Guardian) getIntent().getSerializableExtra("Guardian");

		gurdian_info_key.add("Name");
		gurdian_info_key.add("SalMin");
		gurdian_info_key.add("SalMax");
		gurdian_info_key.add("state");
		gurdian_info_key.add("Inst");
		gurdian_info_key.add("phn");
		gurdian_info_key.add("Sub");
		gurdian_info_key.add("lat");
		gurdian_info_key.add("lng");
		
		
		
		gurdian_info_value.add(info.getName());
		gurdian_info_value.add(info.getMin());
		gurdian_info_value.add(info.getMax());
		gurdian_info_value.add(info.getState());
		gurdian_info_value.add(info.getInst());
		gurdian_info_value.add(info.getPh());
		gurdian_info_value.add(info.getSub());
	
		googlemap= ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		//	googlemap.onResume();
		CameraUpdate center=
		        CameraUpdateFactory.newLatLng(new LatLng(23.684994000000000000,
		        		90.356330999999950000));
		    CameraUpdate zoom=CameraUpdateFactory.zoomTo(10);
		
		    googlemap.moveCamera(center);
		    googlemap.animateCamera(zoom);
		googlemap.clear();
			
			
		
		googlemap.setOnMapLongClickListener(new OnMapLongClickListener() {				
			@Override
			public void onMapLongClick(final LatLng point) {
				googlemap.clear();
				
				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.
						Builder(Guardian_Addmap.this);				
				alertDialog.setTitle("Confirmation..");
				
				alertDialogBuilder.setMessage("Are You Sure To give this Add in this location?")
						.setCancelable(false)
						.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								double lat = point.latitude;
								double lng  = point.longitude;
								gurdian_info_value.add(String.valueOf(lat));
								gurdian_info_value.add(String.valueOf(lng));
								googlemap.addMarker(new MarkerOptions().position(point));
								new JSONParse().execute();

							}
						})
						.setNegativeButton("No", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.cancel();
								//marker.remove();
							}
						});
				
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();												
			}
		});
		
	}
	private class JSONParse extends AsyncTask<String, String, JSONObject> {
   	
   	
   	@Override
       protected JSONObject doInBackground(String... args) {
   		
   			PostData postVal = new PostData();
   	   	
      	
      		JSONObject json = postVal.postData(gurdian_add_giving_url, gurdian_info_key,
      				gurdian_info_value , 1);
      		gurdian_info_value.remove(gurdian_info_value.size()-1);
      		gurdian_info_value.remove(gurdian_info_value.size()-1);
      		
      		return json;
   	}
   	 @Override
        protected void onPostExecute(JSONObject json) {
   		try {
				// Getting JSON Array from URL
				JSONArray info = json.getJSONArray(gurdian_add_giving_url);
				info.getJSONObject(0);
	    		
			
				
   
   		} catch (JSONException e) {
   			e.printStackTrace();
   		}

   		 
   	 }
}
}