package itsBank;

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Bank {
	//atributes
	private static ArrayList <Client> bankClients = new ArrayList<Client>();
	private static ArrayList <BankAccount> bankAccounts = new ArrayList<BankAccount>();
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	//main method
	public static void main(String[] args) throws ParseException  {
		try {
			//4 already initialized clients
			bankClients.add(new Client("Benjamin","Juarez","JB Justo Street 2412","40719256",dateFormat.parse("10/05/1997")));
			bankClients.add(new Client("Andrés","Lederer Dobra","Larrea Street 2230","39647094",dateFormat.parse("02/07/1996")));
			bankClients.add(new Client("Jael","Stainer","Don Donalds Road 24","39456852",dateFormat.parse("24/11/1996")));
			bankClients.add(new Client("Luiza","Paixao","Whiteladies Road 101","40234122",dateFormat.parse("27/01/1997")));
			
			//4 already initializes bank accounts
			bankAccounts.add(new BankAccount(bankClients.get(0),"pass1")); //BENJAMIN JUAREZ ACCOUNT
			bankAccounts.add(new BankAccount(bankClients.get(1),"pass2")); //ANDRES LEDERER ACCOUNT
			bankAccounts.add(new BankAccount(bankClients.get(2),"pass3")); //JAEL STAINER ACCOUNT
			bankAccounts.add(new BankAccount(bankClients.get(3),"pass4")); //LUIZA PAIXAO ACCOUNT
			
			//initializes GUI (JFrame) && makes it visible
			Window  mainWindow = new Window(bankClients,bankAccounts);
			mainWindow.setVisible(true);
			
			
		}catch(ParseException pEx) {
			System.out.println("parse exception!");
		}
	}
}
















