package com.NASB.TuitionMedia;

import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.EditText;
 
public class LoginActivity extends Activity implements OnClickListener{
 
	
	UserCredentials user = new UserCredentials();
	
	TextView forget;
	private int i =0; 
	boolean val = false;
	private EditText phn;
	private EditText pass;
	private Button btn;
	private Button btnreg;
	
	
	// 2 URL Teacher and Gurdian respectively 
    String urltec ="http://nasb.herobo.com/tut_login.php";
    String urlgur ="http://nasb.herobo.com/gur_login.php";
    String url=urlgur;
    RadioGroup tu;
    int variable;
    private RadioGroup gartut;
    private RadioButton select;
    private TextView help;
//	private ProgressBar pb;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		new AlertDialog.Builder(this).create();
		
		
		phn=(EditText)findViewById(R.id.phn);
		pass=(EditText)findViewById(R.id.pass);
		btn=(Button)findViewById(R.id.button1);
		btnreg=(Button)findViewById(R.id.button2);
		forget=(TextView) findViewById(R.id.forget);
		gartut = (RadioGroup) findViewById(R.id.radioGroup1);
		help=(TextView)findViewById(R.id.textView1);
		
		btn.setOnClickListener(this);
		
		
			
		ConnectivityManager nai = (ConnectivityManager) getSystemService(Context
				.CONNECTIVITY_SERVICE);
		NetworkInfo net = nai.getActiveNetworkInfo();
		if(net==null)
		{
			AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(LoginActivity.this);
			alertDialogBuilder.setMessage("No Internet connection." +
					"Please check to continue")
					.setCancelable(false)
					.setTitle("No Connection")
					.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				
							
							public void onClick(DialogInterface dialog, int which) {
					
								startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS), 0);
									return;
								}
					})
					.setNegativeButton("No", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.cancel();
								// add app cancel feature 
							}
						});
				
			alertDialogBuilder.show();
		}
		btnreg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent reg=new Intent(LoginActivity.this,Signup.class);
				MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.button);
				mp.start();
				startActivity(reg);
			}
		});
		
		
		help.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.button);
				mp.start();
				Intent in1= new Intent(LoginActivity.this,Help.class);
	        	startActivity(in1);
				
			}
		});
		
		addListenerOnChkIos();
		
		
		
			
	     //myOption3.setOnClickListener(myOptionOnClickListener);
	}
	public void addListenerOnChkIos() {
		 
		 gartut.setOnCheckedChangeListener( 
	                new  RadioGroup.OnCheckedChangeListener() { 
	                    public  void  onCheckedChanged(RadioGroup group, 
	                            int  checkedId) { 
	                    	int selectedId = gartut.getCheckedRadioButtonId();
	                		select=(RadioButton) findViewById(selectedId);	                		
	                		String opinion = select.getText().toString();
	                		if(!opinion.equals("Guardian"))
	                		{
	                			url = urltec;
	                			val = true;
	                			
	                		}
	                		else 
	                		{
	                			url = urlgur;
	                			
	                		}
	                    	
	                    } 
	                }); 
	 
		
	  }
	
 
	public void onClick(View v) {
		MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.button);
			mp.start();
		// TODO Auto-generated method stub
			if(phn.getText().toString().equals(""))
			{
				Toast.makeText(getApplicationContext(), 
						"Please enter Phone Number", Toast.LENGTH_LONG).show();
			}
			else if(pass.getText().toString().equals(""))
			{
				Toast.makeText(getApplicationContext(), 
						"Please enter  password", Toast.LENGTH_LONG).show();

			}
			
			else
			{
				user.SetUserCredentials(phn.getText().toString(),
						pass.getText().toString());
				
				new MyAsyncTask().execute(url);
			}
				
			
		
		
		
 
	} 
 
	private class MyAsyncTask extends AsyncTask<String, Integer, JSONObject>{
		
		private ProgressDialog pDialog;
    	@Override
        protected void onPreExecute() {
    		 super.onPreExecute();
 			
             pDialog = new ProgressDialog(LoginActivity.this);
             pDialog.setTitle("Logging in...");
             pDialog.setMessage("Please Wait..... ");         
             pDialog.setCancelable(true);
             pDialog.show();
             
    	}
            
		@Override
		
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			PostData DataPost  = new PostData();
			JSONObject json = DataPost.postData(url,user.GetUserCredentials(), 
					user.GetUserCredentialsKeys());
    		
			return json;
			
		}
 
		protected void onPostExecute(JSONObject json){
			//pb.setVisibility(View.GONE);
			String St = json.toString();
			Log.d("JSSSONNN", St);
            if(St.contains("Incorrect"))
            {
            	//onPostExecute("Incorrect");
            	i=0;
            	Log.e("JSON", "Incorrect");
            }
            else
            {
            	Log.e("JSON", "Correct");
            	if(url.equals(urlgur))
            		i =2;
            	else i =1;
            }
            
			if(i ==1)
			{
				Intent t=new Intent(LoginActivity.this,TTab_Main.class);
				t.putExtra("phn", phn.getText().toString());
				startActivity(t);
				finish();
			}
			else if(i ==2)
			{
				Intent g=new Intent(LoginActivity.this,GTab_Main.class);
				g.putExtra("phn", phn.getText().toString());
				startActivity(g);
				
				finish();
			}
			else
			{
				Toast.makeText(getApplicationContext(), "You have entered wrong " +
						"phone number or password", Toast.LENGTH_LONG).show();
			}
			
			pDialog.dismiss();
		}
		protected void onProgressUpdate(Integer... progress){
			//pb.setProgress(progress[0]);
			  
        
		}
 
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
	        	Intent in= new Intent(LoginActivity.this,About.class);
	        	startActivity(in);
	        	break;

	        	
	
	            //return true;
	        default:
	            return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
}





 
