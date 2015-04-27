import java.util.*;
import java.util.Date;

public class Transaction{
    
    private String type; // spending / income / transfer
    private double amount; // the amount of money involved
    private String date; // the date the transaction occurred
    private String payee; // where the money came from or went to
    private String comments; // any comments the user would like to make about the transaction
    private String category; // e.g. groceries, bills, phone, gas, etc
    
    // only used for transfers
    private Account target; // the account this transfer is being sent to
    private Account sender; // the account that sent this transfer
    
    public Transaction(){
        amount = 0;
        date = "today";
        payee = "N/A";
        comments = "";
        category = "any";
        target = null;
        sender = null;
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
    
    public void setType(String type){
        
        this.type=type;
    }
    
    public void setTarget(Account target){
        this.target = target;
    } // setTarget
    
    public void setSender(Account sender){
        this.sender = sender;
    } // setSender
    
    
    
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
        return category;
    } // getCategory
    
    public String getType(){
        return type;
    } // getType
    
    public Account getTarget(){
        return target;
    } // getTarget
    
    public Account getSender(){
        return sender;
    } // getSender
} // class


