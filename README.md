# financial-project

<b>Background</b>  
You as a user would like to manage your financial accounts with the help of a tool that keeps track of your finances. The purpose of the software is to allow users to manage their spending and income accounts. The software shall also provide a friendly graphical user interface. 


<b>Security Requirements</b> (optional)  
The user must be able to set up password protected account for him/her. The user must be asked to login each time before the user can gain access to the software. 


###<b>Major functions:</b>  

####<b>1. Account management</b>  
The user will be able to use the software to create accounts. The accounts can be of the type savings, checking, Certificate of Deposit (CD), money market or credit card. For each account, the name of the account must be provided, and the name should be unique for each account The initial balance of each account is 0. 

The user will also be able to change the name of the account. 

The user will also be able to delete any of the accounts at any time. Before deleting the account, the user must be asked to provide confirmation. 


####<b>2. Record transaction</b>  
Once the accounts are set-up, the user can record transactions to keep track of the daily financial exchanges. For each transaction, the following attributes must be provided:

- Each transaction must be of type income, spending or transfer (from one account to another account, for example, pay a credit card from your checking account)  
- Each transaction, except for transfers, will also have a category associated with it.   
-- If the transaction pertains to income, then it can belong to salary, interest, bonus, cash, gift, return, tax return or adjustments.   
-- If the transaction pertains to spending, then it can belong to grocery, mortgage, home, child, tuition, health, car, entertainment, dinner, clothing, gifts or adjustments.  
- The amount related to the transaction must be recorded.   
- The date the transaction took place must also be recorded.  
- The balance of the account should be updated.  
- For a spending transaction, also record the payee. (For example, you buy food at Walmart, you record the payee as Walmart)  
- The transaction can also have comments associated with it, which will be a text box where the user can enter information.   

<i>Optional Work:</i> If a transaction occurs periodically with a constant amount, for example, the salary is always received on the first day of each month, or the mortgage is always paid on 5th of each month with a constant amount, the system will automatically add the repeated transactions for up to one year. The software will also allow the user to add or edit categories as needed.


####<b>3. View reports</b>  

The user should be able to view the following reports:  

- Balance report: The user can view the balance for all accounts. The format for the balance report is as follows:  

Date:   

|Name	                      | Balance |  
|---------------------------|---------|  
|SunTrust checking account  |	10,000  |  
|Chase credit card	        | -2,000  |  
|--|--|
|<b>Total balance</b>	      | <b>8,000</b>|  


- Income report: The user will be able to select a period of time and the report should list all the income transactions within that period. The format for income report is as follows:
	
<b>Period: 01/01/2015 to 01/15/2015</b>  

|   |   |   |  
|---|---|---|
|Salary	| 5,000 |	01/02/2015 |  
|Interest |	10 |	01/02/2015 |  
|--|--|--| 
| <b>Total income</b> |	<b>5,010</b>	|  |  

- Spending transaction report: The user will be able to select a period of time and the report should list all the spending transaction within that period. The format for the spending transaction report is as follows:

<b>Period: 01/01/2015 to 01/15/2015</b>

| | | | |
|---|---|---|---|
| Grocery |	Kroger |	100	 | 01/02/2015 |
| Gas	| Costco	 | 25.48	 | 01/02/2015 |
| Health |	CVS	 | 5	| 01/09/2015 |
|--|--|--|--|	
|<b>Total expenditures</b>	| |	<b>130.48</b>	| |

- Spending analysis report: The user will be able to select a period of time and the report should show the spending percentage with respect to different categories. The format for the spending analysis report is as follows:

<b>Period: 01/01/2014 to 12/31/2014</b>  

| | | |
|---|---|---|
|Grocery |	2,000 |	20% |
|Gas	| 1,000	| 10% |
|Health	| 500	| 5% |
|Home 	| 6,000	 | 60% |
|Travel |	500	| 5% |
|--|--|--|
|<b>Total expenditures</b> |	<b>10,000</b>	| |
