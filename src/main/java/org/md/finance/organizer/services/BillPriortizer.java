package org.md.finance.organizer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.md.finance.organizer.constants.FinancialConstant;

/**
 * Used to get several user account balances, and determine how to prioritize
 * how much to pay off from each account based on the amount of interest and
 * available funds.
 *
 * @author Cameron
 */
public class BillPriortizer {

	protected static List<String> accounts;
	protected static List<Double> balances, rates, interests, monthly;
	protected static double funds;

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {

		// holds all user input about accounts
		accounts = new ArrayList<>();
		balances = new ArrayList<>();
		rates = new ArrayList<>();
		interests = new ArrayList<>();
		monthly = new ArrayList<>();
		funds = 0.00;

		// method that asks for user accounts
		// getUserAccounts();

		// method that creates preset test accounts
		getTestAccounts();

		// print out user input back to them
		System.out.print("\n\n" + "=======================Account information" + "========================\n");
		System.out.print(formatString("Account name", "Balance", "Rate", "Yr. Int", "Min. Pay") + "\n");
		// loops through accounts index
		for (int i = 0; i < accounts.size(); i++) {
			String s = formatStringDouble(accounts.get(i), balances.get(i), rates.get(i), interests.get(i),
					monthly.get(i));
			System.out.print(s + "\n");
			yearlyInterest(balances.get(i), rates.get(i), monthly.get(i));
		}
		System.out.print("Funds: " + FinancialConstant.DOLLAR.format(funds) + "\n");

		// TODO add calculation for continuous compound interest

		// TODO create algorithm for computing amount to pay off

	}

	/**
	 * Users a scanner to get user accounts over command line
	 */
	public static void getUserAccounts() {

		// takes all user input
		Scanner scan = new Scanner(System.in);
		String input = "";

		// asks for user input of accounts
		System.out.print("The following will gather information about "
				+ "accounts that you currently owe funds towards." + "\n");
		while (!(input.equals("N"))) {
			// adds new account name to list of accounts names
			System.out.print("Enter account name: ");
			accounts.add(scan.next());

			// adds new account balance to list of balances
			System.out.print("Enter account balance (funds owed): ");
			balances.add(scan.nextDouble());

			// adds new account interest rate to list of interest rates
			System.out.print("Enter account interest rate: ");
			rates.add(scan.nextDouble());

			// adds new account minimum monthly payment
			System.out.print("Enter minimum monthly payment: ");
			monthly.add(scan.nextDouble());

			// calculate yearly interest accumulate with current debt
			int last = accounts.size() - 1;
			interests.add(balances.get(last) * rates.get(last) * .01);

			// asks user if they have another account to input
			System.out.print("Do you have another debted account to enter? ");
			System.out.print("\n" + "Press \"Y\" or \"N\": ");
			input = scan.next();
		}

		// asks for total available funds of the user
		System.out.print("Enter amount of funds avaible for monthly payment: ");
		funds = scan.nextDouble();
		scan.close();
	}

	/**
	 * Creates several fake accounts for testing purposes
	 */
	public static void getTestAccounts() {
		accounts.add("American Express");
		balances.add(4300.00);
		rates.add(12.0);
		monthly.add(50.00);
		interests.add(getInterest(4300.00, 12.0));

		accounts.add("Discover");
		balances.add(9900.00);
		rates.add(10.5);
		monthly.add(150.00);
		interests.add(getInterest(9900.00, 10.5));

		accounts.add("Connex Loan");
		balances.add(23500.00);
		rates.add(5.0);
		monthly.add(400.00);
		interests.add(getInterest(23500.00, 5.0));

		funds = 1200;
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
	 * Creates an output strong to be returned to user with account information used
	 * when doubles have not been converted to strings
	 *
	 * @param a accounts name
	 * @param b account balance, assumed to be negative
	 * @param r account interest rate
	 * @param i account interest occurred
	 * @param m account minimum monthly payment
	 * @return string to display to the user of all account information
	 */
	public static String formatStringDouble(String a, double b, double r, double i, double m) {

		// format doubles
		String balance = "-" + FinancialConstant.DOLLAR.format(b);
		balance = new StringBuilder(balance).insert(balance.length() - 2, "").toString();
		String rate = FinancialConstant.PERCENT.format(r / 100);
		rate = new StringBuilder(rate).insert(rate.length() - 2, "").toString();
		String interest = FinancialConstant.DOLLAR.format(i);
		interest = new StringBuilder(interest).insert(interest.length() - 2, "").toString();
		String month = FinancialConstant.DOLLAR.format(m);
		month = new StringBuilder(month).insert(month.length() - 2, "").toString();

		// calls formatString for Strings only
		return formatString(a, balance, rate, interest, month);
	}

	/**
	 * Format a monthly payment print out of an accounts interest
	 *
	 * @param b account balance
	 * @param i account interest
	 * @param p account payment amount
	 * @param n account new balance
	 * @return formatted print out
	 */
	public static String formatMonthlyString(double b, double i, double p, double n) {

		// empty String
		String empty = "";
		// format doubles
		String balance = "-" + FinancialConstant.DOLLAR.format(b);
		balance = new StringBuilder(balance).insert(balance.length() - 2, "").toString();
		String interest = FinancialConstant.DOLLAR.format(i);
		interest = new StringBuilder(interest).insert(interest.length() - 2, "").toString();
		String payment = FinancialConstant.DOLLAR.format(p);
		payment = new StringBuilder(payment).insert(payment.length() - 2, "").toString();
		String newBalance = FinancialConstant.DOLLAR.format(n);
		newBalance = new StringBuilder(newBalance).insert(newBalance.length() - 2, "").toString();

		return formatString(empty, balance, interest, payment, newBalance);
	}

	/**
	 * Creates an output strong to be returned to user with account information
	 *
	 * @param a account name
	 * @param b account balance
	 * @param r account interest rate
	 * @param i accumulated interest over 1 year
	 * @param m minimum monthly payment for account
	 * @return user information in String format
	 */
	public static String formatString(String a, String b, String r, String i, String m) {
		return String.format("%-18s %12s %8s %12s %12s", a, b, r, i, m);
	}

	/**
	 * Figure out and print yearly account balance after minimum monthly payments
	 *
	 * @param b account balance
	 * @param r account interest rate
	 * @param m account minimum monthly payment
	 */
	public static void yearlyInterest(double b, double r, double m) {

		double year = 0.0;
		// cycle throught 12 months of interest accumulation and minimum
		// monthly payments. cycle ends if balance reaches 0
		for (int i = 0; i < 12; i++) {
			// calculate interest
			double interest = b * (r / 12 * .01);
			double newBal = b;
			b += interest;
			if (b <= m) {
				newBal = 0;
				i = 12;
			} else {
				newBal = b - m;
			}

			// print out balance
			String s = formatMonthlyString(b, interest, m, newBal);
			System.out.print(s + "\n");

			// set b to new balance
			b = newBal;
			year += interest;
		}
		System.out.print("Yearly Interest: " + FinancialConstant.DOLLAR.format(year) + "\n");
	}
}
