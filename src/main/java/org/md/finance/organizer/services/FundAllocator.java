package org.md.finance.organizer.services;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.md.finance.organizer.models.CreditAccount;
import org.md.finance.organizer.models.SavingAccount;

/**
 * Used to determine how to best allocate funds between accounts
 * @author Cameron
 */
public class FundAllocator {
    
    protected final static DecimalFormat DOLLAR = new DecimalFormat("$#,###,###.00");
    protected List<CreditAccount> accounts;
    protected SavingAccount savings;
    protected double funds;
    
    // private varibles used in multiple public methods
    private ArrayList<String> prints;
    private double totalInterest;
    private double minMonthly;
    private double excess;
    
    /**
     * Constructor, sets all variables for
     * @param accounts credit accounts to be reviewed
     * @param savings savings account where any excess will be deposited
     * @param funds amount allocated for monthly payments
     */
    public FundAllocator(ArrayList<CreditAccount> accounts, 
            SavingAccount savings, double funds) {
        this.accounts = new ArrayList<>(accounts);
        this.savings = new SavingAccount(savings);
        this.funds = funds;
    }
    
    /**
     * 
     * @param m number of months to be determined
     * @return 
     */
    public String evenExcessPayments(int m) {
        // reset all variables to blank slates
        resetPaymentVariables();
        String s = resetPaymentString();
        
        // check that return string has been reset to ""
        if( ! s.equals("") ) {
            return s;
        } 
        
        // cycle through m months of interest/payments
        for( int i = 0; i < m; i++ ) {
            // tally interest, minimum payment for month for each account
            monthlyInterestLoop(accounts.size(), (i+1));
            monthlyMinimumPaymentLoop(accounts.size(), (i+1));
            
            // make excess monthly payments for each month
            for( int j = 0; j < accounts.size(); j++ ) {
                CreditAccount c = accounts.get(j);
                String type = "Excess payment month " + (i + 1);
                double payment = excess / (double) accounts.size();
                c.makePayment(payment);
                double balance = c.getBalance();
                String transaction = prints.get(j);
                transaction += accountTransaction(type, payment, balance) + "\n";
                prints.set(j, transaction);
            }
        }
        
        return s + paymentPrintoutFinish();
    }
    
    /**
     * 
     * @param m number of months to be determined
     * @return 
     */
    public String highInterestExcessPayments(int m) {
        resetPaymentVariables();
        String s = resetPaymentString();        
        // check that there is enough funds to make minimum monthly payments
        if( ! s.equals("") ) {
            return s;
        } 
        
        //get account with highest interest
        int highestInterest = 0;
        for( int i = 0; i < accounts.size(); i++ ) {
            double current = accounts.get(highestInterest).getInterestRate();
            double challenge = accounts.get(i).getInterestRate();
            if( challenge > current ) {
                highestInterest = i;
            }
        }
        
        // cycle through m months of interest/payments
        for( int i = 0; i < m; i++ ) {
            // accumulate interest on each account and add statement to prints
            // make minimum monthly payments for each month
            monthlyInterestLoop(accounts.size(), (i+1));
            monthlyMinimumPaymentLoop(accounts.size(), (i+1));
                        
            // put all excess funds to account with highest interest rate
            CreditAccount c = accounts.get(highestInterest);
            String type = "High interest excess payment month " + (i + 1);
            double payment = excess;
            c.makePayment(payment);
            double balance = c.getBalance();
            String transaction = prints.get(highestInterest);
            transaction += accountTransaction(type, payment, balance) + "\n";
            prints.set(highestInterest, transaction);
        }
        
        return s + paymentPrintoutFinish();
    }
    
    /**
     * 
     * @param m number of months to be determined
     * @return 
     */
    public String optimizedExcessPayments(int m) {
        resetPaymentVariables();
        String s = resetPaymentString();
        // check that there is enough funds to make minimum monthly payments
        if( ! s.equals("") ) {
            return s;
        } 
        
        // cycle through m months of interest/payments
        for( int i = 0; i < m; i++ ) {
            // accumulate interest on each account and add statement to prints
            // make minimum monthly payments for each month
            monthlyInterestLoop(accounts.size(), (i+1));
            monthlyMinimumPaymentLoop(accounts.size(), (i+1));
            
            // optimized algorithm for minimum accured interest
            // create array of excess payments for each for account 
            double[] excessPayment = new double[accounts.size()];
            
            // increment all excess funds by 0.01$ at a time and allocate the
            // funds to the account with the most interest being accrued
            double counter = 0.0;
            while( counter < excess )  {
                // position with the most accrued interest
                int greatestPosition = 0;
                double greatestInterest = 0.0;
                for( int j = 0; j < accounts.size(); j++ ) {
                    // checks interest on j account and replace if greater than 
                    // current greatest hold account with interest
                    double thisInterest = accounts.get(j).monthlyInterestAccrued(excessPayment[j]);
                    if( greatestInterest > thisInterest ) {
                        greatestPosition = j;
                        greatestInterest = thisInterest;
                    }
                }
                
                // increment excress payment
                excessPayment[greatestPosition] += 0.01;
                // increment counter
                counter += 0.01;
            }
            
            for( int j = 0; j < accounts.size(); j++ ) {
                CreditAccount c = accounts.get(j);
                String type = "Optimized excess payment month " + (i + 1);
                double payment = excessPayment[j];
                c.makePayment(payment);
                double balance = c.getBalance();
                String transaction = prints.get(j);
                transaction += accountTransaction(type, payment, balance) + "\n";
                prints.set(j, transaction);
            }
        }
        
        return s + paymentPrintoutFinish();
    }
    
