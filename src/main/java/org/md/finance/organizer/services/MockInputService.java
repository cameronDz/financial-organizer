package org.md.finance.organizer.services;

import org.md.finance.organizer.models.UserFinancialDetailsModel;

public class MockInputService {

	private FinancialCalculatorService financialCalculatorServce = new FinancialCalculatorService();

	public MockInputService() { }

	/**
	 * Creates several fake accounts for testing purposes
	 */
	public UserFinancialDetailsModel getMockFinancialDetails() {
		UserFinancialDetailsModel details = new UserFinancialDetailsModel();
		details.getAccounts().add("American Express");
		details.getBalances().add(4300.00);
		details.getRates().add(12.0);
		details.getMonthly().add(50.00);
		details.getInterests().add(financialCalculatorServce.getInterest(4300.00, 12.0));

		details.getAccounts().add("Discover");
		details.getBalances().add(9900.00);
		details.getRates().add(10.5);
		details.getMonthly().add(150.00);
		details.getInterests().add(financialCalculatorServce.getInterest(9900.00, 10.5));

		details.getAccounts().add("Connex Loan");
		details.getBalances().add(23500.00);
		details.getRates().add(5.0);
		details.getMonthly().add(400.00);
		details.getInterests().add(financialCalculatorServce.getInterest(23500.00, 5.0));
		details.setFunds(1200.0);
		return details;
	}
}
