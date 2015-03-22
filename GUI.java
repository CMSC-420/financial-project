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
	
	/**
	 * 
	 * GUI Components
	 */
	
	//Basics for every GUI 
	static JFrame frame;
	private static JPanel panel;
	
	
	//Buttons for user to select the option they wish
	static JButton act_mgmt, reports, record_transaction, button_1, button_2;
    
	/**
	 * 
	 *Below defines the components background data;
	 * @param args
	 */
	
	
	/**Elements of the ComboBox
	*  not implemented but may be a better solution in the future
	*  static String[] view_options = { "Account Management", "Reports", "Record Transactions"};
	*/
	
	//User selectable drop down menu to select the report the wish to view
	private static JComboBox view_acct;

	
	//used for selection management --> Will be used later on during the project
	private ListSelectionModel listModel; 
	//scrollpane for table implementation --> give the table a scrollbar after the limit for viewable entries has be meet meet
	private static JScrollPane scrollPane;
	private static JTable table;
	
	


	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Defines and sets up the Frame and Panel
		// loads the GUI
		GUI();
		
		
	} // main
    
    
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void GUI(){
	
		frame = new JFrame("Financial Tool");
		// sets the size of the frame
		frame.setSize(1024,768);   // <-- Feel free change this dimensions accordingly for debugging --> My machine current aspect ratio is 1600 x 900
		
		//creates a table for user date entry -->table and table model implemented later in cod
		table = new JTable();
		//give table the ability to select multiple rows simultaneously
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); 
		table.setPreferredSize(new Dimension(400,300)); // <-- Feel free change this dimensions accordingly for debugging --> My machine current aspect ratio is 1600 x 900
		table.setFillsViewportHeight(true); // the table fills out the JScrollPane
		
		//scrollpane --> gives the table a scrollbar when the the table entries go outside the space defined for the table on the Frame 
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(400,400)); //<-- Feel free change this dimensions accordingly for debugging --> My machine current aspect ratio is 1600 x 900
		//adds the table to the scrollpane
		scrollPane.setColumnHeaderView(table);
		
		/**
		 * creates a Drop Down Select Menu to choose between different categories:
		 *    Categories that will be implemented later in code:
		 *    	1) Account Management
		 * 		2) Reports
		 * 		3) Record Transactions
		*/
		view_acct= new JComboBox<String>();
        
        // makes the X in the titlebar close the program
        // DO NOT DELETE THIS AGAIN
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
		 * Drop down menu for the user to select the type of report they wish to view
		 * 		1) Account Management
		 * 		2) Reports
		 * 		3) Record Transactions
		 * 
		 * 
		 *
		 */
		
		
		
		//Start Selectable Button
		// account management
		act_mgmt=  new JButton("Accounts");
		act_mgmt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
				//code for onclick event goes here
				
				
			}
		});
		
		
		//reports
		reports = new JButton("Reports");
		reports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				//code for onclick event goes here
				
			}
		});
		
		
		//record transaction
		record_transaction= new JButton("Transactions");
		record_transaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				//code for onclick event goes here
				
				
			}
		});  //end user selectable buttons
		
		
		
		
		
		
		
		
		/**adds components to the Drop down menu for user selectability --> Can be implemented in an Array as seen above: 
		 * 																	This solution could be a better solution in the long run as it allows for a single array to contain
		 * 																	all the user options and then access them with the correct array index --> would make code more organized
		*																	but the current implementation will work for the current sprint
		*/
		view_acct.addItem("Account 1");
		view_acct.addItem("Account 2");
		view_acct.addItem("Account 3");
		
		
		
		/**
		 * 
		 * Below Creates a table and a table model:
		 * 	--> The Table allows for data entry
		 *  --> The Table model allows a custom model in order to implement a table with selectable and editable cell --> Like an excel spreadsheet 
		 * 
		 * 
		 */
	
		table.setModel(new MyTableModel());
		
		/**
		 * 
		 * Bellow are the buttons you requested
		 * 
		 */
		
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
		});//end requested buttons
		
		
		/**
		 * 
		 * 
		 * 	Below Provides positioning definitions for all GUI components:
		 * 
		 *  	1)User selectable Buttons:  
		 *  		1) Account Management
		 * 			2) Reports
		 * 			3) Record Transaction
		 * 		2) Drop Down Box for user selectable report:
		 * 			1) Account Management
		 * 			2) Reports
		 * 			3) Record Transaction
		 * 		3) Table and MyTableModel
		 * 		4) "Button 1" and "Button 2" --> Adam please update the names once you name them
		 * 		________________________________________________________________________________________
		 * 
		 * 		This list will be updated as components are added to the project
		 * 
		 * 
		 */
		
		
		
		
		// provides a definition for the specific layout chooses for this project: GridBagLayout:: 
		// GridBagLayout allow as the name suggests an "x" , "y" grid layout to position things within the the Frame :: Components in this type of layout
		// can be thought of as positioned with in coordinate grid with each component having a specific "x" and "y" coordinate on the grid
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 109, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 333, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		
		//account management button
		GridBagConstraints gbc_act_mgmt = new GridBagConstraints();
		gbc_act_mgmt.fill = GridBagConstraints.HORIZONTAL;
		gbc_act_mgmt.insets = new Insets(0, 0, 5, 5);
		gbc_act_mgmt.gridx = 1;
		gbc_act_mgmt.gridy = 3;
		frame.getContentPane().add(act_mgmt, gbc_act_mgmt);
		
		//report button
		GridBagConstraints gbc_reports = new GridBagConstraints();
		gbc_reports.fill = GridBagConstraints.HORIZONTAL;
		gbc_reports.insets = new Insets(0, 0, 5, 5);
		gbc_reports.gridx = 2;
		gbc_reports.gridy = 3;
		frame.getContentPane().add(reports, gbc_reports);
		
		//record transaction button
		GridBagConstraints gbc_record_transcation = new GridBagConstraints();
		gbc_record_transcation.insets = new Insets(0, 0, 5, 5);
		gbc_record_transcation.gridx = 3;
		gbc_record_transcation.gridy = 3;
		frame.getContentPane().add(record_transaction, gbc_record_transcation);
		
		//view account--> the drop down menu for user selectable reports
		GridBagConstraints gbc_view_acct = new GridBagConstraints();
		gbc_view_acct.gridwidth = 3;
		gbc_view_acct.insets = new Insets(0, 0, 5, 0);
		gbc_view_acct.fill = GridBagConstraints.HORIZONTAL;
		gbc_view_acct.gridx = 19;
		gbc_view_acct.gridy = 3;
		frame.getContentPane().add(view_acct, gbc_view_acct);
		
		
	
		// scrollpane for table
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.anchor = GridBagConstraints.NORTH;
		gbc_scrollPane.gridwidth = 21;
		gbc_scrollPane.fill = GridBagConstraints.HORIZONTAL;  // do not change this! If not specified to "HORIZONTAL" then the scroll pane fills
															  // both the horizontal and vertical limits of the screen which because it goes the the bottom of the frame
															  // then does not allow for other components to be added to the frame after the table and scrollpane limit have been reached
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 4;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);

		
		
		/**
		 * 
		 * 		Bellow: 	GUI positioning definitions for "button 1" and button 2 --> the buttons you requested
		 */
		
		/**
		 * what ever you name the buttons make sure to rename the button layout name as well: ex: gbc_<button name>
		 */
		GridBagConstraints gbc_button1 = new GridBagConstraints();
		gbc_button1.fill = GridBagConstraints.HORIZONTAL;
		gbc_button1.insets = new Insets(0, 0, 0, 5);
		gbc_button1.gridx = 1;
		gbc_button1.gridy = 5;
		frame.getContentPane().add(button_1, gbc_button1);
				
				
		/**
		 * what ever you name the buttons make sure to rename the button layout name as well: ex: gbc_<button name>
		 */
		GridBagConstraints gbc_button2 = new GridBagConstraints();
		gbc_button2.fill = GridBagConstraints.HORIZONTAL;
		gbc_button2.insets = new Insets(0, 0, 0, 5);
		gbc_button2.gridx = 2;
		gbc_button2.gridy = 5;
		frame.getContentPane().add(button_2, gbc_button2);
		
		
		
		
		
		/**
		 * 
		 * What ever you do, DO NOT Delete the below line of code: This line sets the GUI FRAME and ALL SUB COMPONENTS to visible. 
		 * 
		 * Without this line, if you run the application, NOTHINg will display and it will seem as if something is broken. 
		 * This is not the case, the application IS running it is simply not showing thus why you must set the visibility to TRUE.
		 *
		 */
		frame.setVisible(true);
		
		
	} // GUI
	
    
    /*
	 * This class was made to allow the use of certain variable types in the table.
	 * In particular, this allows the use of booleans, because it forces the table to 
	 * return variable classes rather than "Object." 
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
