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
    
    // the currently selected tab
    // 0 = Account, 1 = Reports, 2 = Transactions
    protected static int currTab = 0;
    protected static Account currAccount; // the currently selected account
    

    
    
	public static void main(String[] args) throws IOException {
		
        // create the array list that holds the accounts
        accounts = new ArrayList<Account>();
        
        IO.init(accounts);
        
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
				
                currTab = 0;
				initTableAccounts();
			}
		});
		
		
		//reports
		reports = new JButton("Reports");
		reports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
                currTab = 1;
				initTableReports();
			}
		});
		
		
		//record transaction
		record_transaction = new JButton("Transactions");
		record_transaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
                currTab = 2;
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
				switch(currTab){
                    case 0: // Accounts - delete button
                        int row = table.getSelectedRow();
                        
                        if(row > -1){ // if something is selected
                            // confirm the user's choice to delete
                            int result = JOptionPane.showConfirmDialog(frame, // frame
                                            "Are you sure you want to delete this account?", // message
                                            "Comfirm Delete", // title
                                            JOptionPane.OK_CANCEL_OPTION); // options
                            
                            if(result == JOptionPane.OK_OPTION){ // user confirmed
                                accounts.remove(row); // remove the selected account from the array list
                                view_acct.removeAllItems(); // clear the dropdown
                                // update the dropdown with the new array list
                                for(Account a : accounts) {
                                    view_acct.addItem(a.getName());
                                }
                                initTableAccounts(); // update the table
                                IO.updateAccountData(accounts); // update the text file
                            } else { // user canceled
                                // do nothing
                            }
                        }
                        break;
                    case 1: // Reports
                        break;
                    case 2: // Transactions
                        break;
                    default:
                        System.out.println("\n\nERROR - GUI.button_2 - invalid currTab\n");
                }
			}
		});
        
        
        // adds components to the Drop down menu for user to select the account they wish to view
        for(Account a : accounts) {
            view_acct.addItem(a.getName());
        }
        
        // keep track of the currently selected account
        view_acct.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int index = view_acct.getSelectedIndex();
                
                if(index > 0)
                    currAccount = accounts.get(index);
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
            
            if(check_input_account(accName.getText(), accBal.getText(), dialog, accName, accBal)){
                name = accName.getText();
                balance = Double.parseDouble(accBal.getText());
                type = accType.getSelectedItem().toString();
            
                try{
                    switch(type){ // add account depending on type
                        case "Checking":
                            Checking checking = new Checking();
                            checking.setBalance(balance);
                            checking.setName(name);
                            accounts.add(checking);
                            view_acct.addItem(name); // add new account to dropdown
                            initTableAccounts();
                            
                            if(currAccount == null)
                                currAccount = checking;
                            break;
                        case "Savings":
                            Savings savings = new Savings();
                            savings.setBalance(balance);
                            savings.setName(name);
                            accounts.add(savings);
                            view_acct.addItem(name); // add new account to dropdown
                            initTableAccounts();
                            
                            if(currAccount == null)
                                currAccount = savings;
                            break;
                        case "COD":
                            COD cod = new COD();
                            cod.setBalance(balance);
                            cod.setName(name);
                            accounts.add(cod);
                            view_acct.addItem(name); // add new account to dropdown
                            initTableAccounts();
                            
                            if(currAccount == null)
                                currAccount = cod;
                            break;
                        case "Credit Card":
                            CreditCard card = new CreditCard();
                            card.setBalance(balance);
                            card.setName(name);
                            accounts.add(card);
                            view_acct.addItem(name); // add new account to dropdown
                            initTableAccounts();
                            
                            if(currAccount == null)
                                currAccount = card;
                            break;
                        case "Money Market":
                            MoneyMarket mm = new MoneyMarket();
                            mm.setBalance(balance);
                            mm.setName(name);
                            accounts.add(mm);
                            view_acct.addItem(name); // add new account to dropdown
                            initTableAccounts();
                            
                            if(currAccount == null)
                                currAccount = mm;
                            break;
                        default:
                            JOptionPane.showMessageDialog(null,"Invalid Entry");
                    }
                } catch(NullPointerException e1){
                    e1.printStackTrace();	
                }
                // write the new account to the file
                IO.updateAccountData(accounts);
            }
        }
    
    } // addAccountPopup
    
    
    
    
    private static void addTransactionPopup(){
        int result;
        
        // temporary panel for the JOptionPane
        JPanel dialog = new JPanel(new BorderLayout(5,5));
        // all of the labels for the JOptionPane
        JPanel labels = new JPanel(new GridLayout(0,1,2,2));
        // all of the input fields for the JOptionPane
        JPanel fields = new JPanel(new GridLayout(0,1,2,2));
        
        // setup the JOptionPane for adding a transaction
        labels.add(new JLabel("Date"));
        labels.add(new JLabel("Payee"));
        labels.add(new JLabel("Account Type"));
        labels.add(new JLabel("Category"));
        labels.add(new JLabel("Comments"));
        labels.add(new JLabel("Amount"));
        dialog.add(labels, BorderLayout.WEST);
        
        JTextField transDate = new JTextField();
        JTextField transPayee = new JTextField();
        JComboBox transType = new JComboBox();
        JTextField transCategory = new JTextField();
        JTextField transComments = new JTextField();
        JTextField transAmount = new JTextField();
        transType.addItem("Spending");
        transType.addItem("Income");
        fields.add(transDate);
        fields.add(transPayee);
        fields.add(transType);
        fields.add(transCategory);
        fields.add(transComments);
        fields.add(transAmount);
        dialog.add(fields, BorderLayout.CENTER);

        // prompt the user for basic account info
        result = JOptionPane.showConfirmDialog(frame, dialog,
                        "New Account", JOptionPane.OK_CANCEL_OPTION);
                        
        if(result == JOptionPane.OK_OPTION){
            if(check_input_trans()){
                String date = transDate.getText();
                String payee = transDate.getText();
                String type = transType.getSelectedItem().toString();
                String category = transCategory.getText();
                String comments = transComments.getText();
                double amount = Double.parseDouble(transAmount.getText());
                
                Transaction newTransaction = new Transaction();
                newTransaction.setDate(date);
                newTransaction.setPayee(payee);
                newTransaction.setCategory(category);
                newTransaction.setComments(comments);
                newTransaction.setAmount(amount);
                
                if(type.equals("Spending")){
                    newTransaction.setIsIncome(false);
                } else {
                    newTransaction.setIsIncome(true);
                }
                
                currAccount.addTransaction(newTransaction);
                initTableTransactions();
            }
        }
    } // addTransactionPopup
    
    
    
    
    
    // method that checks the validity of the user input upon account creation
	public static boolean check_input_account(String name, String balance, JPanel dialog, JTextField accName, JTextField accBal){
		
        boolean valid_input = true;
        
		if(name.equals("")){
			JOptionPane.showMessageDialog(null, "The account must have a name!");
            
            int result = JOptionPane.showConfirmDialog(frame, dialog,
                                "New Account", JOptionPane.OK_CANCEL_OPTION);
                                
			if(result == JOptionPane.OK_OPTION){
                check_input_account(accName.getText(), accBal.getText(), dialog, accName, accBal);
            } else {
                valid_input = false;
            }
		} 
        
        else {
            try{
                Double.parseDouble(balance);
            } catch(Exception e){
                JOptionPane.showMessageDialog(null, "Please enter a valid dollar amount!");
                
                int result = JOptionPane.showConfirmDialog(frame, dialog,
                                "New Account", JOptionPane.OK_CANCEL_OPTION);
                                
                if(result == JOptionPane.OK_OPTION){
                    check_input_account(accName.getText(), accBal.getText(), dialog, accName, accBal);
                } else {
                    valid_input = false;
                }
            }
        }
        
		return valid_input;
	} // check_input_account
    
    
    
    
    // method that checks the validity of the user input upon transaction creation
    private static boolean check_input_trans(){
        return true;
    } // check_input_trans
    
    
    
    
    // setup the table for viewing transactions for the current account
    private static void initTableTransactions(){
        tableModel.setColumnCount(0);
        tableModel.setRowCount(0);
        
        tableModel.addColumn("Date");
        tableModel.addColumn("Payee");
        tableModel.addColumn("Type");
        tableModel.addColumn("Category");
        tableModel.addColumn("Comments");
        tableModel.addColumn("Amount");
        
        ArrayList<Transaction> transactions = currAccount.getTransactions();
        Transaction trans; // current transaction
        
        for(int i = 0; i < transactions.size(); i++){
            trans = transactions.get(i);
            tableModel.addRow(new Object[]{
                trans.getDate(),
                trans.getPayee(),
                trans.isIncome() ? "Income" : "Spending",
                trans.getCategory(),
                trans.getComments(),
                trans.getAmount()
            });
        }
        
        button_1.setText("New Transaction");
        button_2.setText("Delete Transaction");
        button_2.setVisible(true);
    } // initTableTransactions
    
    
    
    
    // setup the table for viewing accounts
    private static void initTableAccounts(){
        Account account = new Account();
        tableModel.setColumnCount(0);
        tableModel.setRowCount(0);
        
        tableModel.addColumn("Account Name");
        tableModel.addColumn("Account Type");
        tableModel.addColumn("Balance");
        
        for(int i = 0; i < accounts.size(); i++){
            account = accounts.get(i);
            tableModel.addRow(new Object[]{account.getName(), account.getType(), "$" + account.getBalance()});
        }
        button_1.setText("New Account");
        button_2.setText("Delete Account");
        button_2.setVisible(true);
    } // initTableAccounts
    

    
    
    // setup the table for viewing reports
    private static void initTableReports(){
        tableModel.setColumnCount(0);
        tableModel.setRowCount(0);
        
        tableModel.addColumn("Reports");
        tableModel.addColumn("Reports");
        tableModel.addColumn("Reports");
        
        tableModel.addRow(new Object[]{});
        tableModel.addRow(new Object[]{});
        tableModel.addRow(new Object[]{});
        
        button_1.setText("Placeholder");
        button_2.setText("Placeholder");
        button_2.setVisible(false);
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
                System.out.println("NullPointerException - GUI.MyTableModel.getColumnClass");
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
                    //setValueReport(value, row, col);
                    break;
                case 2:
                    //setValueTransaction(value, row, col);
                    break;
                default:
                    System.out.println("ERROR - GUI.MyTableModel - invalid currTab");
            }
            
            IO.updateAccountData(accounts);
		}
        
        // set values of appropriate account
        private void setValueAccount(Object value, int row, int col){
            switch(col){
                case 0:
                    accounts.get(row).setName(String.valueOf(value)); // rename the account
                    view_acct.removeAllItems(); // clear the dropdown
                    for(Account a : accounts) // update the dropdown
                        view_acct.addItem(a.getName());
                    break;
            }
        }
	} // class MyTableModel
	
	

} // class
