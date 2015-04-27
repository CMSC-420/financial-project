/**
 * As its name suggests, this class manages the GUI.
 * This will act as the staging point for the entire project.
 *
 * The objects of all accounts and reports will be maintained in this class
 * to make it easier to push the information to the interface.
 */


import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.*;
import java.awt.FileDialog;
import java.io.*;
import java.util.*;
public class GUI {
	
	//Basics for every GUI 
	protected static JFrame frame;
	protected static JPanel panel;
    
    protected static int windowHeight; // current height of the window
    protected static int windowWidth; // current width of the window
    protected static GroupLayout groupLayout; // the layout for the components
    
	protected static JButton act_mgmt, reports, record_transaction, button_1, button_2; // Buttons
	protected static JComboBox view_acct; // User selectable drop down menu to select the account they wish to view
 
	// scrollpane for table implementation --> give the table a scrollbar after the limit for viewable entries has been met
	protected static JScrollPane scrollPane;
	protected static JTable table;
    protected static MyTableModel tableModel;
    protected static ListSelectionModel listModel;
    
    // list of all accounts
    protected static ArrayList<Account> accounts;
    
	//list of all transactions
	protected static ArrayList<Transaction> trans;
	
	//list of all report data --< of type string for now
	protected static ArrayList<String> reports_list = new ArrayList<String>();
	//String to grab the selected content for the report
	static String report_list="";
	
    // the currently selected tab
    // 0 = Account, 1 = Reports, 2 = Transactions
    protected static int currTab = 0;
    protected static Account currAccount; // the currently selected account
    
	//label to contain he sum of the all balances
	static JLabel sum_lab = new JLabel("0");
	// variable to contain the sum of all balances for all accounts
	static int sum_bal=0;
	static int sum_tran=0;
	

 
    
