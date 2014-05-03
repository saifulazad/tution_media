package com.NASB.TuitionMedia;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tutor implements Serializable{

	private static final long serialVersionUID = 1L;

	private String SalMin;    // all attribule of a teacher / tutor
	private String SalMax;
	private String state;
	private String Inst;
	private String phn;
	private String Name;
	private String Sub;
	
	
	private double lat , lng;
	
	private Map<String, String> Phone = new HashMap<String, String>();

	List<String> Values = Arrays.asList("Phone", "lat", "lng");
	
	private Map<String, String> Password = new HashMap<String, String>();
	public Tutor(String Name,String SalMin,String SalMax,String state,String Inst,String ph)
	{
		this.SalMin=SalMin;
		this.SalMax=SalMax;
		this.state=state;
		this.Inst=Inst;
		this.phn=ph;
		this.Name = Name;
	}
	public Tutor(String phone , double lat , double lon)
	{
		this.lat = lat;
		this.lng = lon;
		this.phn=phone;
	}
	public void set_lat_lang(double lat , double lon) {
		
		this.lat = lat;
		lng = lon;
	}
	public String getMin() {
		return SalMin;
	}
	public String getSub() {
		return Sub;
	}
	public String getMax() {
		return SalMax;
	}
	public double getlatitude() {
		return lat;
	}
	public double getlongitite() {
		return lng;
	}
	public String getName() {
		return Name;
	}
	public String getState() {
		return state;
	}
	
	public String getInst() {
		return Inst;
	}
	
	public String getPh()
	{
		return phn;
	}
	public List<Map<String, String>> GetUserCredentials(){
		return Arrays.asList(Phone, Password);
	}
	public List< String> GetUserCredentialsKeys(){
		return this.Values;
	}

}