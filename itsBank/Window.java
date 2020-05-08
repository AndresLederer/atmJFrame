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
	
	//Fonts
	private Font titleFont = new Font("arial",3,25);
//	private Font tipFont = new Font("arial",2,12);
	private Font inputFont = new Font("arial",2,12);
	private Font warningFont = new Font("arial",1,10);
	private Font greetFont = new Font("arial",3,12);
	private Font systemFont = new Font("arial",1,12);
	
	//INDEX PANEL
	private JPanel indexPanel; //index JPanel
	private JTextField clientId; //only in indexPanel
	private JPasswordField clientPass; //only in indexPanel
	
	//ACCOUNT PANEL
	private JPanel accountPanel; //account JPanel
	private Client user; // client using the sftw
	private BankAccount userAccount; //BankAccount of user
	
	//DEPOSIT AUXILIARIES
	private JPanel depositPanel; //shows panel for deposits
	private JTextField depositTxtField; //user will input their deposit here
	private JLabel depositUserWarning;
	private JLabel depositUserSuccess;
	
	//BALANCE AUXILIARIES
	private JPanel balancePanel; //shows panel for withdraw
	private JLabel balanceDouble;
	//WITHDRAW AUXILIARIES
	private JPanel withdrawPanel; //shows balance
	private JTextField withdrawTxtField;
	private JLabel withdrawUserWarning; //used in deposit and withdraw panels 
	private JLabel withdrawUserSuccess; //used in deposit,withdraw and balance panels
	private JLabel withdrawBalanceWarning;
	
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
		
		//the next panel will only be dislplayed when btn.actionListener happens
		//(initially with setVisble(false) => the event will change this to "true"
		loadDepositPanel();
		loadBalancePanel();
		loadWithdrawPanel();
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
		accountPanel.add(depositBtn);
		accountPanel.add(balanceBtn);
		accountPanel.add(withdrawBtn);
		accountPanel.add(exitBtn);
		
		//buttons action listeners

		//deposit ActionListener
		ActionListener depositActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				balancePanel.setVisible(false);
				withdrawPanel.setVisible(false);
				depositPanel.setVisible(true);
				clearAllUserMassages();
			}
		};
		depositBtn.addActionListener(depositActionListener);
		
		//balance actioon listener
		ActionListener balanceActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				depositPanel.setVisible(false);
				withdrawPanel.setVisible(false);
				balancePanel.setVisible(true);
				balanceDouble.setText(String.format("$ %.2f",userAccount.getBalance()));
				clearAllUserMassages();
			}
		};
		balanceBtn.addActionListener(balanceActionListener);
		
		//withdraw action listener
		ActionListener withdrawActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				depositPanel.setVisible(false);
				balancePanel.setVisible(false);
				withdrawPanel.setVisible(true);
				clearAllUserMassages();
			}
		};
		withdrawBtn.addActionListener(withdrawActionListener);
		
		ActionListener exitActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				depositPanel.setVisible(false);
				balancePanel.setVisible(false);
				withdrawPanel.setVisible(false);
				accountPanel.setVisible(false);
				indexPanel.setVisible(true);
				clientId.setText("");
				clientPass.setText("");
				clearAllUserMassages();
			}
		};
		exitBtn.addActionListener(exitActionListener);
	}
	
	
	
