package com.BankingApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.Scanner;

import com.BankingApp.Account.AccountType;
import com.BankingApp.Account.User;

public class Banking implements Transaction {
	
	public static void main(String[] args) {
		
		createFolder();
		
		Scanner scanner = new Scanner(System.in);
		try {	
			System.out.println("Banking App");

			for(Mode modes : Mode.values()) {
				System.out.print(String.format("[%d]-%s ", modes.ordinal() , modes));
			}
			
			System.out.println("");
			System.out.print("Choose Action:");
			int action = scanner.nextInt();
			Mode mode = Mode.values()[action];
			
			switch(mode) {
			case LOGIN:
				
				boolean isLoginSuccess = false;
				do { 
					System.out.println("*-----Sign in-----*");
					System.out.print("username:");
					String login_username = scanner.next();
					System.out.print("password:");
					String login_password = scanner.next();
					
					User user = retrieveUserAccount(login_username);
					if(user == null) {
						//non-existent account
						System.out.println("username/password is incorrect");
					}else {
						if(user.getUsername().equals(login_username)&&
								user.getPassword().equals(login_password)) {
							
							isLoginSuccess = true;
							bankBusinessLogic(user);
						}else {
							//wrong credentials
							System.out.println("username/password is incorrect");
						}
					}
				}while(isLoginSuccess != true);
				break;
			case REGISTER:
				boolean isRegSuccess = false;
				do {
					System.out.println("*-----Sign up-----*");
					System.out.print("username:");
					String reg_username = scanner.next();
					System.out.print("password:");
					String reg_password = scanner.next();
					
					isRegSuccess = registerUser(reg_username, reg_password , AccountType.SAVINGS) ;
				}while(isRegSuccess != true);
				break;
			case EXIT:
				terminateApp(scanner);
				break;
			}
		}catch(Exception e){
			scanner.close();
			System.out.println("Unrecognize command");
		}
	}
		
	public static void bankBusinessLogic(User user) {
		
		boolean isUserOnline = true;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("*---------------------------------*");
		System.out.println("Hello " + user.getUsername() + "!");
		System.out.println("*---------------------------------*");
		do {
			System.out.println("USER CONTROL:");
			for(TransactionType transactions : TransactionType.values()) {
				System.out.println(String.format("[%d]-%s" , transactions.ordinal(), transactions));
			}
			System.out.println("*---------------------------------*");
		
			System.out.print("Choose transaction:");
			int action = sc.nextInt();
			TransactionType mode = TransactionType.values()[action];
			System.out.println("*---------------------------------*");
			
			switch (mode) {
				case ACCOUNT_OVERVIEW: 
					Transaction.accountOverView(user);
					
					break;
				case DEPOSIT:				
					System.out.print("Deposit Amount: ");
					long depositAmount = sc.nextLong();
	
					//validation and execution
					Transaction.validateDeposit(user, depositAmount);
					updateAccount(user);
				
					break;
				case WITHDRAW:
					System.out.print("Withdraw Amount:");
					long withdrawAmount = sc.nextLong();
					
					//validation and execution
					Transaction.validateWithdraw(user, withdrawAmount);
					updateAccount(user);
				
					break;
				case BAL_CHECK:
					Transaction.balanceCheck(user);
					
					break;
				case VIEW_TRANS: //Show all the user's transaction - using global variable as parameters
	//				if(userTransactionList.isEmpty()) {
	//					System.out.println("*-NO TRANSACTION FOUND-*");
	//				}else {
	//					showAllTransaction(userTransactionList);
	//				}
					System.out.println("VIEW_TRANS");
					break;
				case TRANS_LIST: //Exit App
					System.out.println("TRANS_LIST");
					break;
				case EXIT:				
					terminateApp(sc);
					break;
				default: 
					System.out.println("Invalid mode");
				}
			} while (isUserOnline != false);
		
		sc.close(); 
	}

	//create a folder
	public static void createFolder() {
		final String bank_userPath = "./account_data/bank_user";
		File bank_userFolder = new File(bank_userPath);
		bank_userFolder.mkdirs();
		
		final String transaction_Path = "./account_data/transaction";
		File transaction_Folder = new File(transaction_Path);
		transaction_Folder.mkdirs();
	}
	
	//serialize custom object
	public static boolean registerUser(String reg_username , String reg_password , AccountType accountType) {
		String filePath = "./account_data/bank_user/" + reg_username.concat(".ser");
		File file = new File(filePath);
		User user = new User(reg_username , reg_password , accountType);
		
		if(!file.exists()) {
			try {
				FileOutputStream userFileOut = new FileOutputStream(filePath);
				ObjectOutputStream userOut = new ObjectOutputStream(userFileOut);
				userOut.writeObject(user);
				userFileOut.close();
				userOut.close();
				System.out.println("Account successfully created!");
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
		
	System.out.println("Registration error: Username has already been taken");
	return false;	
}

	//re-serialize/overwrite - User Object - working
	public static void updateAccount(User user) {
		String filePath = "./account_data/bank_user/" + user.getUsername().concat(".ser");
		File file = new File(filePath);
		
		if(file.exists()) {
			try {
				FileOutputStream userFileOut = new FileOutputStream(filePath);
				ObjectOutputStream userOut = new ObjectOutputStream(userFileOut);
				userOut.writeObject(user);
				userFileOut.close();
				userOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			System.out.println("Unable to update User Account. File not found");
		}	
	}

	//deserialize custom object - Working
	public static User retrieveUserAccount(String userName){
		try {
			FileInputStream fileIn = new FileInputStream("./account_data/bank_user/" + userName.concat(".ser"));
			ObjectInputStream in = new ObjectInputStream(fileIn);
			User user = (User) in.readObject();
			fileIn.close();
			in.close();
			
			return user;
		}catch (Exception e) {
			return null;
		}	
	}	
	
	static void terminateApp(Scanner scanner) {
		System.out.println("Thank you for using the app");
		System.out.println("*---------------------------------*");
 		scanner.close();
 		System.exit(0);
 	}
	
}

