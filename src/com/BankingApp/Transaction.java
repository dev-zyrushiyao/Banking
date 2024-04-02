package com.BankingApp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.BankingApp.Account.SavingsAccount;
import com.BankingApp.Account.User;

public interface Transaction {
	
	public static void accountOverView(User user) {
		System.out.println("*---------------------------------*");
		System.out.println(String.format("Account ID: %d", user.getUserID()));
		System.out.println(String.format("Name: %s", user.getUsername()));
		System.out.println(String.format("Type: %s" , user.getAccountType()));
		System.out.println(String.format("Balance: %s" , user.getBalance()));
		System.out.println("*---------------------------------*");	
	}
	
	//Deposit - accessed by validateDeposit()
	private static void deposit(User user , long amount) {		
		user.depositBalance(amount);
	}
			
	//Withdraw - accessed by validateWithdraw()
	private static void withdraw(User user , long withdrawAmount) {
		user.withdrawBalance(withdrawAmount);
	}
	
	public static void validateDeposit(User user, long transactionAmount) {
		if(transactionAmount < SavingsAccount.minDepositAmount) {
			//Validation #1: if the transaction is below the minimum deposit required
			System.out.println("*---------------------------------*");
			System.out.println("ERROR: Deposit is minimum of 100 peso(s)");
			System.out.println("*---------------------------------*");
		}else {
			//Deposit the desired amount
			deposit(user, transactionAmount);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Transaction success!");
			createReceipt(user, transactionAmount, TransactionType.DEPOSIT);
		}
	}

	public static void validateWithdraw(User user, long transactionAmount) {
		if(transactionAmount < SavingsAccount.minWithdrawAmount) {
			//Validation #1: Check if transaction is greater than the minimum amount required.
			System.out.println("*---------------------------------*");
			System.out.println("ERROR: Withdraw is minimum of 200 peso(s)");
			System.out.println("*---------------------------------*");
		}else if(user.getBalance() >= transactionAmount) {
			//Validation #2: Check if the balance is greater than the transaction desired to avoid negative balance
			withdraw(user , transactionAmount);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Transaction success!");	
			createReceipt(user, transactionAmount, TransactionType.WITHDRAW);
		}else {
			//Validation #3: Print the error that the balance should be equal or higher than the transaction
			System.out.println("*---------------------------------*");
			System.out.println("ERROR: Insufficient balance");
			System.out.println("*---------------------------------*");
		}
	}
	
	public static void balanceCheck(User user) {
		createReceipt(user, null, TransactionType.BAL_CHECK);
	}
	
	//create a folder of transaction of a user - returns a file path(target folder)
	private static String userTransactionFolder(User user, LocalDate localDate) {
 		final String transaction_UserPath = "./account_data/transaction/" + user.getUsername() + "/" + localDate;
		File bank_userFolder = new File(transaction_UserPath);
		bank_userFolder.mkdirs();
		
		return transaction_UserPath;
	}
	
 	public static void createReceipt(User user , Long transactionAmount , TransactionType transactionType) {	
 		LocalDate localDate = LocalDate.now();
	
		//Local Time Format for receipt
		String timeColonPattern = "hh-mm-ss a";
		DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
		LocalTime localTime = LocalTime.now();
	
		//replaced ':' due to windows name formatting; 
		//toUppercase() for make 'AM/PM' capital letters
		String formattedTime = timeColonFormatter.format(localTime).toUpperCase();
		
		String targetFolderPath = userTransactionFolder(user , localDate );
		
		//File naming format: USER_DATE_MOVEMENT_TIME.TXT 
		String fileNamePattern = String.format("%s_%s_%s_%s.txt", user.getUsername().toUpperCase(), localDate, transactionType , formattedTime);
		File file = new File(targetFolderPath, fileNamePattern);
	
		
		//if file does not exist then make a new file 
		//[a condition to avoid updating a single text file]
		if(!file.exists()) {
			try {
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
				bufferedWriter.write("|-------------------------");
				bufferedWriter.write("\n|Date: " + localDate + 
						"\n|Time: " + formattedTime +
						"\n|UserID: " + user.getUserID() +  
						"\n|User:" + user.getUsername() +  
						"\n|AccountType: " + user.getAccountType() + 
						"\n|TransactionType: " + transactionType +  
						"\n|Transaction Amount: " + transactionAmount +
						"\n|Balance: " + user.getBalance() + 
						"\n");
				bufferedWriter.write("|-------------------------");
				bufferedWriter.close();
				readReceipt(file);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			System.out.println("FILE EXIST");			
		}		
	}
 	
 	//Accessed by createReceipt() 
 	private static void readReceipt(File file) throws InterruptedException {
 		
 		System.out.println("*---------------------------------*");
		System.out.println("Printing receipt");
		System.out.println("*---------------------------------*");
		
		//loading loop sequence
		for(int i=5; i >=1; i--) {
			System.out.print(i);
			
			for(int j=1; j<= i; j++){
				System.out.print(".");	
			}
			
			Thread.sleep(1000);
			System.out.println("");
		}
		
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
		
			//Loop the reader and put it all to string if null then it reached the end of file-text
			//credits to Youtube:'Coding with John'
			String receipt;
			while((receipt = bufferedReader.readLine()) != null) {
				System.out.println(receipt);
			}
			bufferedReader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File not found.");
			System.out.println("Please check the user transaction list and copy/paste a single transaction to read");
		} 
	}
 	
}
