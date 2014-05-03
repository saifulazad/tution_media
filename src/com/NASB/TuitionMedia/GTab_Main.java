package com.NASB.TuitionMedia;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class GTab_Main extends TabActivity{
	String Ph = "df"; 
	Intent intent;  
	Editor editor;
	Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guardian_tab);
		Ph =getIntent().getExtras().getString("phn");
		TabHost th = getTabHost();
		
		
		//setting up tutor tab  
		 TabSpec map = th.newTabSpec("Map");
		 map.setIndicator("Map");
		 Intent mapIntent = new Intent(this,GMap.class);
		 mapIntent.putExtra("phn", Ph);
	     map.setContent(mapIntent);
	     
	    
		
	     TabSpec add = th.newTabSpec("Add");
		 add.setIndicator("Addvertise");
		 Intent addIntent = new Intent(this, Guardian_ADD.class);
		 addIntent.putExtra("phn", Ph);
	     add.setContent(addIntent);
	     	     
	     th.addTab(map);
	     th.addTab(add);
	    		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);		
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	        case R.id.Logout:
	            // Red item was selected
	            if (item.getItemId() == R.id.Logout) {
	                SharedPreferences settings = getSharedPreferences("Logout", 0);
	                SharedPreferences.Editor editor = settings.edit();
	                editor.remove("logged");
	                editor.commit();
	                finish();

	        	//Toast.makeText(getBaseContext(), "You selected Phone", Toast.LENGTH_SHORT).show();
	        	break;
	            }
	            //return true;
	        case R.id.About:
	            // Green item was selected
	        	Intent in= new Intent(GTab_Main.this,About.class);
	        	startActivity(in);
	        	break;

	        	//return true;
	        default:
	            return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
}