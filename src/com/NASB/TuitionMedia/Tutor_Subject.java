package com.NASB.TuitionMedia;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Tutor_Subject extends Activity {

	MyCustomAdapter dataAdapter = null;
	EditText et;
	StringBuffer sp;
	Tutor tutor;
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutor_subject);
		
		//et=(EditText) findViewById(R.id.editText1);
		
		// Generate list View from ArrayList
		
		
		tutor=(Tutor) getIntent().getSerializableExtra("Tutor");
		displayListView();
		receive();
		
		checkButtonClick(receive());
	}

	private void displayListView() {

		// Array list of countries
		ArrayList<Tsubject> stateList = new ArrayList<Tsubject>();

		Tsubject _states = new Tsubject( "Bangla", false);
		stateList.add(_states);
		_states = new Tsubject( "English", false);
		stateList.add(_states);
		_states = new Tsubject( "Mathematics", false);
		stateList.add(_states);
		_states = new Tsubject( "Biology", false);
		stateList.add(_states);
		_states = new Tsubject( "Physics", false);
		stateList.add(_states);
		_states = new Tsubject( "Chemistry", false);
		stateList.add(_states);
		_states = new Tsubject( "Accounting", false);
		stateList.add(_states);
		
		// create an ArrayAdaptar from the String Array
		dataAdapter = new MyCustomAdapter(this, R.layout.tstate_info, stateList);
		ListView listView = (ListView) findViewById(R.id.listView1);
		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				parent.getItemAtPosition(position);
				
			}
		});
	}

	private class MyCustomAdapter extends ArrayAdapter<Tsubject> {

		private ArrayList<Tsubject> stateList;

		public MyCustomAdapter(Context context, int textViewResourceId,

		ArrayList<Tsubject> stateList) {
			super(context, textViewResourceId, stateList);
			this.stateList = new ArrayList<Tsubject>();
			this.stateList.addAll(stateList);
		}

		private class ViewHolder {
			TextView code;
			CheckBox name;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;

			Log.v("ConvertView", String.valueOf(position));

			if (convertView == null) {

				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				convertView = vi.inflate(R.layout.tstate_info, null);

				holder = new ViewHolder();
				holder.code = (TextView) convertView.findViewById(R.id.code);
				holder.name = (CheckBox) convertView
						.findViewById(R.id.checkBox1);

				convertView.setTag(holder);

				holder.name.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						CheckBox cb = (CheckBox) v;
						Tsubject _state = (Tsubject) cb.getTag();

						
						_state.setSelected(cb.isChecked());
					}
				});

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Tsubject state = stateList.get(position);

			
			holder.name.setText(state.getName());
			holder.name.setChecked(state.isSelected());

			holder.name.setTag(state);

			return convertView;
		}

	}
	
	
	public StringBuffer receive()
	{
		
		StringBuffer responseText = new StringBuffer();

		ArrayList<Tsubject> stateList = dataAdapter.stateList;
		

		for (int i = 0; i < stateList.size(); i++) {
			Tsubject state = stateList.get(i);

			if (state.isSelected()) {
				
				responseText.append("\n" + state.getName());
			}
			
			
		}
		return responseText;
		
	}
	
	
	
	private void checkButtonClick(StringBuffer bf) {

		Button myButton = (Button) findViewById(R.id.findSelected);
		
		myButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.button);
				mp.start();
				if(receive()==null)
				{
					Toast.makeText(getApplicationContext(), "Select Subject", 
							Toast.LENGTH_LONG).show();
				}
				else
				{
					Intent in=new Intent(Tutor_Subject.this, Tutor_Addmap.class);
					TutorS nt=new TutorS(tutor, receive());
					in.putExtra("All", nt);				
					startActivity(in);
				}

			}
		});

	}
}