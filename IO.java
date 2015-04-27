/**
 * This class will handle all of the file IO. 
 * Keeping it separate will clean up the main code and make it easier to track down bugs.
 */
 
import javax.swing.*;
import java.io.*;
import java.util.*;
//import java.nio.file.*;
import java.sql.*;

public class IO extends GUI {
    
	
	// JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/cmsc420";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "";
	
    // create all necessary mysql tables if they don't already exist
    // load in any saved information if the files do exist=
    @SuppressWarnings("unchecked")
    public static void initAccount(ArrayList<Account> accounts) throws IOException{
        
        // create account data mysql table
        try{
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            Statement stmt = conn.createStatement();
            
            String sql = "CREATE TABLE accounts " +
               "(type VARCHAR(10) not NULL, " +
               " name VARCHAR(30), " + 
               " balance double, " +  
               " PRIMARY KEY ( type, name ))"; 
            
            stmt.executeUpdate(sql);
        
            conn.close();
        } 
        
        catch(SQLException se){
            se.printStackTrace();
        }
         catch(Exception e){
            e.printStackTrace();
        }
        
        
        
        //checks all transactions to make sure that the account they are associated with still exists
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            Statement stmt = conn.createStatement();
          
            String query = "Select * From transactions";
          
            ResultSet rslt = stmt.executeQuery(query);
            
        
            while(rslt.next()){
                
                String accNameCheck = rslt.getString(1);
                boolean deleteAccTransData = true;
                
                Statement stmt2 = conn.createStatement();
                
                String query2 = "Select * FROM accounts";
              
                ResultSet rslt2 = stmt2.executeQuery(query2);
                
                while(rslt2.next())
                {
                    String transNameCheck = rslt2.getString(2);
                    
                    if(accNameCheck.equals(transNameCheck)){
                    deleteAccTransData = false;
                    }
                }
                
                if(deleteAccTransData)
                {
                    Statement stmt3 = conn.createStatement();
                    String update = "DELETE FROM transactions WHERE name = \'" + accNameCheck + "\'";
                    stmt3.executeUpdate(update);
                }

            }
            conn.close();
        }
        
        catch(SQLException se){
            se.printStackTrace();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        
        

        //pulls all existing data from the tables into the GUI
        try{
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            Statement stmt2 = conn.createStatement();
          
            String query = "Select * From accounts";
          
            ResultSet rslt = stmt2.executeQuery(query);
       
            String type;
            String name;
            double balance;
            
            while(rslt.next()){
                type = rslt.getString(1);
                name = rslt.getString(2);
                balance = rslt.getDouble(3);
               

                Account acc = new Account();
                acc.setType(type);
                acc.setName(name);
                acc.setBalance(balance);
                
                initTrans(acc);
                
                accounts.add(acc);
                
                
            }
            conn.close();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }

	} // initAccount
	
    
    
