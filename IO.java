/**
 * This class will handle all of the file IO. 
 * Keeping it separate will clean up the main code and make it easier to track down bugs.
 */
 
import java.io.*;
import java.util.*;

public class IO extends GUI {
    
    // a text file to hold account information
    private static File accountData = new File(System.getProperty("user.dir")+"/AccountData.txt");
   private static File tranData = new File(System.getProperty("user.dir")+"/TransactionData.txt");
    // private static File tranData = new File("");
    // create all necessary files if they don't already exist
    // load in any saved information if the files do exist
    @SuppressWarnings("unchecked")
    public static void initAccount(ArrayList<Account> accounts){
        
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
                    case "CreditCard":
                        CreditCard card = new CreditCard();
                        card.setBalance(balance);
                        card.setName(name);
                        accounts.add(card);
                        break;
                    case "MoneyMarket":
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
        
    } // init Accounts
    
    
    
    public static void initTrans(ArrayList<Transaction> trans){  // compiler error" name clash init--> Account and Transaction have the same erasure  "
        
        if (!tranData.exists()) { // create transaction data file if it doesn't exist
            try{
                for (Account a : accounts) {
                 tranData.createNewFile();
                    // tranData = new File(System.getProperty("user.dir")+"0"+a.getName()+"TransactionData.txt");
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        } else { // Load tran data if the file already exists
        
            Scanner scanner = null;
            try {
                scanner = new Scanner(tranData);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            
            double amount; // the amount of money involved
            String date; // the date the transaction occurred
            String payee; // where the money came from or went to
            String comments; // any comments the user would like to make about the transaction
            String category; // e.g. groceries, bills, phone, gas, etc
            boolean isIncome; //
            String type; // income, spending or transfer
            
            while(scanner.hasNextLine()){
                type = scanner.next();
               amount = scanner.nextDouble();
                date = scanner.next();
                payee= scanner.next();
                comments = scanner.next();
                category = scanner.next();
//              isIncome=scanner.nextBoolean();
                
                switch(type){ // add account depending on type
                    case "Income":
                        Income income = new Income();
                        income.setAmount(amount);
                        income.setPayee(payee);
                        income.setComments(comments);
                        income.setCategory(category);
//                      income.setIsIncome(isIncome);
                        trans.add(income);
                        break;
                    case "Spending":
                        Spending spending = new Spending();
                        spending.setAmount(amount);
                        spending.setPayee(payee);
                        spending.setComments(comments);
                        spending.setCategory(category);
//                      spending.setIsIncome(isIncome);
                        trans.add(spending);
                        break;
                    case "Transfer":
                        Transfer transfer = new Transfer();
                        transfer.setAmount(amount);
                        transfer.setPayee(payee);
                        transfer.setComments(comments);
                        transfer.setCategory(category);
//                      transfer.setIsIncome(isIncome);
                        trans.add(transfer);
                        break;
                  
                }//switch
                scanner.nextLine();
            }//while
            scanner.close();
        }
        
    } // init Transaction
    
    
    
    
    
    
    
    
    
    
    
    
    
    
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
    public static void updateTranData(ArrayList<Transaction> trans){
        try{ 
            tranData.delete();
            tranData.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(tranData,true));
            
            for(int i = 0; i < trans.size(); i++){
                
                bw.write(trans.get(i).getType() + " " + trans.get(i).getAmount() + " " + trans.get(i).getDate() + " " + trans.get(i).getPayee() + " " +trans.get(i).getComments() + " " +trans.get(i).getCategory() + " " +trans.get(i).isIncome());
                // System.out.println(trans.get(i).getType() + " " + trans.get(i).getAmount() + " " + trans.get(i).getDate() + " " + trans.get(i).getPayee() + " " +trans.get(i).getComments() + " " +trans.get(i).getCategory() + " " +trans.get(i).isIncome());

               bw.newLine();
                
            
                
            }// for
            
            bw.close();
        } catch(IOException e1) {
            e1.printStackTrace();
        }
    } // updateAccountData
    
} // class