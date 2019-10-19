package org.md.finance.organizer.services;

import org.md.finance.organizer.constants.FormatConstant;

public class FinancialStringFormatterService {
	
	public FinancialStringFormatterService() { }

	/**
	 * Creates an output strong to be returned to user with account information used
	 * when doubles have not been converted to strings
	 *
	 * @param accountName String accounts name
	 * @param balance Double account balance, assumed to be negative
	 * @param interestRate Double account interest rate
	 * @param interest Double account interest occurred
	 * @param monthlyPayment Double account minimum monthly payment
	 * @return string to display to the user of all account information
	 */
	public String formatStringDouble(String accountName, Double balance, Double interestRate, Double interest, Double monthlyPayment) {
		return formatString(accountName, 
				FormatConstant.DOLLAR_DECIMAL.format(balance),
				FormatConstant.PERCENT_DECIMAL.format(interestRate / 100),
				FormatConstant.DOLLAR_DECIMAL.format(interest),
				FormatConstant.DOLLAR_DECIMAL.format(monthlyPayment));
	}

	/**
	 * Format a monthly payment print out of an accounts interest
	 *
	 * @param balance account balance
	 * @param interest account interest
	 * @param payment account payment amount
	 * @param newBalance account new balance
	 * @return formatted print out
	 */
	public String formatMonthlyString(Double balance, Double interest, Double payment, Double newBalance) {
		return formatString(FormatConstant.EMPTY_STRING,
				FormatConstant.DOLLAR_DECIMAL.format(balance),
				FormatConstant.DOLLAR_DECIMAL.format(interest),
				FormatConstant.DOLLAR_DECIMAL.format(payment),
				FormatConstant.DOLLAR_DECIMAL.format(newBalance));
	}

	/**
	 * Figure out and print yearly account balance after minimum monthly payments
	 *
	 * @param balance account balance
	 * @param rate account interest rate
	 * @param minimumPayment account minimum monthly payment
	 */
	public String yearlyInterest(Double balance, Double rate, Double minimumPayment) {
		StringBuilder sb = new StringBuilder();
		Double year = 0.0;
		// cycle throught 12 months of interest accumulation and minimum
		// monthly payments. cycle ends if balance reaches 0
		for (int i = 0; i < 12; i++) {
			// calculate interest
			Double interest = balance * (rate / 12 * .01);
			Double newBal = balance;
			balance += interest;
			if (balance <= minimumPayment) {
				newBal = 0.0;
				i = 12;
			} else {
				newBal = balance - minimumPayment;
			}

			// print out balance
			sb.append(formatMonthlyString(balance, interest, minimumPayment, newBal));
			sb.append("\n");

			// set b to new balance
			balance = newBal;
			year += interest;
		}
		sb.append("Yearly Interest: ");
		sb.append(FormatConstant.DOLLAR_DECIMAL.format(year));
		sb.append("\n");
		return sb.toString();
	}

	/**
	 * Creates an output strong to be returned to user with account information
	 *
	 * @param name String account name
	 * @param balance String account balance
	 * @param interest String account interest rate
	 * @param accumulatedInterest String accumulated interest over 1 year
	 * @param payment String minimum monthly payment for account
	 * @return user information in String format
	 */
	public String formatString(String name, String balance, String interest, String accumulatedInterest, String payment) {
		return String.format(FormatConstant.BASIC_ACCOUNT_OUTPUT, name, balance, interest, accumulatedInterest, payment);
	}
}
