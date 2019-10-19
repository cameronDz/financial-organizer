package org.md.finance.organizer.services;

import org.md.finance.organizer.constants.FinancialConstant;
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
				FinancialConstant.DOLLAR.format(balance),
				FinancialConstant.PERCENT.format(interestRate / 100),
				FinancialConstant.DOLLAR.format(interest),
				FinancialConstant.DOLLAR.format(monthlyPayment));
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
				FinancialConstant.DOLLAR.format(balance),
				FinancialConstant.DOLLAR.format(interest),
				FinancialConstant.DOLLAR.format(payment),
				FinancialConstant.DOLLAR.format(newBalance));
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
