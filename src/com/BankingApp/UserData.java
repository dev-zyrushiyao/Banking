package com.BankingApp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class UserData implements Transaction {
	
	private double currentBalance = 0.0;
	public static final double minDepositAmount = 100.0;  
	public static final double minWithdrawAmount = 200.0; 
	
	private String messageDisplay = "Error ";
	private String error_1 = messageDisplay.concat("#1: Deposit is minimum of 100 peso(s)");
	private String error_2 = messageDisplay.concat("#2: Withdraw is minimum of 200 peso(s)");
	private String error_3 = messageDisplay.concat("#3: Balance should be equal or higher than withdraw amount");

	public UserData() {
		super();
		// TODO Auto-generated constructor stub
	}

	//Balance - View only
	public double getCurrentBalance() {
		return currentBalance;
	}
	
	//Deposit
	public void setCurrentBalance(double depositAmount) {
		if(depositAmount >= minDepositAmount) {
			userDepositPrint();
			this.currentBalance += depositAmount;
		}else {
			System.out.println("*------------------------------*");
			System.out.println(error_1);
			System.out.println("*------------------------------*");
		}
	}
	
	//Withdraw
	public void setWithdrawCurrentBalance(double withdrawAmount) {
		if(withdrawAmount >= minWithdrawAmount) {
			if(withdrawAmount <= currentBalance) {
				this.currentBalance -= withdrawAmount;
				userWithdrawPrint();			
			}else {
				System.out.println("*------------------------------*");
				System.out.println(error_3);
				System.out.println("*------------------------------*");
			}
		}else {
			System.out.println("*------------------------------*");
			System.out.println(error_2);
			System.out.println("*------------------------------*");
		}
	}
	
	@Override
	public void userDepositPrint() {
		System.out.println("*------------------------------*");
		System.out.println("Deposit transaction success!");
		System.out.println("*------------------------------*");
	}
	
	@Override
	public void userWithdrawPrint() {
		System.out.println("*------------------------------*");
		System.out.println("Withdraw transaction success!");
		System.out.println("*------------------------------*");	
	}
	
	
	
	
}
