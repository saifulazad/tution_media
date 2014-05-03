package com.NASB.TuitionMedia;

import java.io.Serializable;

public class TutorS implements Serializable {
	
	Tutor t;
	StringBuffer sub;
	public TutorS(Tutor tutor,StringBuffer sub)
	{
		this.t= tutor;
		this.sub=sub;
	}
	
	public Tutor getobj()
	{
		return t;
	}
	
	public String getSub()
	{
		return sub.toString();
	}
	
	

	
	

}
