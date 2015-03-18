/**
 * This is the base class for each account type.
 * All accounts (checking, savings, etc) will extend this class.
 * 
 * This class holds the basic information and operations common to all accounts.
 */

public class Account{
    
    private int balance; // balance of the account
    private String name; // name of the account
    
    public Account(){
        balance = 0;
        name = "NEW ACCOUNT";
    } // default constructor
    
    /*
     * Properties
     */
     
    public void setBalance(int balance){
        this.balance = balance;
    } // setBalance
    
    public int getBalance(){
        return balance;
    } // getBalance
    
    public void setName(String name){
        this.name = name;
    } // setName
    
    public String getName(){
        return name;
    } // getName
} // class