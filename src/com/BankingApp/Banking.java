package com.BankingApp;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Banking {
 public static void main(String[] args) {

	 int mode;
		Scanner scanner = new Scanner(System.in);
	
	try {
		do {
			 System.out.println("*---------------------------------*");
			 System.out.println("Mode:");
			 System.out.println("[1]-Create your Account(Savings) [0]-Exit the app" );
			 System.out.print("Input:");
			 mode = scanner.nextInt(); 
			 System.out.println("*---------------------------------*");
			 
			 switch (mode) {
			 	case 0: 		 	
					terminateApp(scanner);
					break; //case0 [mode]
				case 1:
					System.out.print("Enter your name: ");
					String name = scanner.next();
					System.out.println("*---------------------------------*");
					System.out.println("Hello " + name);
					System.out.println("*---------------------------------*");
					User user = new User(name, AccountType.SAVINGS);

					int transaction;
						do {
							System.out.println("Choose a Transaction:");
							System.out.println("[1]-Account Overview [2]-Deposit [3]-Withdraw [4]-Exit [5]-Print Transaction [6]-ExitApp"); //To Readjust the Exit as Mode: 5
							System.out.print("Input:");
							transaction = scanner.nextInt();
							System.out.println("*---------------------------------*");
							
							switch (transaction) {
								case 1:
									user.getAccountDetails();
									printReceipt(user, null, TransactionType.BAL_CHECK);
									break;
								case 2:
									System.out.print("Deposit Amount:");
									Double depositAmount = scanner.nextDouble();
									
									//validation and execution
									validateTransaction(TransactionType.DEPOSIT, depositAmount, Transaction.minDepositAmount, user);
									break;
								case 3:
									System.out.print("Withdraw Amount:");
									Double withdrawAmount = scanner.nextDouble();
									
									//validation and execution
									validateTransaction(TransactionType.WITHDRAW, withdrawAmount , Transaction.minWithdrawAmount , user);
									break;
								case 4:
									System.out.println("Exit transaction");
									break;
								case 5:
									//to add file reader
									break;
								case 6:
									//triggering the exit
									terminateApp(scanner);
									break;
								default: 
									System.out.println("Invalid mode");
								}
							} while (transaction !=4);
					
					break; //case1 [mode]
				default: 
					System.out.println("Invalid command");
			 } 
		} while (mode != 0);
		
	} catch (InputMismatchException e) {
		//All inputMismatch for Scanner class
		System.out.println("Input mode error");
		e.printStackTrace();
	}
	
 } //end of main method
 	
 	static void printReceipt(User user , Double transactionAmount , Enum<TransactionType> transactionEnum) {
 		LocalDate localDate = LocalDate.now();
		
		//Local Time Format for receipt
		String timeColonPattern = "hh-mm-ss a";
		DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
		LocalTime localTime = LocalTime.now();
		
		//replaced ':' due to windows name formatting; toUppercase() for make 'AM/PM' capital letters
		String formattedTime = timeColonFormatter.format(localTime).toUpperCase();
		
		//File naming format: USER_DATE_MOVEMENT_TIME.TXT 
		String fileNamePattern = String.format("%s_%s_%s_%s.txt", user.getName().toUpperCase(), localDate, transactionEnum , formattedTime);
		File file = new File(fileNamePattern);
		
		//if file does not exist then make a new file [a condition to avoid updating a single text file]
		if(!file.exists()) {
			try {
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
				bufferedWriter.write("|-------------------------");
				bufferedWriter.write("\n|Date: " + localDate + 
						"\n|Time: " + formattedTime +
						"\n|UserID: " + user.getId() +  
						"\n|User:" + user.getName() +  
						"\n|AccountType: " + user.getAccountType() + 
						"\n|TransactionType: " + transactionEnum +  
						"\n|Transaction Amount: " + transactionAmount +
						"\n|Balance: " + user.getCurrentBalance() + 
						"\n");
				bufferedWriter.write("|-------------------------");
				bufferedWriter.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			System.out.println("FILE EXIST");
		}
 	}
 	
 	static void validateTransaction(Enum<TransactionType> transEnum, Double transactionAmount , double minimumAmount , User user) {
 		TransactionType transactionType = (TransactionType)transEnum;
 	
 		switch (transactionType) {
			case WITHDRAW:
				 if(transactionAmount < minimumAmount) {
					//Validation #1: Check if transaction is greater than the minimum amount required.
					 System.out.println(Transaction.error_2);
				 }else if(user.getCurrentBalance() >= transactionAmount) {
					//Validation #2: Check if the balance is greater than the transaction desired to avoid negative balance
					 user.setWithdrawCurrentBalance(transactionAmount);
					 user.printTransactionStatus(transactionType);
					 
					//print-generate receipt
					printReceipt(user, transactionAmount, transactionType );
				 }else {
					//Validation #3: Print the error that the balance should be equal or higher than the transaction
					 System.out.println(Transaction.error_3);
				 }	
				break;
			case DEPOSIT:
				if(transactionAmount < minimumAmount) {
					//Validation #1: if the transaction is below the minimum deposit required
					System.out.println(Transaction.error_1);
				}else {
					//Deposit the desired amount
					user.setDepositCurrentBalance(transactionAmount);
					user.printTransactionStatus(transactionType);
					printReceipt(user, transactionAmount, transactionType);
				}
				break;
			default:
				System.out.println("Invalid Enum Transaction Type");
				break;
		}
 	}
 	
	static void terminateApp(Scanner scanner) {
		System.out.println("Thank you for using the app");
		System.out.println("*---------------------------------*");
 		scanner.close();
 		System.exit(0);
 	}

}

