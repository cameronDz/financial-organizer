package org.md.finance.organizer.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.md.finance.organizer.constants.FinancialConstant;
import org.md.finance.organizer.constants.FormatConstant;
import org.md.finance.organizer.models.CreditAccount;
import org.md.finance.organizer.models.SavingAccount;
import org.md.finance.organizer.models.UserFinancialAccountDetailModel;

public class FinancialAllocationService {

	public FinancialAllocationService() { }

	public String optimizedExcessPayments(UserFinancialAccountDetailModel details, Integer months) {
		List<CreditAccount> accounts = details.getAccounts();
		SavingAccount savings = details.getSavings();
		Double minimumMonthlyPayments = calculateTotalMinimumMonthlyPayments(accounts);
		Double userFunds = details.getFunds();
		String paymentPrintout = FinancialConstant.INSUFFICIENT_MONTHLY_FUNDS;
		if (minimumMonthlyPayments <= userFunds) {
			paymentPrintout = createOptimizedPrintout(accounts, savings, months);
		}
		return paymentPrintout;
	}

	/**
	 * Calculates the total minimum monthly payments of all CreditAccount by run through a loop
	 * checking account balances and incrementing the minimum amount of funds required to fulfill
	 * minimum payments
	 * @return double representing the total minimum monthly payment
	 */
	private Double calculateTotalMinimumMonthlyPayments(List<CreditAccount> accounts) {
		Double minimumPayment = 0.0;
		for (CreditAccount account : accounts) {
			Double balance = account.getBalance();
			Double monthMinimum = account.getMinimum();
			minimumPayment += (Math.abs(balance) < monthMinimum) ? balance : monthMinimum;
		}
		return minimumPayment;
	}

	private String createOptimizedPrintout(List<CreditAccount> accounts, SavingAccount savings, Integer months) {
		List<String> prints = createPlansPrintouts(accounts, savings);
		Double excess = 0.0;
		Double totalInterest = 0.0;
		// cycle through m months of interest/payments
		for (int monthInc = 0; monthInc < months; monthInc++) {
			// accumulate interest on each account and add statement to prints
			// make minimum monthly payments for each month

			monthlyInterestLoop(accounts, prints, (monthInc + 1), totalInterest);
			monthlyMinimumPaymentLoop(accounts, prints, (monthInc + 1));

			// optimized algorithm for minimum accured interest
			// create array of excess payments for each for account
			Double[] excessPayment = new Double[accounts.size()];

			// increment all excess funds by 0.01$ at a time and allocate the
			// funds to the account with the most interest being accrued
			Double counter = 0.0;
			while (counter < excess) {
				// position with the most accrued interest
				Integer greatestPosition = 0;
				Double greatestInterest = 0.0;
				for (int accountInc = 0; accountInc < accounts.size(); accountInc++) {
					// checks interest of account, replace if greater than current greatest
					Double thisInterest = accounts.get(accountInc).monthlyInterestAccrued(excessPayment[accountInc]);
					if (greatestInterest > thisInterest) {
						greatestPosition = accountInc;
						greatestInterest = thisInterest;
					}
				}

				// increment excress payment
				excessPayment[greatestPosition] += 0.01;
				// increment counter
				counter += 0.01;
			}

			for (int accountInc = 0; accountInc < accounts.size(); accountInc++) {
				CreditAccount account = accounts.get(accountInc);
				String type = FinancialConstant.OPTIMIZE_PAYMENT + (accountInc + 1);
				Double payment = excessPayment[accountInc];
				account.makePayment(payment);
				Double balance = account.getBalance();
				StringBuilder transactionBuilder = new StringBuilder();
				transactionBuilder.append(prints.get(accountInc));
				transactionBuilder.append(accountTransaction(type, payment, balance));
				transactionBuilder.append(FormatConstant.NEW_LINE);
				prints.set(accountInc, transactionBuilder.toString());
			}
		}
		return paymentPrintoutFinish(totalInterest, accounts, prints);
	}

	private void monthlyMinimumPaymentLoop(List<CreditAccount> accounts, List<String> prints, int month) {
		for (int accountInc = 0; accountInc < accounts.size(); accountInc++) {
			CreditAccount account = accounts.get(accountInc);
			String type = FinancialConstant.MONTHLY_MINIMUM + month;
			Double payment = account.getMinimum();
			account.makePayment(payment);
			Double balance = account.getBalance();
			String transaction = new StringBuilder()
					.append(prints.get(accountInc))
					.append(accountTransaction(type, payment, balance))
					.append(FormatConstant.NEW_LINE)
					.toString();
			prints.set(accountInc, transaction);
		}
	}

	private List<String> monthlyInterestLoop(List<CreditAccount> accounts, List<String> prints, Integer month, Double totalInterest) {
		Iterator<CreditAccount> accountIterator = accounts.iterator();
		Iterator<String> printIterator = prints.iterator();
		List<String> newPrints = new ArrayList<String>();
		while (accountIterator.hasNext() && printIterator.hasNext()) {
			CreditAccount account = accountIterator.next();
			String print = printIterator.next();

			String type = FinancialConstant.INTEREST_MONTH + month;
			Double interest = account.monthlyInterestAccrued();
			totalInterest += interest;
			account.accountTransaction(interest);
			Double balance = account.getBalance();
			String newPrint = print + accountTransaction(type, interest, balance) + FormatConstant.NEW_LINE;
			newPrints.add(newPrint);
		}
		return newPrints;
	}

	private String accountTransaction(String transactionType, Double amount, Double balance) {
		return String.format(FormatConstant.ACCOUNT_TRANSACTION_OUTPUT,
				FormatConstant.EMPTY_STRING,
				transactionType,
				FormatConstant.DOLLAR_DECIMAL.format(amount == null ? 0.0 : amount.doubleValue()),
				FormatConstant.DOLLAR_DECIMAL.format(balance == null ? 0.0 : balance.doubleValue()));
	}

	private String paymentPrintoutFinish(Double totalInterest, List<CreditAccount> accounts, List<String> prints) {
		StringBuilder sb = new StringBuilder();
		Double totalDebt = 0.0;

		// put all the account print outs into return string
		for (String print : prints) {
			sb.append(print)
			.append(FormatConstant.NEW_LINE)
			.append(FormatConstant.NEW_LINE);
		}
		for (CreditAccount account : accounts) {
			totalDebt += account.getBalance();
			sb.append(account.accountOverview());
		}

		// show total interst yielded
		sb.append(FinancialConstant.TOTAL_INTEREST)
				.append(FormatConstant.DOLLAR_DECIMAL.format(totalInterest))
				.append(FormatConstant.NEW_LINE)
				.append(FinancialConstant.TOTAL_DEBT)
				.append(FormatConstant.DOLLAR_DECIMAL.format(totalDebt))
				.append(FormatConstant.NEW_LINE);
		return sb.toString();
	}
	
	/**
	 * Creates the base of an array list that will store a budget plan for each
	 * account on the allocator. Starts each String as the accounts overview.
	 * @return ArrayList of Strings, each String being an account overview
	 */
	private ArrayList<String> createPlansPrintouts(List<CreditAccount> accounts, SavingAccount savings) {
		ArrayList<String> plans = new ArrayList<String>();
		// cycle through list of credit accounts
		for (int i = 0; i < accounts.size(); i++) {
			plans.add(accounts.get(i).accountOverview());
		}
		// add saving account to end of list
		plans.add(savings.accountOverview());
		return plans;
	}
}
