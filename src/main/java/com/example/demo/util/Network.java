package com.example.demo.util;

public class Network {
	public static String currentTemporaryPassword;
	
	public static String randomTemporaryPassword() {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		 
		StringBuilder sb = new StringBuilder(10);
		 
		for (int i = 0; i < 10; i++) {
			int index = (int)(AlphaNumericString.length() * Math.random());
		 
			sb.append(AlphaNumericString.charAt(index));
		}
		
		currentTemporaryPassword = sb.toString();
		 
		return sb.toString();
	}
}
