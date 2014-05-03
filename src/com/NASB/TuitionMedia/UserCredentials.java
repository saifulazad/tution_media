package com.NASB.TuitionMedia;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserCredentials {

//	private String  ,  ; // Users phone and pass;
	
	//Map<String, String> Phone;
	private Map<String, String> Phone = new HashMap<String, String>();

	List<String> Values = Arrays.asList("Phone", "Password");
	
	private Map<String, String> Password = new HashMap<String, String>();
	private final int Credential_len = 2;  // data length for Users
	
	public UserCredentials() {

	}
	public void SetUserCredentials( String Phone , String Password)  // Set user Credential 
	{
	
		this.Phone.put("Phone",  Phone) ;
		
		this.Password.put("Password",  Password) ;
	}
	public List<Map<String, String>> GetUserCredentials(){
		return Arrays.asList(Phone, Password);
	}
	public List< String> GetUserCredentialsKeys(){
		return this.Values;
	}
	
}
