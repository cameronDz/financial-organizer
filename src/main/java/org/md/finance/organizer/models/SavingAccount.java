package org.md.finance.organizer.models;

/**
 * Saving account associated to user. Used as data holder
 * @author Cameron
 */
public class SavingAccount extends AbstractAccount {
    
    /**
     * Constructor, sets all class variables
     * @param name institute associated with account
     * @param balance funds associated with account
     * @param interest interest rate % associated with account
     */
    public SavingAccount(String name, double balance, double interest) {
        this.name = name;
        this.balance = balance;
        this.interest = interest;        
    }
    
    /**
     * Constructor using an existing instance of a SavingAccount class
     * @param s instance of a SavingAccount class to be duplicated
     */
    public SavingAccount(SavingAccount s) {
        this.name = s.getName();
        this.balance = s.getBalance();
        this.interest = s.getInterestRate();
    }
    
    /**
     * Deposits funds into the account
     * @param d amount to deposit into account
     */
    public void makeDeposit(double d) {
        this.balance += d;
    }
    
    /**
     * An overview of the account status
     * @return String of an account status formatted for user
     */
    @Override
    public String accountOverview() {
        return String.format( "Account Overview: %-20s %8s %25s \n", 
                name, 
                this.percentFormat(interest),
                this.dollarFormat(balance) );
    }
        
    /**
     * Calls parent class toString, adds addition information to output
     * @return String representation of class
     */
    @Override
    public String toString() {
        String s = "Saving ";
        s += super.toString();
        return s;
    }
}
