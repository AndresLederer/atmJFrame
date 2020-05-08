package itsBank;

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Bank {
	//atributes
	private static ArrayList <Client> bankClients = new ArrayList<Client>();
	private static ArrayList <BankAccount> bankAccounts = new ArrayList<BankAccount>();
//	private static Date actualDate;
	
	//main method
	public static void main(String[] args) throws ParseException  {
//		String s = "1234cinco";
//		if(!s.equals("")) {
//			System.out.println("no vacio");
//		}
//		try {
//			double id = Double.parseDouble(s);
//			System.out.println(id);
//		}catch (NumberFormatException nEx) {
//			System.out.println("el id no es double 100%");
//		}
		try {
			//4 already initialized clients
			bankClients.add(new Client("Benjamin","Juarez","JB Justo Street 2412","40719256",new SimpleDateFormat("dd/MM/yyyy").parse("10/05/1997")));
			bankClients.add(new Client("Andrés","Lederer Dobra","Larrea Street 2230","39647094",new SimpleDateFormat("dd/MM/yyyy").parse("02/07/1996")));
			bankClients.add(new Client("Jael","Stainer","Don Donalds Road 24","39456852",new SimpleDateFormat("dd/MM/yyyy").parse("24/09/1996")));
			bankClients.add(new Client("Luiza","Paixao","Whiteladies Road 101","40234122",new SimpleDateFormat("dd/MM/yyyy").parse("27/01/1997")));
			
			//4 already initializes bank accounts
			bankAccounts.add(new BankAccount(bankClients.get(0),"pass1")); //BENJAMIN JUAREZ ACCOUNT
			bankAccounts.add(new BankAccount(bankClients.get(1),"pass2")); //ANDRES LEDERER ACCOUNT
			bankAccounts.add(new BankAccount(bankClients.get(2),"pas3")); //JAEL STAINER ACCOUNT
			bankAccounts.add(new BankAccount(bankClients.get(3),"pass4")); //LUIZA PAIXAO ACCOUNT
			
			//initializes GUI (JFrame) && makes it visible
			Window  mainWindow = new Window(bankClients,bankAccounts);
			mainWindow.setVisible(true);
			
			
		}catch(ParseException pEx) {
			System.out.println("parse exception!");
		}
	}
	
//	//checks client personal ID && password for their account -- returns true if the password matches the client password account
//	private static boolean checkPassword(String password,String personalId) {
//		boolean access = false;
//		for(BankAccount b : bankAccounts) {
//			if(b.getAccountClient().getPersonalId().equals(personalId) && b.getPassword().equals(password)) {
//				access = true;
//			}
//		}
//		return access;
//	}
	//serches (by a personal ID client) and returns a Bank Account -- returns NULL if !found
	private static BankAccount getAccount(String personalIdClient) {
		BankAccount baSearched = null;
		for(BankAccount b : bankAccounts) {
			if(b.getAccountClient().getPersonalId().equals(personalIdClient)) {
				baSearched = b;
			}
		}
		return baSearched;
	}
	
	//shows a Bank Account in CMD
	private static void showAccount(BankAccount ba) {
		System.out.println(ba.toString());
	}
	
	//shows all Bank Accounts in CMD
	private static void showAllAccounts() {
		System.out.println("<<< ALL BANK ACCOUNTS >>>");
		for(BankAccount b : bankAccounts) {
			System.out.println(b.toString());
		}
	}
	
	
}
















