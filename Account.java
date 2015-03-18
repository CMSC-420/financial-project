/**
 * This is the base class for each account type.
 * All accounts (checking, savings, etc) will extend this class.
 * 
 * This class holds the basic information and operations common to all accounts.
 */

public class Account{
    
    private int balance;
    
    public Account(){
        
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
} // class