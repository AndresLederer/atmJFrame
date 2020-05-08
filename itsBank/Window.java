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
	private Font systemFont = new Font("arial",1,12);
	
	private JPanel indexPanel; //index JPanel
	private JTextField clientId; //only in indexPanel
	private JPasswordField clientPass; //only in indexPanel
	
	private JPanel accountPanel; //account JPanel
	private Client user; // client using the sftw
	private BankAccount userAccount; //BankAccount of user
	
	//DEPOSIT AUXILIARIES
	private JPanel depositPanel; //shows panel for deposits
	private JTextField depositTxtField; //user will input their deposit here
	
	//BALANCE AUXILIARIES
	private JPanel balancePanel; //shows panel for withdraw
	
	//WITHDRAW AUXILIARIES
	private JPanel withdrawPanel; //shows balance
	private JTextField withdrawTxtField;
	
	private JLabel userWarning; //used in deposit and withdraw panels 
	private JLabel userSuccess; //used in deposit,withdraw and balance panels
	
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
			}
		};
		depositBtn.addActionListener(depositActionListener);
		
		//balance actioon listener
		ActionListener balanceActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				depositPanel.setVisible(false);
				balancePanel.setVisible(true);
				withdrawPanel.setVisible(false);
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
			}
		};
		withdrawBtn.addActionListener(withdrawActionListener);
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
		
		userWarning = new JLabel("Check this field");
		userWarning.setFont(warningFont);
		userWarning.setForeground(Color.red);
		userWarning.setBounds(30,132,100,20);
		userWarning.setVisible(false);
		
		userSuccess = new JLabel("Done");
		userSuccess.setFont(warningFont);
		userSuccess.setForeground(Color.blue);
		userSuccess.setBounds(30,132,100,20);
		userSuccess.setVisible(false);
		
		depositPanel.add(depositTxtLabel);
		depositPanel.add(userWarning);
		depositPanel.add(userSuccess);
		
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
				userWarning.setVisible(false);
				userSuccess.setVisible(false);
				//the try-catch will get the NumberFormatException if user doesn't input a double value
				try {
					if(!checkEmptyTextField(depositTxtField)) {
						makeDeposit(depositTxtField.getText());
						depositTxtField.setText("");
						userSuccess.setVisible(true);
						System.out.println("success");
						System.out.println("new balance: "+userAccount.getBalance());
					}else {
						userWarning.setVisible(true);
					}
				}catch(NumberFormatException nEx) {
					depositTxtField.setText("");
					userWarning.setVisible(true);
					System.out.println("number format exception");
				}
			}
		};
		depositBtn.addActionListener(depositBtnListener);
	}
	
	//checks if a text field is empty -- FALSE 
	private boolean checkEmptyTextField(JTextField textField){
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
		balancePanel.setVisible(false);
		
		accountPanel.add(balancePanel);
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
		
		userWarning = new JLabel("Check this field");
		userWarning.setFont(warningFont);
		userWarning.setForeground(Color.red);
		userWarning.setBounds(30,132,100,20);
		userWarning.setVisible(false);
		
		userSuccess = new JLabel("Done");
		userSuccess.setFont(warningFont);
		userSuccess.setForeground(Color.blue);
		userSuccess.setBounds(30,132,100,20);
		userSuccess.setVisible(false);
		
		withdrawPanel.add(withdrawLabel);
		withdrawPanel.add(userWarning);
		withdrawPanel.add(userSuccess);
	}
	
	private void loadWithdrawTextField() {
		withdrawTxtField = new JTextField(); //initializes withdraw text field => user'll input wuthdrawment here
		withdrawTxtField.setBounds(30,110,360,20);
		withdrawTxtField.setFont(inputFont);
		
		depositPanel.add(withdrawTxtField);
	}
	
	private void loadWithdrawButton() {
		JButton withdrawButton = new JButton("Withdraw");
		
	}
}










































