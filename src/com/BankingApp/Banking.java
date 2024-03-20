package com.BankingApp;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Banking {
	
	//[Global Variable]	
		//Get all the transaction file name of the user as Array
		private static ArrayList<String> userTransactionList = new ArrayList<>();
	
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
							System.out.println("[1]-Account Overview [2]-Deposit [3]-Withdraw [4]-Exit [5]-Print Transaction [6]-ExitApp");
							System.out.print("Input:");
							transaction = scanner.nextInt();
							System.out.println("*---------------------------------*");
							
							switch (transaction) {
								case 1: //Account Overview
									user.getAccountDetails();
									createReceipt(user, null, TransactionType.BAL_CHECK);
									break;
								case 2: //Deposit
									System.out.print("Deposit Amount:");
									Double depositAmount = scanner.nextDouble();
									
									//validation and execution
									validateTransaction(TransactionType.DEPOSIT, depositAmount, Transaction.minDepositAmount, user);
									break;
								case 3: //Withdraw
									System.out.print("Withdraw Amount:");
									Double withdrawAmount = scanner.nextDouble();
									
									//validation and execution
									validateTransaction(TransactionType.WITHDRAW, withdrawAmount , Transaction.minWithdrawAmount , user);
									break;
								case 4: //Read Receipt - using global variable as parameters
									System.out.println("Please input the transaction filename:");
									String transactionFileName = scanner.next();
									transactionFileName += scanner.nextLine(); //adding [White Space] "AM/PM"
							
									readReceipt(transactionFileName.concat(".txt"));
									
									break;
								case 5: //Show all the user's transaction - using global variable as parameters
									showAllTransaction(userTransactionList);
									break;
								case 6: //Exit App
									terminateApp(scanner);
									break;
								default: 
									System.out.println("Invalid mode");
								}
							} while (transaction !=6);
					
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
 	
 	static void createReceipt(User user , Double transactionAmount , Enum<TransactionType> transactionEnum) {
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
		
		//Add filenames to an [Global] arrayList [showAllTransaction()]
		userTransactionList.add(file.getName().replace(".txt", ""));
		
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
 	
 	static void readReceipt(String latestTransaction) {
 		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(latestTransaction));
			
			//Loop the reader and put it all to string if null then it reached the end of file-text
			String receipt;
			while((receipt = bufferedReader.readLine()) != null) {
				System.out.println(receipt);
			}
			bufferedReader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File not found.");
		} 
 	}
 	
 	static void showAllTransaction(ArrayList<String> userTransactionList) {
 		for(String list : userTransactionList) {
 			System.out.println(list);
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
					createReceipt(user, transactionAmount, transactionType);
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
					createReceipt(user, transactionAmount, transactionType);
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

