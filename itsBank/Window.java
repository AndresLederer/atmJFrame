package itsBank;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame{
	//atributes
	private Container contentPane;
	
	private static ArrayList<BankAccount> accounts;
	private static ArrayList<Client> clients;
	
	private Font titleFont = new Font("arial",3,25);
//	private Font tipFont = new Font("arial",2,12);
	private Font inputFont = new Font("arial",2,12);
	private Font warningFont = new Font("arial",1,10);
	private Font greetFont = new Font("arial",3,12);
	
	private JPanel indexPanel; //index JPanel
	private JTextField clientId; //only in indexPanel
	private JPasswordField clientPass; //only in indexPanel
	
	private JPanel accountPanel; //account JPanel
	private Client user; // client using the sftw
	private BankAccount userAccount; //BankAccount of user
	
	//constructor
	public Window(ArrayList<Client> bankClients,ArrayList<BankAccount> bankAccounts) {
		//imports clients and bank accounts form main()
		clients = bankClients;
		accounts = bankAccounts;
		
		//JFrame atributes
		setTitle("ITS Bank");
		setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		contentPane = getContentPane();
		
		//adds JPanel to JFrame
		loadIndexPanel();
	}
	
	//INDEX PANEL && COMPONENTS
	
	private void loadIndexPanel() {
		indexPanel = new JPanel(); 	//initializes index panel for GUI
		contentPane.add(indexPanel);  //sticks JPanel into JFrame
		indexPanel.setVisible(true); //makes panel visible
		indexPanel.setLayout(null); //frees panel layout
		
		//loads all components for index JPanel
		loadIndexJLabels();
		loadIndexJTextFields();
		loadIndexJButtons();
	}
	
	private void loadIndexJLabels() {
		JLabel indexTitle = new JLabel("ITS Bank - We make it happend");
		JLabel idRequest = new JLabel("Client personal ID");
		JLabel passRequest = new JLabel("Account Password");
		
		indexTitle.setBounds(200,100,400,50);
		indexTitle.setFont(titleFont);
		indexTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		idRequest.setBounds(300,198,150,10);
		idRequest.setFont(warningFont);
		idRequest.setForeground(Color.black);
		
		passRequest.setBounds(300,253,150,10);
		passRequest.setFont(warningFont);
		passRequest.setForeground(Color.black);
		
		indexPanel.add(indexTitle);
		indexPanel.add(idRequest);
		indexPanel.add(passRequest);
	}
	
	private void loadIndexJTextFields() {
		clientId = new JTextField();
		clientPass = new JPasswordField();
		
		clientId.setFont(inputFont);
		clientId.setForeground(Color.black);
		clientId.setBounds(300,210,200,20);
		
		clientPass.setFont(inputFont);
		clientPass.setForeground(Color.black);
		clientPass.setEchoChar('*');
		clientPass.setBounds(300,265,200,20);
		
		indexPanel.add(clientId);
		indexPanel.add(clientPass);
		
//		//clears the text fields when focused & shows the tip massage if the fields are empty
//		//On cliend it text field
//		FocusListener focusOnId = new FocusListener() {
//			@Override
//			public void focusGained(FocusEvent e) {
//				if(clientId.getText().equals("Client personal ID")) {
//					clientId.setText("");
//					clientId.setFont(inputFont);
//					clientId.setForeground(Color.black);
//				}
//			}
//			@Override
//			public void focusLost(FocusEvent e) {
//				if(clientId.getText().equals("")) {
//					clientId.setFont(tipFont);
//					clientId.setForeground(Color.gray);
//					clientId.setText("Client personal ID");
//				}
//			}
//			
//		};
//		//on client password account text field
//		FocusListener focusOnPass = new FocusListener() {
//			@Override
//			public void focusGained(FocusEvent e) {
//				if(clientPass.getText().equals("Account password")) {
//					clientPass.setText("");
//					clientPass.setFont(inputFont);
//					clientPass.setForeground(Color.black);
//				}
//			}
//			@Override
//			public void focusLost(FocusEvent e) {
//				if(clientPass.getText().equals("")) {
//					clientPass.setFont(tipFont);
//					clientPass.setForeground(Color.gray);
//					clientPass.setText("Account password");
//				}
//			}
//		};
//		clientId.addFocusListener(focusOnId);
//		clientPass.addFocusListener(focusOnPass);
//		
//		//shows * when clientPass (JTextField) is typed
//		KeyListener hidePassListener = new KeyListener() {
//			@Override
//			public void keyTyped(KeyEvent e) {
//				// TODO Auto-generated method stub
//			}
//			@Override
//			public void keyPressed(KeyEvent e) {
//				// TODO Auto-generated method stub
//			}
//			@Override
//			public void keyReleased(KeyEvent e) {
//				// TODO Auto-generated method stub
//			}
//		};
		
	}
	
	private void loadIndexJButtons() {
		JButton goToMyAccountBtn = new JButton("Go to my account");
		JButton createAccountBtn = new JButton("New account");
		JButton contactUsBtn = new JButton("Contact us");
		
		goToMyAccountBtn.setBounds(325,315,150,20);
		createAccountBtn.setBounds(265,355,125,20);
		contactUsBtn.setBounds(410,355,125,20);
		
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
		accountPassWar.setBounds(300,285,150,10);
		accountPassWar.setFont(warningFont);
		accountPassWar.setForeground(Color.red);
		indexPanel.add(accountPassWar);
		//Client id and password DO NOT match any bank account warning JLabel
		JLabel notAccountFound1 = new JLabel();
		JLabel notAccountFound2 = new JLabel();
		notAccountFound1.setBounds(200,435,400,20);
		notAccountFound2.setBounds(200,460,400,20);
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
				//reads JtextFiled inputs
				String clientPersonalId = clientId.getText();
				//.getPassword() returns a char array
				char [] clientPassChars = clientPass.getPassword();
				String clientPassAccount = new String(clientPassChars);
				
				if(clientPersonalId.equals("")) {
					clientIdWar.setText("Please fill in the blank");
				}else {
					clientIdWar.setText("");
					if(clientPassAccount.equals("")) {
						accountPassWar.setText("Please fill in the blank");
					}else {
						// if there's at least 1 clientID and password matches 
						if(checkPassword(clientPersonalId,clientPassAccount)) {
							//index panel not visible any more
							indexPanel.setVisible(false);
							//<<< go to AccountPanel >>>
							loadAccountPanel(clientPersonalId,clientPassAccount);
						}else { //Client id and password don't match any bank account
							clientId.setText("");
							clientPass.setText("");
							clientIdWar.setText("");
							accountPassWar.setText("");
							
							notAccountFound1.setText("The personal ID and password logged in do not match any bank account.");
							notAccountFound2.setText("Please check the input and try again or create a new bank account.");
						}
							
					}
					
				}
			}
		};
		goToMyAccountBtn.addActionListener(goToMyAccAction);
	}
	
	//checks client personal ID && password for their account -- returns the number of clientID and password matches 
	private static boolean checkPassword(String personalId, String password) {
		boolean matches = false;
		for(BankAccount b : accounts) {
			if(b.getAccountClient().getPersonalId().equals(personalId) && b.getPassword().equals(password)) {
				matches = true;
			}
		}
		return matches;
	}

	
	
	
	//ACCOUNT PANEL && COMPONENTS
	
	private void loadAccountPanel(String userId,String pass) {
		accountPanel = new JPanel(); //initialize account panel for GUI
		contentPane.add(accountPanel); //sticks account JPanel into JFram
		accountPanel.setLayout(null); //frees panel layout
		
		//gets the current BankAccount and Client (user)
		userAccount = getUserAccount(userId,pass);
		user = userAccount.getAccountClient();
		
		//loads all components for account JPanel
		loadAccountJLabels();
		loadAccountJButtons();
	}
	
	//looks for a client's bank account and returns it
	private BankAccount getUserAccount(String userID,String pass) {
		BankAccount userAccount = null;
		for(BankAccount ba : accounts) {
			if(ba.getAccountClient().getPersonalId().equals(userID) && ba.getPassword().equals(pass)) {
				userAccount = ba;
			}
		}
		return userAccount;
	}
	
	
	
	//ACCOUNT PANEL'S COMPONENTS
	
	//loads JLabel (txt) into the account panel
	private void loadAccountJLabels(){
		//user title
		JLabel accountTitle = new JLabel("Hi, "+user.getName()+"!");
		accountTitle.setBounds(200,80,400,50);
		accountTitle.setHorizontalAlignment(SwingConstants.CENTER);
		accountTitle.setFont(titleFont);
		
		//possible user's greets
		ArrayList<String> greets = new ArrayList<String>();
		greets.add("It's great to have you on board");
		greets.add("Thanks for choosing us");
		greets.add("Great to see you again");
		greets.add("What's new?");
		greets.add("Looking good");
		
		int randomInt = (int) getRandomIntegerBetweenRange(0,greets.size()-1);
		
		//user greet
		JLabel accountGreet = new JLabel(greets.get(randomInt));
		accountGreet.setBounds(250,160,300,20);
		accountGreet.setHorizontalAlignment(SwingConstants.CENTER);
		accountGreet.setFont(greetFont);

		accountPanel.add(accountTitle);
		accountPanel.add(accountGreet);
	}
	
	//return a random int between the indicated range
	private double getRandomIntegerBetweenRange(double min, double max){
	    double x = (int)(Math.random()*((max-min)+1))+min;
	    return x;
	}
	
	//loads JButtons into the account panel
	private void loadAccountJButtons() {
		JButton depositBtn = new JButton("Make deposit");
		JButton balanceBtn = new JButton("Check balance");
		JButton withdrawBtn = new JButton("Withdraw");
		JButton exitBtn = new JButton("Exit");
		
		//buttons position
		depositBtn.setBounds(80,210,200,20);
		balanceBtn.setBounds(300,210,200,20);
		withdrawBtn.setBounds(520,210,200,20);
		exitBtn.setBounds(300,530,200,20);
		
		//let's add all buttons
		accountPanel.add(balanceBtn);
		accountPanel.add(withdrawBtn);
		accountPanel.add(depositBtn);
		accountPanel.add(exitBtn);
		
		//buttons action listeners
		
	}
	
}










































