package com.NASB.TuitionMedia;

import java.io.Serializable;

public class Person implements Serializable{

	private static final long serialVersionUID = 1L;

	private String Name, Phone,Pass, CnPass, Email;
	public Person()
	{
		
	}
	public void set_all(String name,String phone,String pass,String cnPass,String email) {
		
		Name = name;
		Phone = phone;
		
		Pass = pass;
		CnPass= cnPass;
		
		Email = email;
	}
	public String Get_Name()
	{
		return Name;
	}
	
	public String Get_Phone()
	{
		return Phone;
	}
	
	public String Get_Pass()
	{
		return Pass;
	}
	
	public String Get_CnPass()
	{
		return CnPass;
	}
	
	public String Get_Email()
	{
		return Email;
	}
}
