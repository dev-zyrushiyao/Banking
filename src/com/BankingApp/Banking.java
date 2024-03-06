package com.BankingApp;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
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
			 			scanner.close();
			 			System.out.println("Thank you for using the app");
					break; //case0 [mode]
				case 1:
					System.out.print("Enter your name: ");
					String name = scanner.next();
					System.out.println("Hello " + name);
					User user = new User(name, AccountType.SAVINGS);

					int transaction;
						do {
							System.out.println("Choose a Transaction:");
							System.out.println("[1]-Account Overview [2]-Deposit [3]-Withdraw [4]-Exit [5]-Print Transaction"); //To Readjust the Exit as Mode: 5
							System.out.print("Input:");
							transaction = scanner.nextInt();
								
							switch (transaction) {
								case 1:
									user.getAccountDetails();
									break;
								case 2:
									System.out.print("Deposit Amount:");
									double depositAmount = scanner.nextDouble();
									user.setCurrentBalance(depositAmount);
									break;
								case 3:
									System.out.println("Withdraw Amount:");
									double withdrawAmount = scanner.nextDouble();
									user.setWithdrawCurrentBalance(withdrawAmount);
									
									//printing Receipt -> For Method to fix + implement time + add static double on main class + Make Enum Transaction Type
										try {
											LocalDate localDate = LocalDate.now();
											BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Widthraw_Receipt.txt"));
											bufferedWriter.write("--------------------------");
											bufferedWriter.write("\n|Date: " + localDate + 
																 "\n|UserID: " + user.getId() +  
																 "\n|User:" + user.getName() +  
																 "\n|AccountType: " + user.getAccountType() + 
																 "\n|TransactionType: WITHDRAW" + 
																 "\n|Withdraw Amount " + withdrawAmount + 
																 "\n|Balance: " + user.getCurrentBalance() + 
																 "\n");
											bufferedWriter.write("--------------------------");
											bufferedWriter.close();
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									
									break;
								case 4:
									System.out.println("Exit transaction");
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
		System.out.println("Input mode error");
		e.printStackTrace();
		
	}
	
	

 	}
 

}

