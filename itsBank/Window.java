package itsBank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.*;

public class Window extends JFrame{
	//atributes
	private static ArrayList<BankAccount> accounts;
	private static ArrayList<Client> clients;
	
	private Font titleFont = new Font("arial",3,25);
	private Font tipFont = new Font("arial",2,12);
	private Font inputFont = new Font("arial",0,12);
	private Font warningFont = new Font("arial",1,10);
	private JPanel indexPanel;
	private JTextField clientId; //only in indexPanel
	private JTextField clientPass; //only in indexPanel
	
	//constructor
	public Window(ArrayList<Client> bankClients,ArrayList<BankAccount> bankAccounts) {
		//imports clients and bank accounts form main()
		clients = bankClients;
		accounts = bankAccounts;
		
		setTitle("ITS Bank");
		setSize(800,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		//adds JPanel to JFrame
		loadIndexPanel();
	}
	
	private void loadIndexPanel() {
		indexPanel = new JPanel(); 	//initializes index panel for GUI
		this.getContentPane().add(indexPanel);  //sticks JPanel into JFrame
		indexPanel.setLayout(null); 
		
		//loads all components for index JPanel (JLable(txt)//JButtons)
		loadIndexJLabels();
		loadIndexJTextFields();
		loadIndexJButtons();
	}
	
	private void loadIndexJLabels() {
		JLabel indexTitle = new JLabel("ITS Bank - We make it happend");
		indexTitle.setBounds(200,100,400,50);
		indexTitle.setFont(titleFont);
		indexTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		indexPanel.add(indexTitle);
	}
	
	private void loadIndexJTextFields() {
		clientId = new JTextField("Client personal ID");
		clientPass = new JTextField("Account password");
		
		clientId.setFont(tipFont);
		clientId.setForeground(Color.gray);
		clientId.setBounds(300,210,200,20);
		
		clientPass.setFont(tipFont);
		clientPass.setForeground(Color.gray);
		clientPass.setBounds(300,250,200,20);
		
		indexPanel.add(clientId);
		indexPanel.add(clientPass);
		
		//clears the text fields when focused & shows the tip massage if the fields are empty
		FocusListener focusOnId = new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if(clientId.getText().equals("Client personal ID")) {
					clientId.setText("");
					clientId.setFont(inputFont);
					clientId.setForeground(Color.black);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(clientId.getText().equals("")) {
					clientId.setFont(tipFont);
					clientId.setForeground(Color.gray);
					clientId.setText("Client personal ID");
				}
			}
			
		};
		FocusListener focusOnPass = new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if(clientPass.getText().equals("Account password")) {
					clientPass.setText("");
					clientPass.setFont(inputFont);
					clientPass.setForeground(Color.black);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(clientPass.getText().equals("")) {
					clientPass.setFont(tipFont);
					clientPass.setForeground(Color.gray);
					clientPass.setText("Account password");
				}
			}
		};
		clientId.addFocusListener(focusOnId);
		clientPass.addFocusListener(focusOnPass);
		
		//shows * when clientPass (JTextField) is typed
		
	}
	
	private void loadIndexJButtons() {
		JButton goToMyAccountBtn = new JButton("Go to my account");
		JButton createAccountBtn = new JButton("New account");
		JButton contactUsBtn = new JButton("Contact us");
		
		goToMyAccountBtn.setBounds(325,300,150,20);
		createAccountBtn.setBounds(265,340,125,20);
		contactUsBtn.setBounds(410,340,125,20);
		
		indexPanel.add(goToMyAccountBtn);
		indexPanel.add(createAccountBtn);
		indexPanel.add(contactUsBtn);
		
		//client ID warning JLabel
		JLabel clientIdWar = new JLabel();
		clientIdWar.setBounds(300,230,150,10);
		clientIdWar.setFont(warningFont);
		clientIdWar.setForeground(Color.red);
		indexPanel.add(clientIdWar);
		//account password warning JLabel
		JLabel accountPassWar = new JLabel();
		accountPassWar.setBounds(300,270,150,10);
		accountPassWar.setFont(warningFont);
		accountPassWar.setForeground(Color.red);
		indexPanel.add(accountPassWar);
		//Client id and password DO NOT match any bank account JLabel
		JLabel notAccountFound1 = new JLabel();
		JLabel notAccountFound2 = new JLabel();
		notAccountFound1.setBounds(200,420,400,20);
		notAccountFound2.setBounds(200,445,400,20);
		notAccountFound1.setFont(warningFont);
		notAccountFound2.setFont(warningFont);
		notAccountFound1.setForeground(Color.blue);
		notAccountFound2.setForeground(Color.blue);
		notAccountFound1.setHorizontalAlignment(SwingConstants.CENTER);
		notAccountFound2.setHorizontalAlignment(SwingConstants.CENTER);
		indexPanel.add(notAccountFound1);
		indexPanel.add(notAccountFound2);
		ActionListener goToMyAccAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String clientPersonalId = clientId.getText();
				String clientPassAccount = clientPass.getText();
				if(clientPersonalId.equals("") || clientPersonalId.equals("Client personal ID")) {
					clientIdWar.setText("Please fill in the blank");
				}else {
					if(clientPassAccount.equals("") || clientPassAccount.equals("Account password")) {
						accountPassWar.setText("Please fill in the blank");
					}else {
						//checks data
						if(checkPassword(clientPersonalId,clientPassAccount)) {
							//<<< go to next Panel >>>
							System.out.println("account found");
						}else { //Client id and password don't match any bank account
							clientId.setText("Client personal ID");
							clientId.setFont(tipFont);
							clientId.setForeground(Color.gray);
							
							clientPass.setText("Account password");
							clientPass.setFont(tipFont);
							clientPass.setForeground(Color.gray);
							
							notAccountFound1.setText("The personal ID and password logged in do not match any bank account.");
							notAccountFound2.setText("Please check the input and try again or create a new bank account.");
						}
							
					}
					
				}
			}
		};
		goToMyAccountBtn.addActionListener(goToMyAccAction);
	}

	//checks client personal ID && password for their account -- returns true if the password matches the client password account
	private static boolean checkPassword(String personalId, String password) {
		boolean access = false;
		for(BankAccount b : accounts) {
			if(b.getAccountClient().getPersonalId().equals(personalId) && b.getPassword().equals(password)) {
				access = true;
			}
		}
		return access;
	}
}
