    private static void initTrans(Account acc){
      
			String nameHolder = acc.getName();
			//creates transactions mysql table if it doesn't already exist
            try{
				Class.forName(JDBC_DRIVER);
				Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				
				Statement stmt = conn.createStatement();
				
				String sql = "CREATE TABLE transactions " +
                   "(name VARCHAR(30) not NULL," +
                   " type VARCHAR(10), " + 
                   " amount double, " +
				   " date VARCHAR(10), " +
				   " payee VARCHAR(30), " +
				   " category VARCHAR(10), " +
                   " comments VARCHAR(100))";

				stmt.executeUpdate(sql);
				
				conn.close();
			}
			catch(SQLException se){
                se.printStackTrace();
            }
            catch(Exception e){
                e.printStackTrace();
            }
			
		
			
			
			//Load data from mysql transactions table into the GUI
            try {
				Class.forName(JDBC_DRIVER);
				Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				
				Statement stmt = conn.createStatement();
			  
				String query = "Select * From transactions WHERE name = " + "\'" + nameHolder + "\'";
			  
				ResultSet rslt = stmt.executeQuery(query);
				Transaction trans;
				
                while(rslt.next()){

                    trans = new Transaction();
                    trans.setType(rslt.getString(2));
                    trans.setAmount(rslt.getDouble(3));
                    trans.setDate(rslt.getString(4));
                    trans.setPayee(rslt.getString(5));
                    trans.setCategory(rslt.getString(6));
                    trans.setComments(rslt.getString(7));
                    
                    acc.addTransaction(trans);
                }
                conn.close();
            }
			
			catch(SQLException se){
                se.printStackTrace();
            }

			catch (Exception e) {
                e.printStackTrace();
            }
        
    }// initTrans
    
	
    
	
    // rewrite mysql accounts table with new account info
    public static void updateAccountData(ArrayList<Account> accounts){
		//delete all entries from accounts table
        try{ 
            Class.forName(JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				
			Statement stmt = conn.createStatement();
			
			String update = "DELETE FROM accounts";
			
			stmt.executeUpdate(update);
            
          conn.close();  
        } catch(SQLException se) {
            se.printStackTrace();
        }
		catch (Exception e) {
            e.printStackTrace();
        }
		
		//Load all data from the GUI accounts array into the mysql accounts table
		try{
			Class.forName(JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				
			Statement stmt = conn.createStatement();
			
			 for(int i = 0; i < accounts.size(); i++){
                
				Statement st = conn.createStatement();
				
				st.executeUpdate("INSERT INTO accounts (type, name, balance) "
                + "VALUES (" + "\'"
                + accounts.get(i).getType() 
                + "\'" + "," + "\'"
                + accounts.get(i).getName() 
                + "\'" + ","
                + accounts.get(i).getBalance()
                + ")");

				
            }// for
			
			conn.close();
			
		}
		 catch(SQLException se) {
            se.printStackTrace();
        }
		catch (Exception e) {
            e.printStackTrace();
        }
		
    } // updateAccountData
	
	
    
	
	// rewrite mysql transactions table with new transaction info
    public static void updateTranData(ArrayList<Transaction> trans, Account acc){
		//delete all entries from the mysql transaction table associated with the account from the parameters 
        try{ 
            
			Class.forName(JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
					
			Statement stmt = conn.createStatement();
			
			String update = "DELETE FROM transactions WHERE name = \'" + acc.getName() +"\'";
			
			stmt.executeUpdate(update);
            
            for(int i = 0; i < trans.size(); i++){
                
				//load all transactions form the transactions array into the mysql transactions table
				Statement st = conn.createStatement();
               
				st.executeUpdate("INSERT INTO transactions (name, type, amount, date, payee, category, comments) "
                    +"VALUES (" + "\'"
                    + acc.getName() 
                    + "\'" + "," + "\'"
                    + trans.get(i).getType() 
                    + "\'" + ","
                    + trans.get(i).getAmount() 
                    + "," + "\'" 
                    + trans.get(i).getDate() 
                    + "\'" + "," + "\'" 
                    + trans.get(i).getPayee() 
                    + "\'" + "," + "\'" 
                    + trans.get(i).getCategory() 
                    + "\'" + "," + "\'"
                    + trans.get(i).getComments() 
                    + "\'" + ")");

            }// for
            
			conn.close();
        }
		catch(SQLException se) {
            se.printStackTrace();
        }
		catch(Exception e) {
            e.printStackTrace();
        }
		
    } // updateAccountData
    
	//edit the name of the of the account in the mysql transactions table
    public static void updateTranDataName(String oldName, String newName){

		try{
			
			Class.forName(JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				
			Statement stmt = conn.createStatement();
			
			String update = "UPDATE transactions SET name= \'" + newName + "\' WHERE name = \'" + oldName + "\'";
			
			stmt.executeUpdate(update);
			
			conn.close();
		}
		
		catch(SQLException se) {
            se.printStackTrace();
        }
		catch(Exception e) {
            e.printStackTrace();
        }
    } // updateTranDataName
    
} // class