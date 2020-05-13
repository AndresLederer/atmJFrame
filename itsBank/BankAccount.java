package itsBank;

import java.util.*;

public class BankAccount {
	//atributes
	private final Client accountClient;
	private final UUID accountUuid;
	private double balance;
	private String password;
	
	//setters
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	//getters
	public Client getAccountClient(){
		return accountClient;
	}
	private UUID getAccountUuid() {
		return accountUuid;
	}
	public double getBalance() {
		return balance;
	}
	public String getPassword() {
		return password;
	}
	
	//constructor
	public BankAccount(Client accountClient, String password) {
		this.accountClient = accountClient; //account owner
		this.accountUuid = UUID.randomUUID(); //each new BankAccount gets a new random "Universally Unique Identification"
		setBalance(0); //inicial balance is $0
		setPassword(password);
	}
	
	//methods
	//returns the account uuid into a String
	private String getAccountUuidString() {
		String uuid = getAccountUuid().toString();
		return uuid;
	}
	
	//deposit incoming money
	public void depositMoney(double incoming) {
		setBalance(balance + incoming);
	}
	
	//withdraw money from this account
	public void withdrawMoney(double outcoming) {
		if(checkWithdraw(outcoming)) {
			setBalance(balance - outcoming);
		}
	}
	
	//check if there's enough money to be withdrawed
	private boolean checkWithdraw(double outcomingMoney) {
		boolean check = false;
		if(outcomingMoney <= balance) {
			check = true;
		}
		return check;
	}
	
	//toString() method @Override
	@Override
	public String toString() {
		String string = String.format("[Account UUID]: %s\n[Account balance]: $%.2f\n[Password}: %s\n[Account owner]: \n%s",getAccountUuidString(),getBalance(),getPassword(),getAccountClient().toString());
		return string;
	}
	
	
	
	
	
	
	
	
	
	
	
}




















