package com.NASB.TuitionMedia;




import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.Menu;



public class Tutor_Add extends Activity {
	
	
	
	EditText max;
	EditText min;
	EditText inst;
	Button nxt;
	String Ph;
	Spinner sp;
	ArrayList<String> difficultyLevelOptionsList = new ArrayList<String>();
	@Override
	public void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutor_add);
		
		//registerForContextMenu(getExpandableListView());
		Intent intent=getIntent();
		Ph = intent.getExtras().getString("phn");
		min=(EditText) findViewById(R.id.min);
		max=(EditText) findViewById(R.id.max);
		inst=(EditText) findViewById(R.id.inst);
		sp=(Spinner) findViewById(R.id.spinner1);
		nxt=(Button) findViewById(R.id.next); 		
		Ph =getIntent().getExtras().getString("phn");
		
		
		
		difficultyLevelOptionsList.add("Primary");
		difficultyLevelOptionsList.add("Secondary");
		difficultyLevelOptionsList.add("Higher Secondary");
		
		 
		  // Create the ArrayAdapter
		  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Tutor_Add.this
		            ,android.R.layout.simple_spinner_item,difficultyLevelOptionsList);
		  
		                 // Set the Adapter
		  sp.setAdapter(arrayAdapter);
		  
		  nxt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.button);
				mp.start();
				if(min.getText().toString().length()<1)
				{
					Toast.makeText(getApplicationContext(), "Enter minimum Salary", 
							Toast.LENGTH_LONG).show();
					
				}
				else if(max.getText().toString().length()<1)
				{
					Toast.makeText(getApplicationContext(), "Enter maximum Salary", 
							Toast.LENGTH_LONG).show();
				}
				
				else if(Integer.parseInt(min.getText().toString())>
					Integer.parseInt(max.getText().toString()))
				{
					Toast.makeText(getApplicationContext(), "Enter Salary Properly", 
							Toast.LENGTH_LONG).show();
				}
				else if(inst.getText().toString().length()<1)
				{
					Toast.makeText(getApplicationContext(), "Enter Your Educational " +
							"Institute", 
						Toast.LENGTH_LONG).show();
				}
				
				else
				{

					Intent in=new Intent(Tutor_Add.this, Tutor_Subject.class);
					Tutor tutor=new Tutor("",min.getText().toString(),max.getText().toString(),
		        		sp.getSelectedItem().toString(),
		        		inst.getText().toString(),Ph.toString());
												
					in.putExtra("Tutor", tutor);
					startActivity(in);
				}
			}	
			
		});
	
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
	        	Intent in= new Intent(Tutor_Add.this,About.class);
	        	startActivity(in);
	        	break;

	        	
	      
	            //return true;
	        default:
	            return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
}





 
		    

		
		 