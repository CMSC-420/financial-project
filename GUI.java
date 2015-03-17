import javax.swing.*;
import javax.swing.table.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;

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
	static JFrame frame;
	static JPanel panel;
	
	
	//Buttons for user to select the option they wish
	static JButton act_mgmt, reports, record_transaction;
	
	
	
	
	/**
	 * 
	 *Below defines the components background data;
	 * @param args
	 */
	//Elements of the ComboBox
	// not impliments but ay be a better solution in the future
	//static String[] view_options = { "Account Management", "Reports", "Record Transactions"};

	
	//User selectable drop down menu to select the report the wish to view
	private static JComboBox view_acct;
	private static JTable table;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Defines and setups up the Frame
		//loads the GUI
		GUI();
		
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void GUI(){
		//panel = new JPanel();
		frame = new JFrame("Financial Tool");
		// sets the size of the frame
		frame.setSize(1070,800);   // <-- Feel free change this diminseions accordinly
		
		
		
		/**
		 * 
		 * 		Below Defines the GUI components
		 * 
		 * 
		 */
		
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		
		/***
		 * 
		 * 
		 * Below defines the GUI components for user selectable Buttons:
		 * 
		 * 1) Account Management
		 * 2) Reports
		 * 3) Record Transaction
		 * 
		 * 
		 * 
		 */
		act_mgmt=  new JButton("Account Management");
		GridBagConstraints gbc_act_mgmt = new GridBagConstraints();
		gbc_act_mgmt.fill = GridBagConstraints.HORIZONTAL;
		gbc_act_mgmt.insets = new Insets(0, 0, 5, 5);
		gbc_act_mgmt.gridx = 1;
		gbc_act_mgmt.gridy = 3;
		frame.getContentPane().add(act_mgmt, gbc_act_mgmt);
		
		reports = new JButton("Reports");
		GridBagConstraints gbc_reports = new GridBagConstraints();
		gbc_reports.fill = GridBagConstraints.HORIZONTAL;
		gbc_reports.insets = new Insets(0, 0, 5, 5);
		gbc_reports.gridx = 2;
		gbc_reports.gridy = 3;
		frame.getContentPane().add(reports, gbc_reports);
		
		record_transaction= new JButton("Record Transaction");
		GridBagConstraints gbc_record_transcation = new GridBagConstraints();
		gbc_record_transcation.insets = new Insets(0, 0, 5, 5);
		gbc_record_transcation.gridx = 3;
		gbc_record_transcation.gridy = 3;
		frame.getContentPane().add(record_transaction, gbc_record_transcation);
		
		
		
		/***
		 * 
		 * 
		 * Below defines a Drop down menu for the user to select the type of report they wish to view
		 */
		view_acct= new JComboBox<String>();
		
		///adds components to the Drop down meen for user selectability --> Can be implmented in an Array as seen above: THis solution could be a better solutio
		// but the current implimentation will work for the current sprint
		view_acct.addItem("Account Management");
		view_acct.addItem("Reports");
		view_acct.addItem( "Record Transactions");
		
		GridBagConstraints gbc_view_acct = new GridBagConstraints();
		gbc_view_acct.gridwidth = 3;
		gbc_view_acct.insets = new Insets(0, 0, 5, 0);
		gbc_view_acct.fill = GridBagConstraints.HORIZONTAL;
		gbc_view_acct.gridx = 19;
		gbc_view_acct.gridy = 3;
		frame.getContentPane().add(view_acct, gbc_view_acct);
		frame.setVisible(true);
		
		//JTable to hold teh user transcation dda
		table = new JTable();
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.gridwidth = 21;
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 1;
		gbc_table.gridy = 4;
		frame.getContentPane().add(table, gbc_table);
	
		frame.setVisible(true);
	}

}