	public static void main(String[] args) throws IOException {
		
        // create the array list that holds the accounts
        accounts = new ArrayList<Account>();
        
        IO.initAccount(accounts);
        
        if(!accounts.isEmpty()){
            currAccount = accounts.get(0);
        }
		
        // Defines and sets up the Frame and Panel
		// loads the GUI
		GUI();
		

	} // main

    
    
    
    // setup the main window
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void GUI(){
	
		frame = new JFrame("Financial Tool"); // label the window
		frame.setSize(1024,768); // default frame size
		
		//creates a table for user date entry
		table = new JTable();
        tableModel = new MyTableModel();
		//give table the ability to select multiple rows simultaneously
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setFillsViewportHeight(true); // the table fills out the JScrollPane
        table.setAutoResizeMode(1); // table till auto-resize
        table.setModel(tableModel);
		
		
		//scrollpane --> gives the table a scrollbar when the the table entries go outside the space defined for the table on the Frame
		scrollPane = new JScrollPane(table);
		
		/**
		 * creates a Drop Down Select Menu to choose between different categories:
		 *    Categories that will be implemented later in code:
		 *    	1) Account Management
		 * 		2) Reports
		 * 		3) Record Transactions
		 */
		view_acct = new JComboBox<String>();
        for(Account a : accounts) // add accounts to dropdown
			view_acct.addItem(a.getName());
            
        // keep track of the currently selected account
        view_acct.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int index = view_acct.getSelectedIndex();
                
                if(index >= 0){
                    currAccount = accounts.get(index);
                    if(currTab == 1)
                        initTableReports();
                    else if(currTab == 2)
                        initTableTransactions();
                }
            }
        });
        
        /**
         * makes the X in the titlebar close the program
         * DO NOT DELETE THIS AGAIN
         * Without it the window will close but the program will still be running.
         */
        frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
			
		});
		
		
		
		/**
		 * 
		 * 
		 * Below defines the GUI components for: 
		 * 
		 * User Selectable Buttons:
		 * 		1) Account Management
		 * 		2) Reports
		 * 		3) Record Transaction
		 * 
		 * 
		 * Drop down menu for the user to select the account they wish to view
		 *
		 */
		
		
		// account management
		act_mgmt =  new JButton("Accounts");
		act_mgmt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				initTableAccounts();
			}
		});
		
		
		//reports
		reports = new JButton("Reports");
		reports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initTableReports();
			}
		});
		
		
		//record transaction
		record_transaction = new JButton("Transactions");
		record_transaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initTableTransactions();
			}
		});
		
        
		// button 1
		button_1 = new JButton("Button 1");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				switch(currTab){
                    case 0: // this button will add an account
                        addAccountPopup();
                        break;
                    case 1: // reports
                        break;
                    case 2: // transactions
                        addTransactionPopup();
                        break;
                    default:
                        System.out.println("ERROR - GUI button_1 - invalid currTab");
                }
			}
		});
		
		
		// button 2
		button_2 = new JButton("Button 2");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                int row;
                
				switch(currTab){
                    case 0: // Accounts - delete button
                        row = table.getSelectedRow();
                        
                        if(row > -1){ // if something is selected
                            // confirm the user's choice to delete
                            int result = JOptionPane.showConfirmDialog(frame, // frame
                                            "Are you sure you want to delete this account?", // message
                                            "Comfirm Delete", // title
                                            JOptionPane.OK_CANCEL_OPTION); // options
                            
                            if(result == JOptionPane.OK_OPTION){ // user confirmed
                                Account acc = accounts.get(row);
                                accounts.remove(acc); // remove the selected account from the array list
                                view_acct.removeAllItems(); // clear the dropdown
                                // update the dropdown with the new array list
                                for(Account a : accounts) {
                                    view_acct.addItem(a.getName());
                                }
                                
                                // delete the transaction file
                                File transFile = new File(acc.getName() + ".txt");
                                transFile.delete();
                                
                                initTableAccounts(); // update the table
                                IO.updateAccountData(accounts); // update the accounts text file
                                
                                if(accounts.size() == 0)
                                    currAccount = null;
                            } else { // user canceled
                                // do nothing
                            }
                        }
                        break;
                    case 1: // Reports
                        break;
                    case 2: // Transactions
                        trans = currAccount.getTransactions();
                        row = table.getSelectedRow();
                        
                        if(row > -1){ // if something is selected
                            // confirm the user's choice to delete
                            int result = JOptionPane.showConfirmDialog(frame, // frame
                                            "Are you sure you want to delete this transaction?", // message
                                            "Comfirm Delete", // title
                                            JOptionPane.OK_CANCEL_OPTION); // options
                            
                            if(result == JOptionPane.OK_OPTION){ // user confirmed
                                Transaction t = trans.get(row);
                                trans.remove(t); // remove the selected transaction from the array list
                                
                                switch(t.getType()){
                                    case "Spending":
                                        currAccount.setBalance(currAccount.getBalance() + t.getAmount());
                                        break;
                                    case "Income":
                                        currAccount.setBalance(currAccount.getBalance() - t.getAmount());
                                        break;
                                    case "Transfer":
                                        deleteTransfer(t);
                                        break;
                                }
                                
                                initTableTransactions(); // update the table
                                IO.updateTranData(trans, currAccount); // update the text file
                            } else { // user canceled
                                // do nothing
                            }
                        }
                        break;
                    default:
                        System.out.println("\n\nERROR - GUI.button_2 - invalid currTab\n");
                }
			}
		});
        
        
        // These define the current height and width of the window.
        windowHeight = frame.getBounds().height;
        windowWidth = frame.getBounds().width;
		
		// setup the layout
        groupLayout = new GroupLayout(frame.getContentPane());
		makeLayout();
		
		
		
		/**
		 * 
		 * What ever you do, DO NOT DELETE the below line of code: This line sets the GUI FRAME and ALL SUB COMPONENTS to visible. 
		 * 
		 * Without this line, if you run the application, NOTHING will display and it will seem as if something is broken. 
		 * This is not the case, the application IS running it is simply not showing thus why you must set the visibility to TRUE.
		 *
         * Also makes the layout resize itself when the window is resized.
		 */
        frame.getContentPane().setLayout(groupLayout);
        frame.addComponentListener(new ResizeListener());
		frame.setVisible(true);
        initTableAccounts(); // show the account info in the table
		
	} // GUI
    
    
    
    
    // creates a popup for adding an account
    private static void addAccountPopup(){
        int result;
        String name;
        double balance;
        String type;
        boolean accExists, valid_input;
        
        // temporary panel for the JOptionPane
        JPanel dialog = new JPanel(new BorderLayout(5,5));
        // all of the labels for the JOptionPane
        JPanel labels = new JPanel(new GridLayout(0,1,2,2));
        // all of the input fields for the JOptionPane
        JPanel fields = new JPanel(new GridLayout(0,1,2,2));
        
        // setup the JOptionPane for adding an account
        labels.add(new JLabel("Account Name "));
        labels.add(new JLabel("Starting Balance ($)"));
        labels.add(new JLabel("Account Type"));
        dialog.add(labels, BorderLayout.WEST);
        
        JTextField accName = new JTextField();
        JTextField accBal = new JTextField();
        JComboBox accType = new JComboBox();
        accType.addItem("Checking");
        accType.addItem("Savings");
        accType.addItem("COD");
        accType.addItem("Credit Card");
        accType.addItem("Money Market");
        fields.add(accName);
        fields.add(accBal);
        fields.add(accType);
        dialog.add(fields, BorderLayout.CENTER);

        // prompt the user for basic account info
        result = JOptionPane.showConfirmDialog(frame, dialog,
                        "New Account", JOptionPane.OK_CANCEL_OPTION);
        
        
        if(result == JOptionPane.OK_OPTION){ // if the user clicked ok
            

            // index indicating what type of error has occurred
            int inputError = check_input_account(accName.getText(), accBal.getText());
            
            // keep trying until no errors or user cancels
            while(inputError > 0){
                if(inputError == 1){ // empty name field
                    JOptionPane.showMessageDialog(null, "The account must have a name!");
                    
                    result = JOptionPane.showConfirmDialog(frame, dialog,
                                    "New Account", JOptionPane.OK_CANCEL_OPTION);
                    
                    if(result == JOptionPane.OK_OPTION)
                        inputError = check_input_account(accName.getText(), accBal.getText());
                    else
                        break;
                    
                } 
                else if(inputError == 2){
                    accBal.setText("0.0");
                    inputError = 0;
                    break;
                }
                else if(inputError == 3){ // balance is not a number
                    JOptionPane.showMessageDialog(null, "Please enter a valid dollar amount!");
                    
                    result = JOptionPane.showConfirmDialog(frame, dialog,
                                    "New Account", JOptionPane.OK_CANCEL_OPTION);
                    
                    if(result == JOptionPane.OK_OPTION)
                        inputError = check_input_account(accName.getText(), accBal.getText());
                    else
                        break;
                }
            }
            
            if(inputError == 0){ // no errors
                name = accName.getText();
                balance = Double.parseDouble(accBal.getText());
                type = accType.getSelectedItem().toString();
                
                //check to see if account name already exists
                accExists = false;
                for(int i = 0; i < accounts.size(); i++){
                    
                	if((accounts.get(i).getName().toLowerCase().equals(name.toLowerCase()))
                			&&(accounts.get(i).getType().equals(type))){
                		accExists = true;
                	}
                }//for
                
                //add new account or show error message for dupe
                if(!accExists){
	                try{
                        Account acc = new Account();
                        acc.setBalance(balance);
                        acc.setName(name);
                        acc.setType(type);
                        
                        accounts.add(acc);
                        view_acct.addItem(name); // add new account to dropdown
                        initTableAccounts();
                        
                        if(currAccount == null)
                            currAccount = acc;
                        
	                } catch(NullPointerException e1){
	                    e1.printStackTrace();	
                    }
	                // write the new account to the file
	                IO.updateAccountData(accounts);
                }//if
				
                else{
                	JOptionPane.showMessageDialog(null, "Account already exists!");
                	
                	// try again
                    result = JOptionPane.showConfirmDialog(frame, dialog,
                                    "New Account", JOptionPane.OK_CANCEL_OPTION);
                                    
                    if(result == JOptionPane.OK_OPTION){
                        //check_input_account(accName.getText(), accBal.getText(), dialog, accName, accBal);
                    } else {
                        valid_input = false;
                    }
                }//else
            }
        }
        
    
    } // addAccountPopup
    
    
    
    
    // creates a popup for adding a transaction
    private static void addTransactionPopup(){
        int result;
		
		//Constructs a Date Object to pull the current date automatically
        Calendar current_date = Calendar.getInstance();
        int day = current_date.get(Calendar.DAY_OF_MONTH);
        int month = current_date.get(Calendar.MONTH)+1;
        int year = current_date.get(Calendar.YEAR);
		
        //current date string to pass to the panel	
        String curr_date = Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year);
        
        // temporary panel for the JOptionPane
        JPanel dialog = new JPanel(new BorderLayout(5,5));
        // all of the labels for the JOptionPane
        JPanel labels = new JPanel(new GridLayout(0,1,2,2));
        // all of the input fields for the JOptionPane
        JPanel fields = new JPanel(new GridLayout(0,1,2,2));
        
        // setup the JOptionPane for adding a transaction
        JLabel transTarget = new JLabel("Target Account");
        transTarget.setVisible(false); // initially invisible
        
        labels.add(new JLabel("Date"));
        labels.add(new JLabel("Payee"));
        labels.add(new JLabel("Account Type"));
        labels.add(transTarget); 
        labels.add(new JLabel("Category"));
        labels.add(new JLabel("Comments"));
        labels.add(new JLabel("Amount"));
        dialog.add(labels, BorderLayout.WEST);
        
        //JLabel transDate = new JLabel(curr_date);
        JTextField transDate = new JTextField(curr_date);
        JTextField transPayee = new JTextField();
        JComboBox transType = new JComboBox();
        JComboBox transAcc = new JComboBox(); // target account choices
        transAcc.setVisible(false); // initially invisible
        JTextField transCategory = new JTextField();
        JTextField transComments = new JTextField();
        JTextField transAmount = new JTextField();
        transType.addItem("Spending");
        transType.addItem("Income");
		transType.addItem("Transfer");
        
        for(Account a : accounts) // add existing accounts to the dropdown
            transAcc.addItem(a.getName());
            
        // show transAcc only if transaction type is "Transfer"
        transType.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int index = transType.getSelectedIndex();
                
                if(index == 2){
                    transAcc.setVisible(true);
                    transTarget.setVisible(true);
                } else {
                    transAcc.setVisible(false);
                    transTarget.setVisible(false);
                }
            }
        });
        
		fields.add(transDate);
        fields.add(transPayee);
        fields.add(transType);
        fields.add(transAcc);
        fields.add(transCategory);
        fields.add(transComments);
        fields.add(transAmount);
        dialog.add(fields, BorderLayout.CENTER);

        
        
        // prompt the user for basic account info
        result = JOptionPane.showConfirmDialog(frame, dialog,
                        "New Transaction", JOptionPane.OK_CANCEL_OPTION);
                        
		if(result == JOptionPane.OK_OPTION){ // if the user clicked OK
            
            int inputError = check_input_trans(transDate.getText(), transPayee.getText(), transCategory.getText(), transAmount.getText());
            
            // keep trying until no errors or user cancels
            while(inputError > 0){
                if(inputError == 1){ // empty payee field
                    JOptionPane.showMessageDialog(null, "The transaction must have a payee!");
                    
                    result = JOptionPane.showConfirmDialog(frame, dialog,
                                    "New Transaction", JOptionPane.OK_CANCEL_OPTION);
                    
                    if(result == JOptionPane.OK_OPTION)
                        inputError = check_input_trans(transDate.getText(), transPayee.getText(), transCategory.getText(), transAmount.getText());
                    else
                        break;
                    
                } 
                else if(inputError == 2){
                    JOptionPane.showMessageDialog(null, "The transaction must have a category!");
                    
                    result = JOptionPane.showConfirmDialog(frame, dialog,
                                    "New Transaction", JOptionPane.OK_CANCEL_OPTION);
                    
                    if(result == JOptionPane.OK_OPTION)
                        inputError = check_input_trans(transDate.getText(), transPayee.getText(), transCategory.getText(), transAmount.getText());
                    else
                        break;
                }
                else if(inputError == 3){
                    JOptionPane.showMessageDialog(null, "Invalid date format!");
                    
                    result = JOptionPane.showConfirmDialog(frame, dialog,
                                    "New Transaction", JOptionPane.OK_CANCEL_OPTION);
                    
                    if(result == JOptionPane.OK_OPTION)
                        inputError = check_input_trans(transDate.getText(), transPayee.getText(), transCategory.getText(), transAmount.getText());
                    else
                        break;
                }
                else if(inputError == 4){ // amount is not a number
                    JOptionPane.showMessageDialog(null, "Please enter a valid dollar amount!");
                    
                    result = JOptionPane.showConfirmDialog(frame, dialog,
                                    "New Transaction", JOptionPane.OK_CANCEL_OPTION);
                    
                    if(result == JOptionPane.OK_OPTION)
                        inputError = check_input_trans(transDate.getText(), transPayee.getText(), transCategory.getText(), transAmount.getText());
                    else
                        break;
                }
            }
            
            if(inputError == 0){
                // get the account info from the popup
                String date = transDate.getText();
                String payee = transPayee.getText();
                String cat = transCategory.getText();
                double amount = Double.parseDouble(transAmount.getText());
                String type = transType.getSelectedItem().toString();
                String comment = transComments.getText();
                
               
                Transaction transaction = new Transaction();
                transaction.setDate(date);
                transaction.setAmount(amount);
                transaction.setPayee(payee);
                transaction.setComments(comment);
                transaction.setCategory(cat);
                transaction.setDate(curr_date);
                transaction.setType(type);
                
                switch(type){
                    case "Spending":
                    
                        trans.add(transaction);
                        currAccount.setBalance(currAccount.getBalance() - amount);
                        break;
                    case "Income":
                    
                        trans.add(transaction);
                        currAccount.setBalance(currAccount.getBalance() + amount);
                        break;
                    case "Transfer":
                        Account target = accounts.get(transAcc.getSelectedIndex());
                        
                        if(target == currAccount){
                            JOptionPane.showMessageDialog(null, "Cannot transfer to itself!");
                        } else {
                            
                            trans.add(transaction);
                            currAccount.setBalance(currAccount.getBalance() - amount);
                            
                            target.setBalance(target.getBalance() + amount);
                            Transaction temp = new Transaction();
                            temp.setAmount(amount);
                            temp.setPayee(payee);
                            temp.setComments(comment);
                            temp.setCategory(cat);
                            temp.setDate(curr_date);
                            temp.setType(type);
                            target.addTransaction(temp);
                            IO.updateTranData(target.getTransactions(), target);
                        }
                        break;
                }
                
                initTableTransactions();
                
                // update files
                IO.updateAccountData(accounts); // this updates the accounts for the user
                IO.updateTranData(trans, currAccount); //this updates the transactions that have been taking place by the user
            }
        }
    } // addTransactionPopup
    
    
    
    
    // check input for errors
    private static int check_input_account(String name, String balance){
		
        // index that indicates what type of error occurred
        int valid_input = 0;
        
		if(name.equals("")){ // empty name field
			valid_input = 1;
		} 
        
        else {
            try{ // try to parse the accBal field
                if(balance.equals("")){
                    valid_input = 2;
                } else {
                    Double.parseDouble(balance);
                }
            } catch(Exception e){
                valid_input = 3;
            }
        }
        
		return valid_input;
	} // check_input_account
    
    
    
    
    // check input for errors
    private static int check_input_trans(String date, String payee, String category, String amount){
        int valid_input = 0;
        
        if(payee.equals("")){
            valid_input = 1;
        }
        else if(category.equals("")){
            valid_input = 2;
        }
        else if(!transPopCheckDate(date)){
            valid_input = 3;
        }
        else{
            try{
                Double.parseDouble(amount);
            } catch(Exception e){
                valid_input = 4;
            }
        }
        
        return valid_input;
    } // check_input_trans
    
    
    
    
    // setup the table for viewing transactions for the current account
    private static void initTableTransactions(){
        if(currAccount != null){
            currTab = 2;
            
            trans = currAccount.getTransactions();
            
            // display account balance at the bottom of the screen
            sum_lab.setText("Balance: $" + currAccount.getBalance());
            
            Transaction transaction = new Transaction();
            
            tableModel.setColumnCount(0);
            tableModel.setRowCount(0);
            tableModel.addColumn("Date");
            tableModel.addColumn("Payee");
            tableModel.addColumn("Type");
            tableModel.addColumn("Category");
            tableModel.addColumn("Comments");
            tableModel.addColumn("Amount ($)");
            
            // combobox to change the transaction type
            JComboBox transTypeCombo = new JComboBox();
            transTypeCombo.addItem("Spending");
            transTypeCombo.addItem("Income");
            
            // put the combobox in the middle column
            table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(transTypeCombo));
            
            // make the combobox change the account type when it is selected
            transTypeCombo.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    
                    int index = transTypeCombo.getSelectedIndex();
                    int row = table.getSelectedRow();
                    
                    if(index >= 0 && row >= 0){
                        Transaction t = trans.get(row);
                        String oldType;
                        
                        switch(index){
                            case 0:
                                oldType = t.getType(); // get the old type
                                
                                if(oldType.equals("Income")){ // if the old type was income
                                    currAccount.setBalance(currAccount.getBalance() - (2 * t.getAmount()));
                                    sum_lab.setText("Balance: $" + currAccount.getBalance());
                                }
                                t.setType("Spending");
                                break;
                            case 1:
                                oldType = t.getType(); // get the old type
                                
                                if(oldType.equals("Spending")){ // if the old type was spending
                                    currAccount.setBalance(currAccount.getBalance() + (2 * t.getAmount()));
                                    sum_lab.setText("Balance: $" + currAccount.getBalance());
                                }
                                t.setType("Income");
                                break;
                        }
                        
                        IO.updateAccountData(accounts);
                        IO.updateTranData(trans, currAccount);
                    }
                }
            });
        
            
            for(int i = 0; i < trans.size(); i++){
                transaction = trans.get(i);
                
                tableModel.addRow(new Object[]{
                    transaction.getDate(),
                    transaction.getPayee(),
                    transaction.getType(), 
                    transaction.getCategory(),
                    transaction.getComments(),
                    transaction.getAmount()
                });
            }
            
            sum_lab.setVisible(true);
            button_1.setText("New Transaction");
            button_2.setText("Delete Transaction");
            button_1.setVisible(true);
            button_2.setVisible(true);
            view_acct.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "You must create an account first!");
        }
    } // initTableTransactions
	
	
	
	
	// setup the table for viewing a spending analysis report
	private static void initTableReports(){
        if(currAccount != null){
            currTab = 1;
            
            // reset table
            tableModel.setColumnCount(0);
            tableModel.setRowCount(0);
            // create appropriate columns
            tableModel.addColumn("Category");
            tableModel.addColumn("Total");
            tableModel.addColumn("Percent");
            
            // transactions of the current account
            trans = currAccount.getTransactions();
            
            // I made this class to simplify the grouping of these three pieces of data
            class Report{
                public String category; // category of spending
                public double total; // total spent in this category
                public double percent; // percent compared to total spending
            } // report
            
            // one Report for each category
            ArrayList<Report> reports = new ArrayList<>();
            
            Transaction t; // the transaction currently being looked at
            boolean exists = false; // whether the category is already represented in reports
            int index = 0; // the index of the report (if the category already exists)
            double totalSpent = 0; // the total amount spent across all categories
            Report rep; // the report currently being looked at
            
            // update the reports arraylist with all necessary information
            for(int i = 0; i < trans.size(); i++){
                t = trans.get(i);
                
                if(t.getType().equals("Spending")){ // only count spending; ignore income and transfers
                    exists = false; // assume the category doesn't exist yet
                    
                    for(int j = 0; j < reports.size(); j++){
                        // the category has been found in 'reports' list
                        if(reports.get(j).category.equals(t.getCategory())){
                            exists = true;
                            index = j; // record the index of the report
                        }
                    }
                    
                    if(exists){ // if the category already exists
                        rep = reports.get(index); // get the correct report
                        
                        // update values
                        totalSpent += t.getAmount();
                        rep.total += t.getAmount();
                    }
                    else { // the category does not already exist
                        rep = new Report(); // new Report
                        
                        // update values
                        totalSpent += t.getAmount();
                        rep.category = t.getCategory();
                        rep.total = t.getAmount();
                        
                        // add new report to the list
                        reports.add(rep);
                    }
                } // if
            } // for
            
            // populate the table
            for(int i = 0; i < reports.size(); i++){
                rep = reports.get(i);
                rep.percent = (rep.total / totalSpent) * 100; // update the percentage of total spending
                
                // add a row to the table
                tableModel.addRow(new Object[]{rep.category, "$" + rep.total, rep.percent + "%"});
            } // for
            
            // update the UI
            sum_lab.setVisible(false);
            button_1.setVisible(false);
            button_2.setVisible(false);
            view_acct.setVisible(true);
        }
        else { // no accounts have been created yet
            JOptionPane.showMessageDialog(null, "You must create an account first!");
        }
    }
    
    
    
    
    // setup the table for viewing accounts
    private static void initTableAccounts(){
		currTab = 0;
        
        // update the total balance of all accounts
		Double total = 0.0;
		for(Account a:accounts){
            total += a.getBalance();
		}
        sum_lab.setText("Total: $" + total);
        
        Account account;// = new Account();
        tableModel.setColumnCount(0);
        tableModel.setRowCount(0);
        
        // setup columns
        tableModel.addColumn("Account Name");
        tableModel.addColumn("Account Type");
        tableModel.addColumn("Balance");
        
        // combobox for changing account type
        JComboBox accTypeCombo = new JComboBox();
        accTypeCombo.addItem("Checking");
        accTypeCombo.addItem("Savings");
        accTypeCombo.addItem("Credit Card");
        accTypeCombo.addItem("COD");
        accTypeCombo.addItem("Money Market");
        
        // put the combobox in the middle column
        table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(accTypeCombo));
        
        // make the combobox change the account type when it is selected
        accTypeCombo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int index = accTypeCombo.getSelectedIndex();
                int row = table.getSelectedRow();
                
                if(index >= 0 && row >= 0){
                    Account account = accounts.get(row);
                    
                    switch(index){
                        case 0:
                            account.setType("Checking");
                            break;
                        case 1:
                            account.setType("Savings");
                            break;
                        case 2:
                            account.setType("CreditCard");
                            break;
                        case 3:
                            account.setType("COD");
                            break;
                        case 4:
                            account.setType("MoneyMarket");
                            break;
                    }
                    
                    IO.updateAccountData(accounts);
                }
            }
        });
        
        // add a row for each account
        for(int i = 0; i < accounts.size(); i++){
            account = accounts.get(i);
            
            tableModel.addRow(new Object[]{account.getName(), account.getType() , "$" + account.getBalance()});
        }
        
        // buttons, dropdown, and balance counter
        sum_lab.setVisible(true);
        button_1.setText("New Account");
        button_1.setVisible(true);
        button_2.setText("Delete Account");
        button_2.setVisible(true);
        view_acct.setVisible(false);
    } // initTableAccounts
    
    
    
    
    // draw the components onto the window
    private static void makeLayout(){
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createParallelGroup()
                    .addGroup(groupLayout.createSequentialGroup()
                        .addComponent(act_mgmt, 0, 0, Short.MAX_VALUE)
                        .addPreferredGap(act_mgmt, reports, LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(reports, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(reports, record_transaction, LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(record_transaction, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(record_transaction, view_acct, LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(view_acct, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    )
                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, windowWidth - 40, GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addGroup(groupLayout.createSequentialGroup()
                        .addComponent(button_1)
                        .addGap(10)
                        .addComponent(button_2)
						.addGap(600)
						.addComponent(sum_lab)
                    )
                )
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addGroup(groupLayout.createParallelGroup()
                        .addComponent(act_mgmt)
                        .addGap(10)
                        .addComponent(reports)
                        .addGap(10)
                        .addComponent(record_transaction)
                        .addGap(10)
                        .addComponent(view_acct)
                    )
                    .addGap(10)
                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, windowHeight - 150, GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addGroup(groupLayout.createParallelGroup()
                        .addGap(10)
                        .addComponent(button_1)
                        .addGap(10)
                        .addComponent(button_2)
						.addGap(600)
						.addComponent(sum_lab)
                    )
                )
        );
        // keep the buttons all the same size
        groupLayout.linkSize(SwingConstants.HORIZONTAL, act_mgmt, reports, record_transaction, view_acct, button_1, button_2);
        groupLayout.linkSize(SwingConstants.VERTICAL, act_mgmt, reports, record_transaction, view_acct, button_1, button_2);
    } // makeLayout
    
    
    
    
    // update the layout whenever the window is resized
    private static class ResizeListener implements ComponentListener{
        public void componentHidden(ComponentEvent e){}
        public void componentMoved(ComponentEvent e){}
        public void componentShown(ComponentEvent e){}
        
        public void componentResized(ComponentEvent e){
            windowWidth = frame.getBounds().width;
            windowHeight = frame.getBounds().height;
            makeLayout();
        }
    } // class ResizeListener
	
    
    
    
    // set the date in the addTransaction popup
    private static boolean transPopCheckDate(String value){
        Scanner scan = new Scanner(value);
        scan.useDelimiter("/");
        
        // make sure there are 3 ints separated by "/"
        for(int i = 0; i < 3; i++){
            if(scan.hasNextInt()){ 
                scan.nextInt();
            } else {
                return false;
            }
        }
        
        return true;
    } // transChangeDate
    
    
    
    
    // handle the complex task of deleting a transfer
    private static void deleteTransfer(Transaction t){
        
    } // deleteTransfer
    
    
    
    
    /*
	 * This class was made to allow the use of certain variable types in the table.
	 * In particular, this allows the use of booleans, because it forces the table to 
	 * return variable classes rather than "Object." 
     * 
     * I pulled this from the Advisor project from last semester so we can easily 
     * use check-boxes if we need to.
	 */
	private static class MyTableModel extends DefaultTableModel{
	
		public Class<?> getColumnClass(int index){
			Class<?> temp = String.class;

			
			try{
				temp = getValueAt(0, index).getClass();
			} catch(NullPointerException npe){
                //System.out.println("NullPointerException - GUI.MyTableModel.getColumnClass");
			}
			
			return temp;
		}
		
		@SuppressWarnings("unchecked")
		public void setValueAt(Object value, int row, int col) {  
			// overridden code
			Vector rowVector = (Vector)dataVector.elementAt(row);  
			rowVector.setElementAt(value, col);  
			fireTableCellUpdated(row, col);
            
            
            
            
            switch(currTab){
                case 0:
                    setValueAccount(value, row, col);
                    break;
                case 1:
                    super.setValueAt(value, row, col); // temporary
                    break;
                case 2:
                    setValueTransaction(value, row, col);
                    break;
                default:
                    System.out.println("ERROR - GUI.MyTableModel - invalid currTab");
            }
		}
        
        // set values of appropriate account
        private void setValueAccount(Object value, int row, int col){
            switch(col){
                case 0:
                    if(String.valueOf(value) == ""){
                        JOptionPane.showMessageDialog(null, "The account must have a name!");
                    } else {
                        String oldName = accounts.get(row).getName();
                        IO.updateTranDataName(oldName, String.valueOf(value)); // rename transaction file
                        
                        accounts.get(row).setName(String.valueOf(value)); // rename the account
                        view_acct.removeAllItems(); // clear the dropdown
                        for(Account a : accounts) // update the dropdown
                            view_acct.addItem(a.getName());
                            
                        IO.updateAccountData(accounts);
                    }
                    break;
			}
        }
		
		
		// set values of appropriate transaction
		private void setValueTransaction(Object value, int row, int col){
			
            switch(col){
				case 0:
                    transChangeDate(value,row);
                    break;
                case 1: 
                    trans.get(row).setPayee(String.valueOf(value)); // change the payee
                    break;
                case 3:
                    trans.get(row).setCategory(String.valueOf(value)); // change the category
                    break;
                case 4:
                    trans.get(row).setComments(String.valueOf(value)); // change the comments
                    break;
                case 5:
                    try{
                        if(trans.get(row).getType().equals("Spending")){
                            currAccount.setBalance(currAccount.getBalance() + trans.get(row).getAmount());
                            trans.get(row).setAmount(Double.parseDouble(String.valueOf(value))); // change the amount
                            currAccount.setBalance(currAccount.getBalance() - trans.get(row).getAmount());
                        }
                        else if(trans.get(row).getType().equals("Income")){
                            currAccount.setBalance(currAccount.getBalance() - trans.get(row).getAmount());
                            trans.get(row).setAmount(Double.parseDouble(String.valueOf(value))); // change the amount
                            currAccount.setBalance(currAccount.getBalance() + trans.get(row).getAmount());
                        }
                        
                    } catch(Exception e){
                        JOptionPane.showMessageDialog(null, "Please enter a valid dollar amount!");
                    }
                    initTableTransactions();
                    break;
			}
            
            IO.updateAccountData(accounts);
            IO.updateTranData(currAccount.getTransactions(), currAccount);
        }
        
        
        private void setValueReport(Object value, int row, int col){
            
        } // setValueReport
        
        // change the date of the transaction
        private void transChangeDate(Object value, int row){
            Transaction t = trans.get(row);
            int[] date = new int[3];
            
            String dateStr = value.toString();
            Scanner scan = new Scanner(dateStr);
            scan.useDelimiter("/");
            
            for(int i = 0; i < 3; i++){
                if(scan.hasNextInt()){
                    scan.nextInt();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Date Format");
                    initTableTransactions();
                    return;
                }
            }
            
            t.setDate(dateStr);
        } // transChangeDate
	} // class MyTableModel
	
	

} // class
