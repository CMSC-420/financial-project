public class Transaction{
    private double amount; // the amount of money involved
    private boolean isIncome; // whether this is an income or spending transaction
    
    public Transaction(){
        amount = 0;
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
    
    public void setIsIncome(Boolean income){
        this.income = income;
    } // setIncome
    
    public double getAmount(){
        return amount;
    } // getAmount
    
    public Boolean isIncome(){
        return isIncome;
    } // isIncome
} // class