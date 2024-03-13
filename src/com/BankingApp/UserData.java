package com.BankingApp;

public class UserData implements Transaction {
	
	private Double currentBalance = 0.0;

	public UserData() {
		super();
		// TODO Auto-generated constructor stub
	}

	//Balance - View only
	public Double getCurrentBalance() {
		return currentBalance;
	}
	
	//Deposit
	public void setDepositCurrentBalance(double depositAmount) {		
		this.currentBalance += depositAmount;
	}
	
	//Withdraw
	public void setWithdrawCurrentBalance(double withdrawAmount) {
		this.currentBalance -= withdrawAmount;
		
	}
	
	@Override
	public void printTransactionStatus(Enum<TransactionType> trasactionType) {
		System.out.println(trasactionType + " SUCCESS");
	}
	
	
	
	
	
}
