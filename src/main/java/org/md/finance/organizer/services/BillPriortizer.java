package org.md.finance.organizer.services;

import org.md.finance.organizer.constants.FinancialConstant;
import org.md.finance.organizer.models.UserFinancialDetailsModel;

/**
 * Used to get several user account balances, and determine how to prioritize
 * how much to pay off from each account based on the amount of interest and
 * available funds.
 *
 * @author Cameron
 */
public class BillPriortizer {

	// TODO add calculation for continuous compound interest
	// TODO create algorithm for computing amount to pay off

	private static final FinancialStringFormatterService financialStringFormatter = new FinancialStringFormatterService();

	public static void main(String[] args) {
		UserFinancialDetailsModel userFinancialDetails = new MockInputService().getMockFinancialDetails();

		// print out user input back to them
		StringBuilder sb = new StringBuilder()
				.append("\n\n")
				.append("=======================")
				.append("Account information")
				.append("========================")
				.append("\n")
				.append(financialStringFormatter.formatString("Account name", "Balance", "Rate", "Yr. Int", "Min. Pay"))
				.append("\n");
		// loops through accounts index
		for (int i = 0; i < userFinancialDetails.getAccounts().size(); i++) {
			sb.append(financialStringFormatter.formatStringDouble(
					userFinancialDetails.getAccounts().get(i),
					userFinancialDetails.getBalances().get(i),
					userFinancialDetails.getRates().get(i),
					userFinancialDetails.getInterests().get(i),
					userFinancialDetails.getMonthly().get(i)));
			sb.append("\n");
			sb.append(financialStringFormatter.yearlyInterest(
					userFinancialDetails.getBalances().get(i),
					userFinancialDetails.getRates().get(i),
					userFinancialDetails.getMonthly().get(i)));
		}

		sb.append("Funds: ");
		sb.append(FinancialConstant.DOLLAR.format(userFinancialDetails.getFunds()));
		sb.append("\n");
		System.out.println(sb.toString());
	}
}
