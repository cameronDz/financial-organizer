package org.md.finance.organizer.models;

import java.text.DecimalFormat;

/**
 * Abstract class used to hold data for financial accounts of users. 
 * Interest rate stored as a percent
 * @author Cameron
 */
public abstract class AbstractAccount {
    
    protected String name; 
    protected double balance;
    protected double interest;
    
    /**
     * Get the name/institution associated with account
     * @return String representation of name/institution associated with account
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set name/institution associated with account
     * @param n String representation of name/institution associated with account
     */
    public void setName(String n) {
        this.name = n;
    }
    
    /**
     * Get balance associate with the account
     * @return balance associate with account
     */
    public double getBalance() {
        return balance;
    }
    
    /**
     * Sets the balance associated with the account
     * @param d double to be set to the account balance
     */
    public void setBalance(double d) {
        this.balance = d;
    }
    
    /**
     * Amount to be added or deducted from account balance
     * @param amount double amount to be transacted on account
     * @return returns amount that could not be applied to account balance
     */
    public double accountTransaction(double amount) {
        double d = amount;
        
        this.balance += d;
        d = 0.0;
            
        return d;
    }
    /**
     * Get the interest rate associated with the account
     * @return double representing the interest % of the account
     */
    public double getInterestRate() { 
        return interest;
    }
    
    /**
     * Sets the interest rate associated with the account
     * @param d double to represent the interest % of the account
     */
    public void setInterestRate(double d) {
        this.interest = d;
    }
    
    /**
     * Determine the interest accrued by an account over a one month period
     * @return 
     */
    public double monthlyInterestAccrued() {
        return balance * (interest * 0.01 ) / 12.00;
    }
    
    /**
     * An overview of the account status
     * @return String of an account status formatted for user
     */
    public abstract String accountOverview();
    
    /**
     * A print out of an account transaction
     * @param action String of action being performed on account
     * @param amount amount of 
     * @return String of an account transaction formatted for user
     */
    public String accountTransaction(String action, double amount) {
        String empty = "";
        String affect = dollarFormat(amount);
        if( amount == 0.0 ) {
            affect = empty;
        }
        return String.format( " %20s %-25s %12s %12s \n", 
                empty,
                action, 
                affect,
                dollarFormat(balance));
    }
    /**
     * String representation of the class
     * @return String representation the class
     */
    @Override
    public String toString() {
        String s = "Account - ";
        s += "Name: " + name + ". ";
        s += "Balance: " + balance + ". ";
        s += "Interest: " + interest + ". ";
        return s;
    }
    
    /**
     * Formats a double into dollar format
     * @param d double to be formatted
     * @return String representing a dollar amount
     */
    protected String dollarFormat(double d) {
        DecimalFormat dollar = new DecimalFormat("$##,###,###.00");
        return dollar.format(d);
    }
    
    /**
     * Formats a double into percent format
     * @param d double to be formatted
     * @return String representing a percentage 
     */
    protected String percentFormat(double d) {
        DecimalFormat percent = new DecimalFormat("##0.00%");
        return percent.format(d * 0.01);
    }
}
