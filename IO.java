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
	
    // a text file to hold account information
    private static File accountData = new File("AccountData.txt");
    private static File tranData;
    
    // create all necessary files if they don't already exist
    // load in any saved information if the files do exist
    @SuppressWarnings("unchecked")
    public static void initAccount(ArrayList<Account> accounts) throws IOException{
        
		
        // create account data database
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
                System.out.println(type + name + balance);
				
				
				
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
			
		 // Load account data if the file already exists
	
	}
    
    
    
    private static void initTrans(Account acc){
      
			String nameHolder = acc.getName();
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
			
            try {
				Class.forName(JDBC_DRIVER);
				Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				
				Statement stmt2 = conn.createStatement();
			  
				String query = "Select * From transactions WHERE name = " + "\'" + nameHolder + "\'";
			  
				ResultSet rslt = stmt2.executeQuery(query);
				Transaction trans;
                
            while(rslt.next()){
				System.out.println("check");
				System.out.println(rslt.getString(1));
				System.out.println(rslt.getString(2));
				System.out.println(rslt.getString(3));
				System.out.println(rslt.getString(4));
				System.out.println(rslt.getString(5));
				System.out.println(rslt.getString(6));
				System.out.println(rslt.getString(7));
				System.out.println("check done");
				
				
				
                trans = new Transaction();
                trans.setType(rslt.getString(2));
                trans.setAmount(rslt.getDouble(3));
                trans.setDate(rslt.getString(4));
                trans.setPayee(rslt.getString(5));
                trans.setCategory(rslt.getString(6));
                trans.setComments(rslt.getString(7));
                
                acc.addTransaction(trans);
                
                conn.close();
            }
        }
        
        catch(SQLException se){
            se.printStackTrace();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        
    }// initTrans
    
	
    
	
    // rewrite accountData.txt with new account info
    public static void updateAccountData(ArrayList<Account> accounts){
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
	
	
    
	
	// rewrite tranData.txt with new account info
    public static void updateTranData(ArrayList<Transaction> trans, Account acc){
        try{ 
            
			Class.forName(JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
					
			Statement stmt = conn.createStatement();
			
			String update = "DELETE FROM transactions WHERE name = \'" + acc.getName() +"\'";
			
			stmt.executeUpdate(update);
            for(int i = 0; i < trans.size(); i++){
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