    /**
     * 
     * @param type
     * @param amountD
     * @param balanceD
     * @return 
     */
    private String accountTransaction(String type, double amountD, double balanceD) {
        String empty = "";
        String amountS = DOLLAR.format(amountD);
        String balanceS = DOLLAR.format(balanceD);
        return String.format( "%5s %-41s %15s %15s", 
                empty, type, amountS, balanceS);
    }
    
    /**
     * Calculates the total minimum monthly payments of all CreditAccount
     * @return double representing the total minimum monthly payment
     */
    private double totalMinimumPayment() {
        double min = 0.0;
        // run through a loop checking account balances and incrementing 
        // the minimum amount of funds required to fulfill minimum payments
        for( int i = 0; i < accounts.size(); i++ ) {
            double bal = accounts.get(i).getBalance();
            double month = accounts.get(i).getMinimum();
            if( Math.abs(bal) < month ) {
                min += bal;
            } else {
                min += month;
            }
        }
        return min;
    }
    
    /**
     * 
     */
    private void resetPaymentVariables() {
        totalInterest = 0.0;
        minMonthly = totalMinimumPayment();
        excess = funds - minMonthly;
        prints = createPlansPrintouts();
    }
    
    /**
     * 
     * @return 
     */
    private String resetPaymentString() {
        String s = "";
        if( minMonthly > funds ) {
            s += "Unable to make minimumal monthly payments for all accounts.";
            s += "\n" + "Procure more funds." + "\n";
            return s;
        } 
        return s;
    }
    
    /**
     * 
     * @param size
     * @param month 
     */
    private void monthlyInterestLoop(int size, int month) {
        for( int i = 0; i < size; i++ ) {
            CreditAccount c = accounts.get(i);
            String type = "Interest month " + month;
            double interest = c.monthlyInterestAccrued();
            totalInterest += interest;
            c.accountTransaction(interest);
            double balance = c.getBalance();
            String transaction = prints.get(i);
            transaction += accountTransaction(type, interest, balance) + "\n";
            prints.set(i, transaction);
        }
    }
    
    /**
     * 
     * @param size
     * @param month 
     */
    private void monthlyMinimumPaymentLoop(int size, int month) {
        for( int j = 0; j < size; j++ ) {
            CreditAccount c = accounts.get(j);
            String type = "Minimum payment month " + month;
            double payment = c.getMinimum();
            c.makePayment(payment);
            double balance = c.getBalance();
            String transaction = prints.get(j);
            transaction += accountTransaction(type, payment, balance) + "\n";
            prints.set(j, transaction);
        }
    }
    
    /**
     * 
     * @return 
     */
    private String paymentPrintoutFinish() {
        String s = "";
        double totalDebt = 0.0;
        
        // put all the account print outs into return string
        for( int i = 0; i < prints.size(); i++ ) {
            s += prints.get(i) + "\n\n";
        }
        
        // add account overviews to end of printout
        for( int i = 0; i < accounts.size(); i ++ ) {
            totalDebt += accounts.get(i).getBalance();
            s += accounts.get(i).accountOverview();
        }
        
        // show total interst yielded
        s += "Total interest: " + DOLLAR.format(totalInterest) + "\n";
        s += "Total debt: " + DOLLAR.format(totalDebt) + "\n";
        
        return s;
    }
    
    /**
     * Creates the base of an array list that will store a budget plan for each
     * account on the allocator. Starts each String as the accounts overview.
     * @return ArrayList of Strings, each String being an account overview
     */
    private ArrayList<String> createPlansPrintouts() {
        ArrayList<String> plans = new ArrayList<>();
        // cycle through list of credit accounts
        for( int i = 0; i < accounts.size(); i++ ){
            String s = accounts.get(i).accountOverview();
            plans.add(s);
        }
        // add saving account to end of list
        plans.add(savings.accountOverview());
        return plans;
    }
    
    /**
     * Gets amount of funds available for accounts
     * @return double representing the amount of funds
     */
    public double getFunds() {
        return this.funds;
    }
    
    /**
     * Sets a new amount to be funds available
     * @param d double to be amount of available funds
     */
    public void setFunds(double d) {
        this.funds = d;
    }
        
    /**
     * String representation of the class
     * @return String representation of the class
     */
    @Override
    public String toString() {
        String s = "Fund Allocator: \n";
        // cycle through all credit accounts
        for( int i = 0; i < accounts.size(); i++ ) {
            s += accounts.get(i).toString() + "\n";
        }
        s += savings + "\n" + "Funds: " + funds + "\n";
        return s;
    }
    
}
