package org.md.finance.organizer.services;

import static org.junit.Assert.*;

import org.junit.Test;

public class FinancialStringFormatterServiceTest {

	private static final FinancialStringFormatterService SERVICE = new FinancialStringFormatterService();

	@Test
	public void formatMonthlyString() {
		Double balance = 100.0;
		Double interest = 10.0;
		Double payment = 25.0;
		Double newBalance = 125.0;
		String actual = SERVICE.formatMonthlyString(balance, interest, payment, newBalance);
		String expected = "                        $100.00   $10.00       $25.00      $125.00";
		assertEquals(actual, expected);
	}
}
