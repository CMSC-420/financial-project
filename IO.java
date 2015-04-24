/**
 * This class will handle all of the file IO. 
 * Keeping it separate will clean up the main code and make it easier to track down bugs.
 */
 
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.nio.file.*;

public class IO extends GUI {
    
    // a text file to hold account information
    private static File accountData = new File("AccountData.txt");
    private static File tranData;
	private static File tranData_copy;
	 
    
    // create all necessary files if they don't already exist
    // load in any saved information if the files do exist
    @SuppressWarnings("unchecked")
    public static void initAccount(ArrayList<Account> accounts) throws IOException{
        
        if (!accountData.exists()) { // create account data file if it doesn't exist
            try{
                accountData.createNewFile();
            } catch(Exception e){
                e.printStackTrace();
            }
        } else { // Load account data if the file already exists
        
            Scanner scanner = null;
            try {
                scanner = new Scanner(accountData);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            
            String type;
            String name;
            double balance;
            
            while(scanner.hasNextLine()){
                type = scanner.next();
                name = scanner.next();
                balance = scanner.nextDouble();
                
                Account acc = new Account();
                acc.setType(type);
                acc.setName(name);
                acc.setBalance(balance);
                
                initTrans(acc);
                
                accounts.add(acc);
                
                scanner.nextLine();
            }
            
            scanner.close();
        }
        
    } // init Accounts
	
	
	
	
	
    
    
    
    private static void initTrans(Account acc) throws IOException{
        tranData = new File(acc.getName() + ".txt");
		/**
		* Below was me playing around with an idea... dont delete just yet...	
		*
		*  My Thinking was to create text files for the date as one possible solution. Rather than hav ea gigantic data file to have to sort by data range:
		*		Simply create a text file for each new entry, but then I realized that that this might not be the best idea as 
		*		yes the data per file would be low, that would create many text files which would be a bad Idea all the way around....
		*		I abondond this idea and began attempting another idea, but Am keeping this here in the event I think of a better implementation 
		*		of this model.
		*   Basically copies the textfile as to retain the data easily but the renames the file to create a new distinct file with all the original data
		*   
		*/
		//Path srcPath = tranData.toPath();
		//File tranData_copy = new File(acc.getName() +"Reports.txt");
		//Path destPath = tranData_copy.toPath();
		//Files.copy(srcPath, destPath);
		
        
        if(!tranData.exists()){
            try{
                for (Account a : accounts) {
                 tranData.createNewFile();
                    // tranData = new File(System.getProperty("user.dir")+"0"+a.getName()+"TransactionData.txt");
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        } else {
            Scanner scan = null;
            try {
                scan = new Scanner(tranData);
                scan.useDelimiter(" ");
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            
            Transaction trans;
            
            while(scan.hasNextLine()){
                
                trans = new Transaction();
                trans.setType(scan.next());
                trans.setAmount(scan.nextDouble());
                trans.setDate(scan.next());
                trans.setPayee(scan.next());
                trans.setCategory(scan.next());
                trans.setComments(scan.next());
                
                acc.addTransaction(trans);
                
                scan.nextLine();
            }
            scan.close();
            
        }
    } // initTrans
    
	
    
	
    // rewrite accountData.txt with new account info
    public static void updateAccountData(ArrayList<Account> accounts){
        try{ 
            accountData.delete();
            accountData.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(accountData,true));
            
            for(int i = 0; i < accounts.size(); i++){
                
                bw.write(accounts.get(i).getType() + " " + accounts.get(i).getName() + " " + accounts.get(i).getBalance());
                bw.newLine();
                
            }// for
            
            bw.close();
        } catch(IOException e1) {
            e1.printStackTrace();
        }
    } // updateAccountData
	
	
    
	
	// rewrite tranData.txt with new account info
    public static void updateTranData(ArrayList<Transaction> trans, Account acc){
        try{ 
            tranData = new File(System.getProperty("user.dir")+"/" + acc.getName() + ".txt");
            tranData.delete();
            tranData.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(tranData,true));
            
            for(int i = 0; i < trans.size(); i++){
                //System.out.println("Testing Date: " + trans.get(i).getDate());
                bw.write(trans.get(i).getType() + " " 
                    + trans.get(i).getAmount() + " " 
                    + trans.get(i).getDate() + " " 
                    + trans.get(i).getPayee() + " " 
                    + trans.get(i).getCategory() + " "
                    + trans.get(i).getComments() + " " );

               bw.newLine();
                
            
                
            }// for
            
            bw.close();
        } catch(IOException e1) {
            e1.printStackTrace();
        }
    } // updateAccountData
    
    public static void updateTranDataName(String oldName, String newName){
        File oldFile = new File(oldName + ".txt");
        File newFile = new File(newName + ".txt");
        
        if(newFile.exists()){
            JOptionPane.showMessageDialog(null, "That account name already exists!");
        } else {
            oldFile.renameTo(newFile);
        }
    } // updateTranDataName
    
} // class