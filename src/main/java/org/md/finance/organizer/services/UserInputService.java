package org.md.finance.organizer.services;

import java.util.Scanner;

import org.md.finance.organizer.constants.UserInputConstant;
import org.md.finance.organizer.models.UserFinancialDetailsModel;

public class UserInputService {

	public UserInputService() {}

	public UserFinancialDetailsModel getUserFinancialDetails() {
		UserFinancialDetailsModel details = new UserFinancialDetailsModel();
		// takes all user input
		Scanner scan = new Scanner(System.in);
		String input = "";

		// asks for user input of accounts
		System.out.print(UserInputConstant.INITIAL_PROMPT_MESSAGE);
		while (!(input.equals(UserInputConstant.NO))) {
			System.out.print(UserInputConstant.ENTER_ACCOUNT_NAME);
			details.getAccounts().add(scan.next());

			System.out.print(UserInputConstant.ENTER_ACCOUNT_BALANCE);
			details.getBalances().add(scan.nextDouble());

			System.out.print(UserInputConstant.ENTER_ACCOUNT_INTEREST);
			details.getRates().add(scan.nextDouble());

			System.out.print(UserInputConstant.ENTER_MINIMUM_MONTHLY);
			details.getMonthly().add(scan.nextDouble());

			int last = details.getAccounts().size() - 1;
			details.getInterests().add(details.getBalances().get(last) * details.getRates().get(last) * .01);

			System.out.println(UserInputConstant.ENTER_ANOTHER_ACCOUNT);
			System.out.print(UserInputConstant.PRESS_YES_NO);
			input = scan.next();
		}

		// asks for total available funds of the user
		System.out.print(UserInputConstant.ENTER_FUNDS_AVAILABLE);
		details.setFunds(scan.nextDouble());
		scan.close();

		return details;
	}
}
