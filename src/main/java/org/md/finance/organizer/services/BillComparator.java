package org.md.finance.organizer.services;

import org.md.finance.organizer.constants.FormatConstant;

/**
 * Compares 2 pre set accounts and determines how to allocate funds on a monthly
 * basis
 *
 * @author Cameron
 */
public class BillComparator {


	// account variables
	protected static String[] act = new String[2];
	protected static double[] balance = new double[2];
	protected static double[] rate = new double[2];
	protected static double[] pay = new double[2];

	protected static double funds;

	/**
	 * Runs the file.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {

		// set fake act data for testing
		setTestActs();

		System.out.print("MINIMUM MONTHLY PAYMENT" + "\n");
		// cycle through 12 months of payments for the two accounts at
		// minimum monthly payment
		for (int j = 0; j <= 1; j++) {
			double total = 0.0;
			for (int i = 0; i < 12; i++) {
				// calculate this cycles interest amount
				double monthlyInt = getInterest(balance[j], rate[j]) / 12;

				// print formatted information to user
				System.out.print(act[j] + " Month " + (1 + i));
				System.out.print(formatInterest(balance[j], monthlyInt, pay[j]) + "\n");

				// perform balance calculations
				balance[j] = balance[j] + monthlyInt - pay[j];
				total += monthlyInt;
			}

			// print out total interest amount
			System.out.print(act[j] + " - Total Interest: " + FormatConstant.DOLLAR_DECIMAL.format(total) + "\n");
		}

		// reset fake test accounts
		setTestActs();
		System.out.print("MONTHLY PAYMENT w/ ADDITION FUNDS" + "\n");
		// cycle through 12 months of payments for the two accounts with
		// funds variable evenly split between the two accounts
		for (int j = 0; j <= 1; j++) {
			double total = 0.0;
			for (int i = 0; i < 12; i++) {
				// calculate this cycles interest amount
				double monthlyInt = getInterest(balance[j], rate[j]) / 12;

				// print formatted information to user
				System.out.print(act[j] + " Month " + (1 + i));
				System.out.print(formatInterest(balance[j], monthlyInt, pay[j] + (funds / 2)) + "\n");

				// perform balance calculations
				balance[j] = balance[j] + monthlyInt - pay[j] - (funds / 2);
				total += monthlyInt;
			}

			// print out total interest amount
			System.out.print(act[j] + " - Total Interest: " + FormatConstant.DOLLAR_DECIMAL.format(total) + "\n");
		}

		// reset fake test accounts
		setTestActs();
		System.out.print("MONTHLY PAYMENT w/ ADDITION FUNDS - OPTIMIZED" + "\n");
		double optTotal = 0.0;
		// cycle through 12 months of payments for the two accounts with
		// funds variable optimized for accumulating mimumal amount of interest
		for (int i = 0; i < 12; i++) {
			double excess = funds;

			// cycle through accounts removing minimum payments from balances
			for (int j = 0; j <= 1; j++) {
				// add monthly interest to each balance
				double monthlyInt = getInterest(balance[j], rate[j]) / 12;
				balance[j] += monthlyInt;
				optTotal += monthlyInt;

				// check if balance is less than minimum payment
				if (balance[j] < pay[j]) {
					// subtract remaining balance from excess funds and
					// set new balance to 0
					excess -= balance[j];
					balance[j] = 0;
				} else {
					// subtract minimum payment from balance and excess funds
					balance[j] -= pay[j];
					excess -= pay[j];
				}
			}

			// allocate excess funds to minimize amount of interest accrued
			if (excess > 0) {

				// check if excess funds can pay off both accounts
				if (excess >= balance[0] + balance[1]) {
					// sets account balances to 0 if excess covers both amounts
					excess -= (balance[0] + balance[1]);
					balance[0] = 0;
					balance[1] = 0;
				} else {
					// attempts to allocate funds of both accounts until
					// interest is as close as possible for each account
					int excessPercent = 0;
					double difference = 0.00;

					// loop from 0 to 99 checking to see which percent yields
					// the least amount of accrued interest
					for (int j = 0; j < 100; j++) {
						// determine total interest funds between two accounts
						double[] excessPay = new double[2];
						excessPay[0] = balance[0] - (excess * j / 100);
						excessPay[1] = balance[1] - (excess * (100 - j) / 100);
						double totalInterest = excessPay[0] + excessPay[1];

						// set least amount of interest for use optimizing
						if (difference > totalInterest || j == 0) {
							excessPercent = j;
							difference = totalInterest;
						}
					}

					// remove optimal percent of excess funds from accounts
					balance[0] = balance[0] - (excess * excessPercent / 100);
					balance[1] = balance[1] - (excess * (100 - excessPercent) / 100);
				}
			}

			// print out monthly account status
			System.out.print(act[0] + " " + FormatConstant.DOLLAR_DECIMAL.format(balance[0]) + "\n");
			System.out.print(act[1] + " " + FormatConstant.DOLLAR_DECIMAL.format(balance[1]) + "\n");

		}
		// print out two account balances and total interest - optimized
		for (int j = 0; j <= 1; j++) {
			System.out.print(act[j] + " " + FormatConstant.DOLLAR_DECIMAL.format(balance[j]) + "\n");
		}
		System.out.print("Total interest accrued: " + FormatConstant.DOLLAR_DECIMAL.format(optTotal) + "\n");

	}

	/**
	 * Calculates interest yields in test account method
	 *
	 * @param b balance of account
	 * @param r interest rate of account
	 * @return interest occurred over a year
	 */
	public static double getInterest(double b, double r) {
		return b * r * .01;
	}

	/**
	 * Sets fake accounts for testing purposes
	 */
	public static void setTestActs() {
		act[0] = "Connex Car Loan";
		balance[0] = 23500.00;
		rate[0] = 6.0;
		pay[0] = 388.50;

		act[1] = "Discover       ";
		balance[1] = 9900.00;
		rate[1] = 12.0;
		pay[1] = 175.00;

		funds = 600.00;
	}

	/**
	 * Takes in a month of occurred interests and formats it for output
	 *
	 * @param b balance of an account
	 * @param i interest occurred on an account
	 * @param m payment made to account
	 * @return a string formatted for user output of interest occurred
	 */
	public static String formatInterest(double b, double i, double m) {
		String empty = "";
		String sum = FormatConstant.DOLLAR_DECIMAL.format(b);
		String interest = FormatConstant.DOLLAR_DECIMAL.format(i);
		String month = FormatConstant.DOLLAR_DECIMAL.format(m);
		String remain = FormatConstant.DOLLAR_DECIMAL.format(b + i - m);

		return String.format("%5s %12s %12s %12s %12s", empty, sum, interest, month, remain);
	}
}
