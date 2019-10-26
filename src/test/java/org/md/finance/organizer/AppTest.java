package org.md.finance.organizer;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import org.md.finance.organizer.models.CreditAccount;
import org.md.finance.organizer.models.SavingAccount;
import org.md.finance.organizer.services.FundAllocator;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    public void shouldAnswerWithTrue()
    {
        
        // sets test accounts
        CreditAccount discover = new CreditAccount(
                "Discover", -10000.00, 10.0, 100.0);
        CreditAccount amex = new CreditAccount(
                "American Express", -4400.00, 14.0, 100.0);
        CreditAccount connex = new CreditAccount(
                "Connex Car Loan", -20000.00, 5.0, 200.0);
        SavingAccount liberty = new SavingAccount(
                "Liberty Savings", 100.00, 0.0);
        
        // test accountOverview method
        System.out.print( discover.accountOverview() );
        System.out.print( amex.accountOverview() );
        System.out.print( connex.accountOverview() );
        System.out.print( liberty.accountOverview() );
                
        // test accountTransaction method
        String interest = "Monthly Interest";
        String check = "Balance check";
        System.out.print( discover.accountTransaction(interest, -100.00) );
        System.out.print( amex.accountTransaction(interest, -100.00) );
        System.out.print( connex.accountTransaction(check, 0.0) );
        System.out.print( liberty.accountTransaction(interest, 1.00) );
        
        // create fund allocator
        ArrayList<CreditAccount> accounts = new ArrayList<>();
        double funds = 2300.00;
        accounts.add(discover);
        //accounts.add(amex);
        accounts.add(connex);
        FundAllocator allocator = new FundAllocator(accounts, liberty, funds);
        
        // tests for fund allocator
        System.out.print( allocator.toString() );
        
        // test monthly budget with even excess payments (2185 @ 8) (1669) (587)
        //System.out.print( allocator.evenExcessPayments(4) );
        
        // test monthly budget with optimized excess payments (2179 @ 8) (1559) (595)
        //System.out.print( allocator.optimizedExcessPayments(4) );
        
        // test monthly budget with high interest excess payments (2148 @ 8) (1388)
        System.out.print( allocator.highInterestExcessPayments(4) );
        
        assertTrue( true );
    }
}
