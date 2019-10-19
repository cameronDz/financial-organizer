package org.md.finance.organizer.models;

import java.util.ArrayList;
import java.util.List;

public class UserFinancialDetailsModel {

	private List<String> accounts;
	private List<Double> balances, interests, monthly, rates;
	private Double funds;
	
	public UserFinancialDetailsModel() {
		accounts = new ArrayList<String>();
		balances = new ArrayList<Double>();
		interests = new ArrayList<Double>();
		monthly = new ArrayList<Double>();
		rates = new ArrayList<Double>();
		funds = 0.0;
	}

	public UserFinancialDetailsModel(
			List<String> accounts,
			List<Double> balances,
			List<Double> interests,
			List<Double> monthly,
			List<Double> rates,
			Double funds) {
		super();
		this.accounts = accounts;
		this.balances = balances;
		this.interests = interests;
		this.monthly = monthly;
		this.rates = rates;
		this.funds = funds;
	}

	public List<String> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<String> accounts) {
		this.accounts = accounts;
	}

	public List<Double> getBalances() {
		return balances;
	}

	public void setBalances(List<Double> balances) {
		this.balances = balances;
	}

	public List<Double> getInterests() {
		return interests;
	}

	public void setInterests(List<Double> interests) {
		this.interests = interests;
	}

	public List<Double> getMonthly() {
		return monthly;
	}

	public void setMonthly(List<Double> monthly) {
		this.monthly = monthly;
	}

	public List<Double> getRates() {
		return rates;
	}

	public void setRates(List<Double> rates) {
		this.rates = rates;
	}

	public Double getFunds() {
		return funds;
	}

	public void setFunds(Double funds) {
		this.funds = funds;
	}

	@Override
	public String toString() {
		return "UserFinancialDetailsModel [accounts=" + accounts + ", balances=" + balances + ", interests=" + interests
				+ ", monthly=" + monthly + ", rates=" + rates + ", funds=" + funds + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accounts == null) ? 0 : accounts.hashCode());
		result = prime * result + ((balances == null) ? 0 : balances.hashCode());
		result = prime * result + ((funds == null) ? 0 : funds.hashCode());
		result = prime * result + ((interests == null) ? 0 : interests.hashCode());
		result = prime * result + ((monthly == null) ? 0 : monthly.hashCode());
		result = prime * result + ((rates == null) ? 0 : rates.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserFinancialDetailsModel other = (UserFinancialDetailsModel) obj;
		if (accounts == null) {
			if (other.accounts != null)
				return false;
		} else if (!accounts.equals(other.accounts))
			return false;
		if (balances == null) {
			if (other.balances != null)
				return false;
		} else if (!balances.equals(other.balances))
			return false;
		if (funds == null) {
			if (other.funds != null)
				return false;
		} else if (!funds.equals(other.funds))
			return false;
		if (interests == null) {
			if (other.interests != null)
				return false;
		} else if (!interests.equals(other.interests))
			return false;
		if (monthly == null) {
			if (other.monthly != null)
				return false;
		} else if (!monthly.equals(other.monthly))
			return false;
		if (rates == null) {
			if (other.rates != null)
				return false;
		} else if (!rates.equals(other.rates))
			return false;
		return true;
	}
}