// DEPOSIT , BALANCE & WITHDRAW PANELS + COMPONENTS
	
	//deposit panel + components
	private void loadDepositPanel() {
		depositPanel = new JPanel();
		depositPanel.setBounds(80,260,640,240);
		depositPanel.setOpaque(true);
		depositPanel.setBackground(Color.white);
		depositPanel.setLayout(null);
		depositPanel.setVisible(false);
		
		//load all components
		//txt label for deposit panel
		loadDepositTxtLabel();
		//txt field for deposit panel (user input)
		loadDepositTxtField();
		//button for deposit panel (action listener)
		loadDepositButton();
		
		accountPanel.add(depositPanel);
	}
	
	private void loadDepositTxtLabel() {
		JLabel depositTxtLabel = new JLabel("Deposit");
		depositTxtLabel.setBounds(30,88,100,20);
		depositTxtLabel.setFont(systemFont);
		
		depositUserWarning = new JLabel("Check this field");
		depositUserWarning.setFont(warningFont);
		depositUserWarning.setForeground(Color.red);
		depositUserWarning.setBounds(30,132,100,20);
		depositUserWarning.setVisible(false);
		
		depositUserSuccess = new JLabel("Done");
		depositUserSuccess.setFont(warningFont);
		depositUserSuccess.setForeground(Color.blue);
		depositUserSuccess.setBounds(30,132,100,20);
		depositUserSuccess.setVisible(false);
		
		depositPanel.add(depositTxtLabel);
		depositPanel.add(depositUserWarning);
		depositPanel.add(depositUserSuccess);
		
	}

	private void loadDepositTxtField() {
		depositTxtField = new JTextField(); //initializes deposit text field => user'll input deposit here
		depositTxtField.setBounds(30,110,360,20);
		depositTxtField.setFont(inputFont);
		
		depositPanel.add(depositTxtField);
	}
	
	private void loadDepositButton() {
		JButton depositBtn = new JButton("Go");
		depositBtn.setBounds(410,110,200,20);
		
		depositPanel.add(depositBtn);
		
		//depositBtn ActionListener development
		ActionListener depositBtnListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				userWarning.setVisible(false);
//				userSuccess.setVisible(false);
				//the try-catch will get the NumberFormatException if user doesn't input a double value
				try {
					if(!emptyTextField(depositTxtField)) {
						makeDeposit(depositTxtField.getText());
						depositTxtField.setText("");
						depositUserWarning.setVisible(false);
						depositUserSuccess.setVisible(true);
//						System.out.println("success");
//						System.out.println("new balance: "+userAccount.getBalance());
					}else {
						depositUserSuccess.setVisible(false);
						depositUserWarning.setVisible(true);
					}
				}catch(NumberFormatException nEx) {
					depositTxtField.setText("");
					depositUserSuccess.setVisible(false);
					depositUserWarning.setVisible(true);
//					System.out.println("number format exception");
				}
			}
		};
		depositBtn.addActionListener(depositBtnListener);
	}
	
	//checks if a text field is empty -- FALSE 
	private boolean emptyTextField(JTextField textField){
		boolean check = true;
		if(!textField.getText().equals(""))
			//if the text field has any String in it=> returns false
			check = false;
		return check;
	}
	
	//sets new balance = old balance + deposit
	//may throw a NumberFormatException when converting the String into a double
	private void makeDeposit(String depositInput) throws NumberFormatException {
		double deposit = Double.parseDouble(depositInput);
		userAccount.setBalance(userAccount.getBalance() + deposit);
	}
	
	
	
	
	
	
	//balance panel + components
	private void loadBalancePanel() {
		balancePanel = new JPanel();
		balancePanel.setBounds(80,260,640,240);
		balancePanel.setOpaque(true);
		balancePanel.setBackground(Color.white);
		balancePanel.setLayout(null);
		balancePanel.setVisible(false);
		
		
		//load all components
		//loads text JLabels
		loadBalanceTextLabels();
		
		accountPanel.add(balancePanel);
	}
	
	private void loadBalanceTextLabels() {
		 JLabel balanceTitle = new JLabel("Account Balance");
		 balanceDouble = new JLabel(String.format("$ %.2f",userAccount.getBalance()));
		 
		 balanceTitle.setBounds(100,20,200,70);
		 balanceDouble.setBounds(100,120,200,100);
		 balanceTitle.setFont(new Font("arial",Font.BOLD,20));
		 balanceDouble.setFont(new Font("arial",Font.BOLD,30));
		 balanceTitle.setForeground(Color.white);
		 balanceDouble.setForeground(Color.white);
		 balanceTitle.setOpaque(true);
		 balanceTitle.setBackground(Color.gray);
		 balanceDouble.setOpaque(true);
		 balanceDouble.setBackground(Color.gray);
		 balanceTitle.setHorizontalAlignment(SwingConstants.CENTER);
		 balanceDouble.setHorizontalAlignment(SwingConstants.CENTER);
		 
		 
		 JLabel clientTitle = new JLabel("Account Owner");
		 JLabel clientFullName = new JLabel(user.getLastName()+", "+user.getName());
		 JLabel clientAddress = new JLabel(user.getAddress());
		 JLabel clientId = new JLabel(user.getPersonalId());
		 JLabel clientBirth = new JLabel(user.getBirthString());
		 
		 
		 balancePanel.add(balanceTitle);
		 balancePanel.add(balanceDouble);
//		 balancePanel.add(clientTitle);
//		 balancePanel.add(clientFullName);
//		 balancePanel.add(clientAddress);
//		 balancePanel.add(clientId);
//		 balancePanel.add(clientBirth);
	}
	
	
	
	
	//withdraw panel + components
	private void loadWithdrawPanel() {
		withdrawPanel = new JPanel();
		withdrawPanel.setBounds(80,260,640,240);
		withdrawPanel.setOpaque(true);
		withdrawPanel.setBackground(Color.white);
		withdrawPanel.setLayout(null);
		withdrawPanel.setVisible(false);
		
		//load all components
		//loads text labels
		loadWithdrawTextLabels();
		//loads withdraw text field - user will input a value here
		loadWithdrawTextField();
		//loads withdraw button + ActionListener for it
		loadWithdrawButton();
		
		accountPanel.add(withdrawPanel);
	}
	
	private void loadWithdrawTextLabels() {
		JLabel withdrawLabel = new JLabel("Withdraw");
		withdrawLabel.setBounds(30,88,100,20);
		withdrawLabel.setFont(systemFont);
		
		withdrawUserWarning = new JLabel("Check this field");
		withdrawUserWarning.setFont(warningFont);
		withdrawUserWarning.setForeground(Color.red);
		withdrawUserWarning.setBounds(30,132,100,20);
		withdrawUserWarning.setVisible(false);
		
		withdrawUserSuccess = new JLabel("Done");
		withdrawUserSuccess.setFont(warningFont);
		withdrawUserSuccess.setForeground(Color.blue);
		withdrawUserSuccess.setBounds(30,132,100,20);
		withdrawUserSuccess.setVisible(false);
		
		withdrawBalanceWarning = new JLabel("Not enough money in your account");
		withdrawBalanceWarning.setFont(warningFont);
		withdrawBalanceWarning.setForeground(Color.red);
		withdrawBalanceWarning.setBounds(30,132,200,20);
		withdrawBalanceWarning.setVisible(false);
		
		withdrawPanel.add(withdrawLabel);
		withdrawPanel.add(withdrawUserWarning);
		withdrawPanel.add(withdrawUserSuccess);
		withdrawPanel.add(withdrawBalanceWarning);
	}
	
	private void loadWithdrawTextField() {
		withdrawTxtField = new JTextField(); //initializes withdraw text field => user'll input wuthdrawment here
		withdrawTxtField.setBounds(30,110,360,20);
		withdrawTxtField.setFont(inputFont);
		
		withdrawPanel.add(withdrawTxtField);
	}
	
	private void loadWithdrawButton() {
		JButton withdrawBtn = new JButton("Withdraw");
		withdrawBtn.setBounds(410,110,200,20);
		
		withdrawPanel.add(withdrawBtn);
		
		ActionListener withdrawBtnListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				try {
					//if there's sth written as the withdraw input
					if(!emptyTextField(withdrawTxtField)) {
						//if the withdraw is successful => shows a success massage
						if(makeWithdraw(withdrawTxtField.getText())) {
							withdrawUserWarning.setVisible(false);
							withdrawBalanceWarning.setVisible(false);
							withdrawUserSuccess.setVisible(true);
						}else { //if there's not enough money to make the withdraw => shows a warning
							withdrawUserWarning.setVisible(false);
							withdrawUserSuccess.setVisible(false);
							withdrawBalanceWarning.setVisible(true);
						}
						withdrawTxtField.setText("");
					}else { //if the withdraw input is empty
						withdrawUserSuccess.setVisible(false);
						withdrawBalanceWarning.setVisible(false);
						withdrawUserWarning.setVisible(true);
					}
				}catch (NumberFormatException nEx) {
					withdrawTxtField.setText("");
					withdrawBalanceWarning.setVisible(false);
					withdrawUserSuccess.setVisible(false);
					withdrawUserWarning.setVisible(true);
				}
			}
		};
		withdrawBtn.addActionListener(withdrawBtnListener);
	}
	
	//checks if there's enough money into the account. If so, goes on and sets a new account balance
	private boolean makeWithdraw(String withdrawInput) throws NumberFormatException {
		boolean success = false;
		double withdraw = Double.parseDouble(withdrawInput);
		if(checkWithdrawAmount(withdraw)) {
			userAccount.setBalance(userAccount.getBalance() - withdraw);
			success = true;
		}
		return success;
	}
	
	//checks if there's enough money into the account for the withdraw input
	private boolean checkWithdrawAmount(double withdrawInput) {
		boolean check = false;
		if(userAccount.getBalance() >= withdrawInput)
			check = true;
		return check;
	}
	
	
	
	
	
	
	//clears out all massages for the user
	private void clearAllUserMassages() {
		depositUserSuccess.setVisible(false);
		depositUserWarning.setVisible(false);
		withdrawUserSuccess.setVisible(false);
		withdrawUserWarning.setVisible(false);
		withdrawBalanceWarning.setVisible(false);
	}
}










































