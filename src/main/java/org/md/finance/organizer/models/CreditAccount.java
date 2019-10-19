package org.md.finance.organizer.models;

/**
 * Credit account associated to a user. Used as data holder, some calculations.
 *
 * @author Cameron
 */
public class CreditAccount extends AbstractAccount {

	protected double minimum;

	/**
	 * Constructor, sets all class variables
	 *
	 * @param name     institute associated with account
	 * @param balance  funds associated with account
	 * @param interest interest rate % associated with account
	 * @param minimum  minimum monthly payment for account
	 */
	public CreditAccount(String name, double balance, double interest, double minimum) {
		this.name = name;
		this.balance = balance;
		this.interest = interest;
		this.minimum = minimum;
	}

	/**
	 * Constructor using an existing instance of a CreditAccount class
	 *
	 * @param a instance of a CreditAccount class to be duplicated
	 */
	public CreditAccount(CreditAccount a) {
		this.name = a.getName();
		this.balance = a.getBalance();
		this.interest = a.getInterestRate();
		this.minimum = a.getMinimum();
	}

	/**
	 * Credits account with a payment. If payments exceeds the amount of account
	 * balance, excess funds are returned. Otherwise, 0 returned.
	 *
	 * @param d payment amount to be credited to account
	 * @return excess amount of funds if account
	 */
	public double makePayment(double d) {
		if (d > Math.abs(this.balance)) {
			d += this.balance;
			this.balance = 0.0;
			return d;
		}
		this.balance += d;
		return 0.0;
	}

	/**
	 * An overview of the account status
	 *
	 * @return String of an account status formatted for user
	 */
	@Override
	public String accountOverview() {
		return String.format("Account Overview: %-20s %8s %12s %12s \n", name, this.percentFormat(interest),
				this.dollarFormat(minimum), this.dollarFormat(balance));
	}

	/**
	 * Gets the minimum funds required each month to pay towards account
	 *
	 * @return double representing funds amount
	 */
	public double getMinimum() {
		return this.minimum;
	}

	/**
	 * Sets the minimum monthly payment for the account
	 *
	 * @param d double representing the monthly payment
	 */
	public void setMinimum(double d) {
		this.minimum = d;
	}

	/**
	 * Mimics the abstract classes method, determine the interest accrued by an
	 * account over a one month period, but with no parameter.
	 *
	 * @param d double amount in which account balance is to be changed
	 * @return interest accrued in the account over a month if the balance of of the
	 *         account was changed by d amount
	 */
	public double monthlyInterestAccrued(double d) {
		double b = this.balance + d;
		return b * (interest * 0.01) / 12.00;
	}

	/**
	 * Calls parent class toString, adds addition information to output
	 *
	 * @return String representation of class
	 */
	@Override
	public String toString() {
		String s = "Credit ";
		s += super.toString();
		s += "Minimum: " + minimum + ". ";
		return s;
	}
}
