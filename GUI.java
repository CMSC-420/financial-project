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
    private static int windowHeight;
    private static int windowWidth;
    private static GroupLayout groupLayout;
	
	
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
        table.setAutoResizeMode(1); // table till auto-resize
		
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
		act_mgmt =  new JButton("Accounts");
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
		record_transaction = new JButton("Transactions");
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
         
        windowHeight = frame.getBounds().height;
        windowWidth = frame.getBounds().width;
		
		
        groupLayout = new GroupLayout(frame.getContentPane());
		makeLayout();
		
		
		
		/**
		 * 
		 * What ever you do, DO NOT Delete the below line of code: This line sets the GUI FRAME and ALL SUB COMPONENTS to visible. 
		 * 
		 * Without this line, if you run the application, NOTHINg will display and it will seem as if something is broken. 
		 * This is not the case, the application IS running it is simply not showing thus why you must set the visibility to TRUE.
		 *
		 */
        frame.getContentPane().setLayout(groupLayout);
        frame.addComponentListener(new ResizeListener());
		frame.setVisible(true);
        
        
		
		
	} // GUI
    
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
        groupLayout.linkSize(SwingConstants.HORIZONTAL, act_mgmt, reports, record_transaction, button_1, button_2);
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
