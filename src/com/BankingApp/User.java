package com.BankingApp;



public class User extends UserData implements GenerateID , AccountView {	
	
	private String name;
	private Enum<AccountType> accountType;
	private Integer id;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public User(String name, Enum<AccountType> accountType) {
		super();
		this.name = name;
		this.accountType = accountType;
		this.id = createID(); //from Interface
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Enum<AccountType> getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	//Account Overview
	@Override
	public void getAccountDetails() {
		System.out.println("*---------------------------------*");
		System.out.println(String.format("Account ID: %d", this.getId()));
		System.out.println(String.format("Name: %s", this.getName()));
		System.out.println(String.format("Type: %s" , this.getAccountType()));
		System.out.println(String.format("Balance: %s" , this.getCurrentBalance())); //double printed as string
		System.out.println("*---------------------------------*");
		
	}
	


}
	
	
	
	
	
	
	
	
	
	
	
	

	

