package com.NASB.TuitionMedia;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;




public class ForgetPass extends Activity implements OnClickListener{
	
	private EditText phn;
	private EditText mail;
	boolean val = false;
	private Button fget;
	private int i =0; 
	static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";    
    String urltec ="http://nasb.herobo.com/Forgetpasstutor.php";
    String urlgur ="http://nasb.herobo.com/Forgetpassguardian.php";
    String url=urlgur;
    RadioGroup tu;
    int variable;
    private RadioGroup gartut;
    private RadioButton select;
//	private ProgressBar pb;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forget);
		
		new AlertDialog.Builder(this).create();
	//	add_split a =new add_split();
	//	a.make_split("[asas][asas][asas]");
		phn=(EditText)findViewById(R.id.editText1);
		mail=(EditText)findViewById(R.id.editText2);		
		fget=(Button)findViewById(R.id.button1);
		gartut = (RadioGroup) findViewById(R.id.radioGroup1);
		
		fget.setOnClickListener(this);
		
		
		
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
			else if(mail.getText().toString().equals(""))
			{
				Toast.makeText(getApplicationContext(), 
						"Please enter  Mail Adress", Toast.LENGTH_LONG).show();

			}
			else 
			{
				
				ConnectivityManager nai = (ConnectivityManager) getSystemService(Context
						.CONNECTIVITY_SERVICE);
				NetworkInfo net = nai.getActiveNetworkInfo();
				if(net==null)
				{
					AlertDialog.Builder alertDialogBuilder= new AlertDialog
							.Builder(ForgetPass.this);
					alertDialogBuilder.setMessage("No Internet connection." +
							"Please check to continue")
							.setCancelable(false)
							.setTitle("No Connection")
							.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
						
									public void onClick(DialogInterface dialog, int which) {
							
											return;
										}
							})
							.setNegativeButton("No", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										dialog.cancel();
									}
								});
						
					alertDialogBuilder.show();
				}
				else
				{
		
					new MyAsyncTask().execute(mail.getText().toString(),
							phn.getText().toString()
							);
				}
					
			}
		
		
		
 
	} 
 
	private class MyAsyncTask extends AsyncTask<String, Integer, Double>{
		
		private ProgressDialog pDialog;
    	@Override
        protected void onPreExecute() {
    		 super.onPreExecute();
 			
             pDialog = new ProgressDialog(ForgetPass.this);
             pDialog.setTitle("Logging in...");
             pDialog.setMessage("Please Wait..... ");         
             pDialog.setCancelable(true);
             pDialog.show();
             
    	}
            
		@Override
		
		protected Double doInBackground(String... params) {
			// TODO Auto-generated method stub
			postData(params[0], params[1]);
			return null;
		}
 
		protected void onPostExecute(Double result){
			//pb.setVisibility(View.GONE);
			if(i ==1)
			{
				 try {   
						AlertDialog.Builder alertDialogBuilder= new AlertDialog
								.Builder(ForgetPass.this);
					alertDialogBuilder.setMessage("Do you want to send the" +
								"password to your email?")
								.setCancelable(false)
								.setTitle("Confirmation")
								.setPositiveButton("Yes",new DialogInterface.OnClickListener() {				
										public void onClick(DialogInterface dialog, int which) {
								
											try {
							                    GMailSender sender = new 
							                    		GMailSender("tuitionmediaappservice@gmail.com",
							                    		"nasb1234");
							                    sender.sendMail("This is Subject",   
							                            "This is Body","tuitionmediaappservice@gmail.com",     
							                            "sadimohammad7@gmail.com");   
							                } catch (Exception e) {   
							                    Log.e("SendMail", e.getMessage(), e);   
							                } 
										}
								})
								.setNegativeButton("No", new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											dialog.cancel();
										}
									});
							
						alertDialogBuilder.show();
					
					

	                } catch (Exception e) {   
	                    Log.e("SendMail", e.getMessage(), e);   
	                } 

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
 
		public void postData(String phone, String mail) {
			
			
			
			
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
 
			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				
				nameValuePairs.add(new BasicNameValuePair("myHttpDataps", mail));
				nameValuePairs.add(new BasicNameValuePair("myHttpDataph", phone));
				
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
	        	Toast.makeText(getBaseContext(), "You selected About", Toast.LENGTH_SHORT).show();
	        	break;

	            //return true;
	        default:
	            return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
}





 
