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
	static JFrame frame;
	private static JPanel panel;
    
    private static int windowHeight; // current height of the window
    private static int windowWidth; // current width of the window
    private static GroupLayout groupLayout; // the layout for the components
    
	private static JButton act_mgmt, reports, record_transaction, button_1, button_2; // Buttons
	private static JComboBox view_acct; // User selectable drop down menu to select the account they wish to view
 
	// scrollpane for table implementation --> give the table a scrollbar after the limit for viewable entries has been met
	private static JScrollPane scrollPane;
	private static JTable table;
    private static MyTableModel tableModel;
    private static ListSelectionModel listModel;
    
    private static ArrayList<Account> accounts;
	
    
    
    
	public static void main(String[] args) {
		
        accounts = new ArrayList<Account>();
        
        // Defines and sets up the Frame and Panel
		// loads the GUI
		GUI();
		
	} // main
    
    
	
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void GUI(){
	
		frame = new JFrame("Financial Tool");
		// sets the size of the frame
		frame.setSize(1024,768);   // <-- Feel free change this dimensions accordingly for debugging --> My machine current aspect ratio is 1600 x 900
		
		//creates a table for user date entry
		table = new JTable();
        tableModel = new MyTableModel();
		//give table the ability to select multiple rows simultaneously
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); 
		//table.setPreferredSize(new Dimension(400,300)); // <-- Feel free change this dimensions accordingly for debugging --> My machine current aspect ratio is 1600 x 900
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
		view_acct= new JComboBox<String>();
        
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
		
        
		//button 1
		button_1 = new JButton("Button 1");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//code for onclick event goes here
			}
		});
		
		
		//button 2
		button_2 = new JButton("Button 2");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				//code for onclick event goes here
			}
		});
        
        
        // adds components to the Drop down menu for user to select the account they wish to view
		view_acct.addItem("Account 1");
		view_acct.addItem("Account 2");
		view_acct.addItem("Account 3");
        
        
        
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
        
        
		
		
	} // GUI
    
    
    
    
    // setup the table for viewing transactions for the current account
    private static void initTableTransactions(){
        tableModel.setColumnCount(0);
        tableModel.setRowCount(0);
        
        tableModel.addColumn("Transactions");
        tableModel.addColumn("Transactions");
        tableModel.addColumn("Transactions");
        
        tableModel.addRow(new Object[]{});
        tableModel.addRow(new Object[]{});
        tableModel.addRow(new Object[]{});
    } // initTableTransactions
    
    
    
    
    // setup the table for viewing accounts
    private static void initTableAccounts(){
        tableModel.setColumnCount(0);
        tableModel.setRowCount(0);
        
        tableModel.addColumn("Accounts");
        tableModel.addColumn("Accounts");
        tableModel.addColumn("Accounts");
        
        tableModel.addRow(new Object[]{});
        tableModel.addRow(new Object[]{});
        tableModel.addRow(new Object[]{});
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
		}
	} // class MyTableModel
	
	

} // class
