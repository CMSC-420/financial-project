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
} // Account class

class Checking extends Account{
    public Checking(){
        
    } // default constructor
} // Checking class

class Savings extends Account{
    public Savings(){
        super();
    } // default constructor
} // Savings class

class CreditCard extends Account{
    public CreditCard(){
        super();
    } // default constructor
} // CreditCard class

class COD extends Account{
    public COD(){
        super();
    } // default constructor
} // COD class

class MoneyMarket extends Account{
    public MoneyMarket(){
        super();
    } // default constructor
} // MoneyMarket class