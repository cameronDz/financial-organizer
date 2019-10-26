package org.md.finance.organizer.services;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.md.finance.organizer.models.CreditAccount;
import org.md.finance.organizer.models.SavingAccount;
import org.md.finance.organizer.models.UserFinancialAccountDetailModel;

public class FinancialAllocationServiceTest {

	private static final FinancialAllocationService SERVICE = new FinancialAllocationService();

	@Test
	public void test() {
        List<CreditAccount> accounts = Arrays.asList(
        		 new CreditAccount("Discover", -10000.00, 10.0, 100.0),
        		 new CreditAccount("American Express", -4400.00, 14.0, 100.0),
        		 new CreditAccount("Connex Car Loan", -20000.00, 5.0, 200.0));
        SavingAccount liberty = new SavingAccount("Liberty Savings", 100.00, 0.0);
        Double funds = 2300.00;
        UserFinancialAccountDetailModel details = new UserFinancialAccountDetailModel(accounts, liberty, funds);
        String output = SERVICE.optimizedExcessPayments(details, 7);
        System.out.println("Output: " + output);
	}
}
