package com.codeaches.java8.examples;

public class Account {

	String name;
	String accountNumber;
	String accountType;
	Double balance;

	@Override
	public String toString() {
		return "[name=" + name + ", accountNumber=" + accountNumber + ", accountType=" + accountType + ", balance="
				+ balance + "]";
	}

	public Account(String name, String accountNumber, String accountType, Double balance) {
		this.name = name;
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.balance = balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
}
