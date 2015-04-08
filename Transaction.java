import java.util.*;
import java.util.Date;

public class Transaction{
    private double amount; // the amount of money involved
    private String date; // the date the transaction occurred
    private String payee; // where the money came from or went to
    private String comments; // any comments the user would like to make about the transaction
    private String category; // e.g. groceries, bills, phone, gas, etc
    private boolean isIncome; // whether this is an income or spending transaction
    
    public Transaction(){
        amount = 0;
        date = "today";
        payee = "N/A";
        comments = "";
        category = "any";
        isIncome = true;
    } // constructor
    
    /*
     * Operations
     */
     
    // anything that isn't getters and setters will go here    
    
    
    
    
    /*
     * Properties
     */
     
    public void setAmount(double amount){
        this.amount = amount;
    } // setAmount
    
    public void setDate(String date){
        this.date = date;
    } // setDate
    
    public void setPayee(String payee){
        this.payee = payee;
    } // setPayee
    
    public void setComments(String comments){
        this.comments = comments;
    } // setComments
    
    public void setCategory(String category){
        this.category = category;
    } // setCategory
    
    public void setIsIncome(Boolean isIncome){
        this.isIncome = isIncome;
    } // setIncome
    
    
    
    public double getAmount(){
        return amount;
    } // getAmount
    
    public String getDate(){
        return date;
    } // getDate
    
    public String getPayee(){
        return payee;
    } // getPayee
    
    public String getComments(){
        return comments;
    } // getComments
    
    public String getCategory(){
        return category
    } // getCategory
    
    public Boolean isIncome(){
        return isIncome;
    } // isIncome
} // class