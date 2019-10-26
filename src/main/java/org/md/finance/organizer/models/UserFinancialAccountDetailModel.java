package org.md.finance.organizer.models;

import java.util.ArrayList;
import java.util.List;

public class UserFinancialAccountDetailModel {

	private List<CreditAccount> accounts;
	private SavingAccount savings;
	private Double funds;

	public UserFinancialAccountDetailModel() {
		super();
		this.accounts = new ArrayList<CreditAccount>();
		this.savings = new SavingAccount();
		this.funds = 0.0;
	}

	public UserFinancialAccountDetailModel(List<CreditAccount> accounts, SavingAccount savings, Double funds) {
		super();
		this.accounts = accounts;
		this.savings = savings;
		this.funds = funds;
	}

	public List<CreditAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<CreditAccount> accounts) {
		this.accounts = accounts;
	}

	public SavingAccount getSavings() {
		return savings;
	}

	public void setSavings(SavingAccount savings) {
		this.savings = savings;
	}

	public Double getFunds() {
		return funds;
	}

	public void setFunds(Double funds) {
		this.funds = funds;
	}

	@Override
	public String toString() {
		return "UserFinancialAccountDetailModel [accounts=" + accounts + ", savings=" + savings + ", funds=" + funds
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accounts == null) ? 0 : accounts.hashCode());
		result = prime * result + ((funds == null) ? 0 : funds.hashCode());
		result = prime * result + ((savings == null) ? 0 : savings.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		UserFinancialAccountDetailModel other = (UserFinancialAccountDetailModel) obj;
		if (accounts == null) {
			if (other.accounts != null) {
				return false;
			}
		} else if (!accounts.equals(other.accounts)) {
			return false;
		}
		if (funds == null) {
			if (other.funds != null) {
				return false;
			}
		} else if (!funds.equals(other.funds)) {
			return false;
		}
		if (savings == null) {
			if (other.savings != null) {
				return false;
			}
		} else if (!savings.equals(other.savings)) {
			return false;
		}
		return true;
	}
}
