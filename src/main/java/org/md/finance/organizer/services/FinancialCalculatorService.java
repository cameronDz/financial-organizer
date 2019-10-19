package org.md.finance.organizer.services;

public class FinancialCalculatorService {

	public FinancialCalculatorService() {}

	/**
	 * Calculates interest yields in test account method
	 *
	 * @param balance Double balance of account
	 * @param interestRate Double interest rate of account
	 * @return interest occurred over a year
	 */
	public Double getInterest(Double balance, Double interestRate) {
		Double interest = 0.0;
		if (balance != null && interestRate != null) {
			interest = balance * interestRate * .01;
		}
		return interest;
	}
}
