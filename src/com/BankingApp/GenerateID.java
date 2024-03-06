package com.BankingApp;

public interface GenerateID {
	
	default int createID() {
		 return (int) Math.floor((Math.random()* 999999999));		 
	}
}
