package com.BankingApp.Account;

import java.io.Serializable;

public class User extends SavingsAccount implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private long balance = 0L;
	private AccountType accountType;
	private long userID;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String username, String password , AccountType accountType) {
		super();
		this.username = username;
		this.password = password;
		this.accountType = accountType;
		this.userID = createID();
	}
	
	public long createID() {
		 return (long) Math.floor((Math.random()* 999999999));		 
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getBalance() {
		return balance;
	}
	
	//deposit accessed via Transaction-interface method parameter
	public long depositBalance(long balance) {
		return this.balance += balance;
	}
	
	//withdraw accessed via Transaction-interface method parameter
	public long withdrawBalance(long balance) {
		return this.balance -= balance;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public long getUserID() {
		return userID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", balance=" + balance + ", accountType="
				+ accountType + ", userID=" + userID + "]";
	}
	
}
