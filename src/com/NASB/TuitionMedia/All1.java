package com.NASB.TuitionMedia;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class All1 extends Activity{
	TextView t1;
	TextView t2;
	TextView t3;
	StringBuffer sp;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all);
	
	TutorS nt=(TutorS) getIntent().getSerializableExtra("All");
	//in.getIntent("Hello");
	t1=(TextView) findViewById(R.id.textView1);
	t2=(TextView) findViewById(R.id.textView2);
	t3=(TextView) findViewById(R.id.textView3);
	
	t1.setText(nt.t.getMax());
	t2.setText(nt.t.getMin());
	t3.setText(nt.t.getState());
	//t2.setText(nt.getmin().toString());
	//t3.setText(nt.getobj().getMax().toString());
	//t2.setText(nt.getobj().getb().toString());
	
	

	}
}
