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

public class Rating extends Activity {

	ArrayList<String> tutor_info_key = new ArrayList<String>(); /// list hold all keys 
	
	TextView name , sub, phone , salrange , institute;
	ArrayList<String> tutor_info_val = new ArrayList<String>(); // list hold all values
	
	
	Button callbutton, ratingbutton;
	
	private static final String json_head_for_tutor = "TutorAdd_info";

	JSONArray android = null;

	private static String tutor_add_all_info = "http://nasb.herobo.com/Tutor_comment.php";
	
	Tutor info;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.t_rateit);
		
	
		
		
}
}