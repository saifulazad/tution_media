package com.NASB.TuitionMedia;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.widget.EditText;
 
public class Signup extends Activity  {
 
	private EditText name,cpass,pass,mail,phn;
	private Button reg;
	static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    private int i =0; 
	boolean val = false;
	String pas,cpas,nam,email,phnn;
    
    String urltec ="http://nasb.herobo.com/tut_signup.php";
    String urlgur ="http://nasb.herobo.com/gur_signup.php";
    String url = urlgur;
    RadioGroup tu;
    int variable;
    private CheckBox chkIos;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		
		name=(EditText)findViewById(R.id.name);
		cpass=(EditText)findViewById(R.id.cpass);
		phn=(EditText)findViewById(R.id.phn);
		pass=(EditText)findViewById(R.id.pass);
		mail=(EditText) findViewById(R.id.email);
		reg=(Button)findViewById(R.id.register);
		
		pas=pass.getText().toString();
		cpas=pass.getText().toString();
		nam=name.getText().toString();
		email=mail.getText().toString();
		phnn=phn.getText().toString();
		
		addListenerOnChkIos();
		
	//register button and alert box
		reg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.button);
				mp.start();
				if (!name.getText().toString().matches("[A-Za-z]{1,}"))
	            {
	            	Toast.makeText(getApplicationContext(),"Name is invalid", Toast.LENGTH_SHORT).show();
	            	
	            }
				
				if (!phn.getText().toString().matches("[0-9]{11}"))
	            {
	            	Toast.makeText(getApplicationContext(),"Phone Number is  invalid", Toast.LENGTH_SHORT).show();
	            	
	            }
				else if(pass.getText().toString().length()<5) 
				{
					
					Toast.makeText(Signup.this, "Passwords will atleast 5 character", Toast.LENGTH_LONG)
					.show();
				}

				else if(!cpass.getText().toString().equals(pass.getText().toString()))
				{
					Toast.makeText(Signup.this, "Password and Confirm password" +
							"is not matched", Toast.LENGTH_LONG)
					.show();
			
				}
				else if (!mail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
	            {
	            	Toast.makeText(getApplicationContext(),"Email is  invalid", Toast.LENGTH_SHORT).show();
	            	
	            }
				
				else
				{
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.
							Builder(Signup.this);				
					alertDialog.setTitle("Confirmation..");
					
						alertDialogBuilder.setMessage("Are You Sure To Register with"+"\n"
								+"Name: "+name.getText().toString()+"\n"+"Phone No.: "+phn.getText().toString()+"\n"
								+"Email: "+mail.getText().toString())
							
							.setCancelable(false)
							.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									new POST_Person().execute(name.getText().toString(),phn.getText()
											.toString(),
											pass.getText().toString(),mail.getText().toString());	
								}
							})
							.setNegativeButton("No", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							});
					
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
				
					
					
				}
					
					
					
			        
			        
					
			}
		});
	}
		
	
	
	
	public void addListenerOnChkIos() {
		 
		chkIos = (CheckBox) findViewById(R.id.checkBox1);
	 
		chkIos.setOnClickListener(new OnClickListener() {
	 
		  @Override
		  public void onClick(View v) {
	                //is chkIos checked?
			if (((CheckBox) v).isChecked()) {
				
			
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
	

	
	private class POST_Person extends AsyncTask<String, Integer, Double>{
		
		
 
		@Override
		
		protected Double doInBackground(String... params) {
			// TODO Auto-generated method stub
			postData(params[0], params[1],params[2], params[3]);
			return null;
		}
 
		protected void onPostExecute(Double result){
			//pb.setVisibility(View.GONE);
			if(i == 1)
			{
				Toast.makeText(Signup.this, "You are successfully registered", Toast.LENGTH_LONG)
				.show();
				Intent login=new Intent(Signup.this,LoginActivity.class);
				startActivity(login);
				finish();
			}
			else
				Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_LONG).show();
		}
		protected void onProgressUpdate(Integer... progress){
			//pb.setProgress(progress[0]);
		}
 
		public void postData(String name, String phn,String pass, String mail) {
			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
 
			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("name", name));
				nameValuePairs.add(new BasicNameValuePair("phn", phn));
				nameValuePairs.add(new BasicNameValuePair("pass", pass));
				nameValuePairs.add(new BasicNameValuePair("mail", mail));
				//nameValuePairs.add(new BasicNameValuePair("edu",edu));
				
				
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
	        	Intent in= new Intent(Signup.this,About.class);
	        	startActivity(in);
	        	break;

	        	
	        
	            //return true;
	        default:
	            return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
}





 
