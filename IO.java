/**
 * This class will handle all of the file IO. 
 * Keeping it separate will clean up the main code and make it easier to track down bugs.
 */
 
import java.io.*;
import java.util.*;

public class IO{
    
    // a text file to hold account information
    private static File accountData = new File(System.getProperty("user.dir")+"/AccountData.txt");
    
    // create all necessary files if they don't already exist
    // also load in any saved information if the files do exist
    @SuppressWarnings("unchecked")
    public static void init(ArrayList<Account> accounts){
        
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
            int balance;
            
            while(scanner.hasNextLine()){
                type = scanner.next();
                name = scanner.next();
                balance = scanner.nextInt();
                
                switch(type){ // add account depending on type
                    case "Checking":
                        Checking checking = new Checking();
                        checking.setBalance(balance);
                        checking.setName(name);
                        accounts.add(checking);
                        break;
                    case "Savings":
                        Savings savings = new Savings();
                        savings.setBalance(balance);
                        savings.setName(name);
                        accounts.add(savings);
                        break;
                    case "COD":
                        COD cod = new COD();
                        cod.setBalance(balance);
                        cod.setName(name);
                        accounts.add(cod);
                        break;
                    case "Credit Card":
                        CreditCard card = new CreditCard();
                        card.setBalance(balance);
                        card.setName(name);
                        accounts.add(card);
                        break;
                    case "Money Market":
                        MoneyMarket mm = new MoneyMarket();
                        mm.setBalance(balance);
                        mm.setName(name);
                        accounts.add(mm);
                        break;
                }//switch
                scanner.nextLine();
            }//while
            scanner.close();
        }
        
    } // init
    
    
    
    
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
    
} // class