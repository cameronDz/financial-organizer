package org.md.finance.organizer;

import org.md.finance.organizer.constants.FinancialConstant;
import org.md.finance.organizer.constants.FormatConstant;
import org.md.finance.organizer.models.UserFinancialDetailsModel;
import org.md.finance.organizer.services.FinancialStringFormatterService;
import org.md.finance.organizer.services.MockInputService;

/**
 * Application entry point.  Used to get several user account balances, and determine how to
 * prioritize how much to pay off from each account based on the amount of interest and available
 * funds.
 */
public class App {

	public static void main(String[] args) {
		FinancialStringFormatterService financialStringFormatter = new FinancialStringFormatterService();
		UserFinancialDetailsModel userFinancialDetails = new MockInputService().getMockFinancialDetails();

		// print out user input back to them
		StringBuilder sb = new StringBuilder()
				.append(FormatConstant.NEW_LINE)
				.append(FormatConstant.NEW_LINE)
				.append(FinancialConstant.ACCOUNT_INFORMATION)
				.append(FormatConstant.NEW_LINE)
				.append(financialStringFormatter.formatString(
						FinancialConstant.ACCOUNT_NAME,
						FinancialConstant.BALANCE,
						FinancialConstant.RATE,
						FinancialConstant.ABBR_YEARLY_INTEREST,
						FinancialConstant.ABBR_MINIMUM_PAYMENT))
				.append(FormatConstant.NEW_LINE);

		// loops through accounts index
		for (int i = 0; i < userFinancialDetails.getAccounts().size(); i++) {
			String financialInfo = financialStringFormatter.formatStringDouble(
					userFinancialDetails.getAccounts().get(i),
					userFinancialDetails.getBalances().get(i),
					userFinancialDetails.getRates().get(i),
					userFinancialDetails.getInterests().get(i),
					userFinancialDetails.getMonthly().get(i));
			String yearlyInterest = financialStringFormatter.yearlyInterest(
					userFinancialDetails.getBalances().get(i),
					userFinancialDetails.getRates().get(i),
					userFinancialDetails.getMonthly().get(i));
			sb.append(financialInfo)
					.append(FormatConstant.NEW_LINE)
					.append(yearlyInterest);
		}

		sb.append(FinancialConstant.FUNDS)
				.append(FormatConstant.DOLLAR_DECIMAL.format(userFinancialDetails.getFunds()))
				.append(FormatConstant.NEW_LINE);
		System.out.println(sb.toString());

		// TODO add calculation for continuous compound interest
		// TODO create algorithm for computing amount to pay off
	}
}
