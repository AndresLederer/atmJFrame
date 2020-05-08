package itsBank;

import java.util.*;
import java.text.*;

public class Client {
	//atributes
	private String name;
	private String lastName;
	private String address;
	private final String personalId; 
	private final Date birth;
	
	//setters
	public void setName(String name) {
		this.name = name;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	//gettes
	public String getName() {
		return name;
	}
	public String getLastName() {
		return lastName;
	}
	public String getAddress() {
		return address;
	}
	public String getPersonalId() {
		return personalId;
	}
	public Date getBirth() {
		return birth;
	}
	
	//returns birth Date in a String
	public String getBirthString() {
		SimpleDateFormat spd = new SimpleDateFormat("dd/MM/yyyy");
		String birth = spd.format(getBirth());
		return birth;
	}
	
	//constructor
	public Client(String name,String lastName,String address,String personalId,Date birth) {
		setName(name);
		setLastName(lastName);
		setAddress(address);
		this.personalId = personalId;
		this.birth = birth;
	}
	
	//toString() method @Override
	@Override
	public String toString() {
		String string = "\t> Name: "+name+"\n\t> Last name: "+lastName+"\n\t> Address: "+address+"\n\t> Personal ID: "+personalId+"\n\t> Born: "+getBirthString();
		return string;
	}
	
















}
