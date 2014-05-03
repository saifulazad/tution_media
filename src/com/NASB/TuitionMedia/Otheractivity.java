package com.NASB.TuitionMedia;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Otheractivity extends Activity {

	ArrayList<String> tutor_info_key = new ArrayList<String>(); /// list hold all keys 
	
	TextView name , sub, phone , salrange , institute;
	ArrayList<String> tutor_info_val = new ArrayList<String>(); // list hold all values
	
	
	Button callbutton, ratingbutton;
	
	private static final String json_head_for_tutor = "TutorAdd_info";

	JSONArray android = null;
	private static String url = "http://nasb.herobo.com/atutor.php";
	Tutor info;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.t_specification);
		info = (Tutor) getIntent().getSerializableExtra("tutor_info");
		
		
	/*	name=(TextView)  findViewById(R.id.name);
		
		sub = (TextView)  findViewById(R.id.sub);
		
		phone=(TextView)  findViewById(R.id.phone);
		
		institute = (TextView)  findViewById(R.id.institute);
		
		
		salrange = (TextView)  findViewById(R.id.salaryRanges);
	*/
		callbutton = (Button) findViewById(R.id.callbutton);
		
		ratingbutton = (Button) findViewById(R.id.ratingbutton);
		
		
		//callbutton.setOnClickListener(this);
		ratingbutton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent reg=new Intent(Otheractivity.this,Rating.class);
					
						startActivity(reg);
						//alert(info.getPh(),info.getName());
					}
				});
		callbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
            	    Intent my_callIntent = new Intent(Intent.ACTION_CALL);
            	    my_callIntent.setData(Uri.parse("tel:"+info.getPh()));
            	    //here the word 'tel' is important for making a call...
            	    startActivity(my_callIntent);
            	} catch (ActivityNotFoundException e) {
            	    Toast.makeText(getApplicationContext(), "Error in your phone call"+e.getMessage(), Toast.LENGTH_LONG).show();
            	}
				//alert(info.getPh(),info.getName());
			}
		});
		
		
		tutor_info_key.add("phone");
		tutor_info_key.add("lat");
		tutor_info_key.add("lng");
		
		
		tutor_info_val.add(info.getPh());
		tutor_info_val.add(String.valueOf(info.getlatitude()));
		tutor_info_val.add(String.valueOf(info.getlongitite()));
		
		new JSONParse().execute();
		
		
	}
	
	private class JSONParse extends AsyncTask<String, String, JSONObject> {
   	 private ProgressDialog pDialog;
   	@Override
       protected void onPreExecute() {
           super.onPreExecute();
           
           pDialog = new ProgressDialog(Otheractivity.this);
           pDialog.setMessage("Getting Data ...");
         
           pDialog.setCancelable(true);
           pDialog.show();
           
           
           
   	}
   	
   	@Override
       protected JSONObject doInBackground(String... args) {
   		
   		PostData postVal = new PostData();
   	
   		
   		// Getting JSON from URL
   		JSONObject json = postVal.postData(url, tutor_info_key, tutor_info_val , 1);
   		return json;
   	}
   	 @Override
        protected void onPostExecute(JSONObject json) {
   		 pDialog.dismiss();
   		  try {
				// Getting JSON Array from URL
   			  	android = json.getJSONArray(json_head_for_tutor);
				final JSONObject p = android.getJSONObject(0);
				String count = p.getString("count");
	    		Log.d("COUNT", count.toString());
	    		int len  = Integer.parseInt(count);
	    		int i;
				for(i = 1; i <= len; i++)
				{
	    				final JSONObject c = android.getJSONObject(i);
	    				
	    				Log.d("TUTOR", c.toString());
	    				
	    				Gson gson=new Gson(); 
	    				String Json=c.toString();
	    			
	    				Tutor ob;
	    					
	    				ob = (Tutor) gson.fromJson(Json, Tutor.class);
	    				
	    				/*name.setText("NAME :"+ob.getName());
	    				phone.setText(ob.getPh());
	    				
	    				sub.setText("Subjects :"+ob.getSub());
	    				
	    				institute.setText("Institute :"+ob.getInst());
	    				
	    				salrange.setText("Salary :"+ob.getMin()+ "-"+ ob.getMax());*/
	 			}

   		} catch (JSONException e) {
   			e.printStackTrace();
   		}
	 
   	 }
   		
   }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
