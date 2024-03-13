package com.BankingApp;

@FunctionalInterface
public interface Transaction {
	
	public static final double minDepositAmount = 100.0;  
	public static final double minWithdrawAmount = 200.0; 
	
	public static String error_1 = String.format("ERROR #%s", "1: Deposit is minimum of 100 peso(s)");
	public static String error_2 = String.format("ERROR #%s", "2: Withdraw is minimum of 200 peso(s)");
	public static String error_3 = String.format("ERROR #%s", "3: Balance should be equal or higher than withdraw amount");
	
	void printTransactionStatus(Enum<TransactionType> transactionType);

}
