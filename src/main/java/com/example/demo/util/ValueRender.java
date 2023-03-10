package com.example.demo.util;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HexFormat;
import java.util.List;
import java.util.Locale;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Invoice;
import com.example.demo.entity.dto.InvoicesWithProducts;
import com.example.demo.entity.embededkey.InvoicesWithProductsPrimaryKeys;
import com.example.demo.respository.CartRepository;
import com.example.demo.respository.ProductRepository;

public class ValueRender {
    //format double value
    public static String formatDoubleNumber(double number) {
        DecimalFormat dfGerman = new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.GERMAN));
        
        String result = dfGerman.format(number);
        return result;
    }
    
    
    //get the total amount of rating stars of product
    public static int ratingStarsTotalNumber(int oneStar, int twoStar, int threeStar, int fourStar, int fiveStar) {
        int zeroRatedNum = 0;
                
        if(oneStar != 0) {
            zeroRatedNum += 1;
        }
        if(twoStar != 0) {
            zeroRatedNum += 1;
        }
        if(threeStar != 0) {
            zeroRatedNum += 1;
        }
        if(fourStar != 0) {
            zeroRatedNum += 1;
        }
        if(fiveStar != 0) {
            zeroRatedNum += 1;
        }
        if(zeroRatedNum == 0) {
            zeroRatedNum = 1;
        }
        return (oneStar + twoStar + threeStar + fourStar + fiveStar) / zeroRatedNum;
    }
    
    
    //encode the password
    public static String encodePassword(String pass) {
        char[] charArr = pass.toCharArray();
        String result;
        
        for(int i = 0; i < charArr.length; i++) {
            charArr[i] = (char) (charArr[i] + 5);
        }
        result = charArr.toString();
        
        return result;
    }
    
    
    //decode the password
    public static String decodePassword(String pass) {
        char[] charArr = pass.toCharArray();
        String result;
        
        for(int i = 0; i < charArr.length; i++) {
            charArr[i] = (char) (charArr[i] - 5);
        }
        result = String.valueOf(charArr);
        
        return result;
    }
    
    
    //format string to link
    public static String stringToLink(String link) {
        String result = " ";
        char[] linkCharrArr = link.toCharArray();
        
        for(int i = 0; i < linkCharrArr.length; i++) {
            if(linkCharrArr[i] == ' ') {
                linkCharrArr[i] = '_';
            }
        }
        result = String.valueOf(linkCharrArr);
        
        return result;
    }
    
    
    //format link to string
    public static String linkToString(String link) {
        String result = " ";
        char[] linkCharrArr = link.toCharArray();
        
        for(int i = 0; i < linkCharrArr.length; i++) {
            if(linkCharrArr[i] == '_') {
                linkCharrArr[i] = ' ';
            }
        }
        result = String.valueOf(linkCharrArr);
        
        return result;
    }
    
    
    //format DateTime
    public static String formatDateDMY(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return simpleDateFormat.format(date);
    }
    
    
    //remove an element in an Integer array
    public static int[] newArrayAfterRemove(int[] arr, int element) {
    	int[] resultArr = new int[arr.length];
    	int arrInd = 0;
    	
    	for(int i = 0; i < arr.length; i++) {
    		if(arr[i] != element) {
    			resultArr[arrInd] = arr[i];
    			arrInd++;
    		}
    	}
    	
    	return resultArr;
    }
    
    
    //get a substring of a string
    public static String getSubstring(String orgString, int startInd, int endInd) {
    	String result = "";
    	
    	result = orgString.substring(startInd, endInd);
    	
    	return result;
    }
    
    
    //get a Cart list from an ID list
    public static List<Cart> getCartListFromIdList(int[] idList, CartRepository cartRepo) {
    	List<Cart> cartList = new ArrayList<Cart>();
    	
    	for(int i = 0; i < idList.length; i++) {
    		Cart cart = cartRepo.getCartById(idList[i]);
    		cartList.add(cart);
    	}
    	
    	return cartList;
    }
    
    
    //get product available quantity list in cart by selected cart ID list
    public static int[] getFullCartAvailableQuantityList(int[] fullCartIdList, ProductRepository productRepo) {
    	int[] result = new int[fullCartIdList.length];
    	
    	for(int i = 0; i < fullCartIdList.length; i++)
    	{
    		result[i] = productRepo.getAvailableQuantityByCartId(fullCartIdList[i]);
    	}
    	
    	return result;
    }
    
    
    //get a products in invoice list by a cart ID list
    public static List<InvoicesWithProducts> getInvoiceProductsListByCartIdList(List<Cart> cartList, Invoice invoice) {
    	List<InvoicesWithProducts> result = new ArrayList<InvoicesWithProducts>();
    	
    	for(int i = 0; i < cartList.size(); i++) {
    		InvoicesWithProductsPrimaryKeys id = new InvoicesWithProductsPrimaryKeys(cartList.get(i).getProduct().getId(), invoice.getId());
    		result.add(new InvoicesWithProducts(id, cartList.get(i).getProduct(), invoice, cartList.get(i).getQuantity()));
    	}
    	
    	return result;
    }
    
    
    //remove spaces at the beginning of the input text
    public static String formattedInputString(String input) {
    	int count = 0;
    	
    	 char[] charArr = input.toCharArray();
    	 char[] resultCharArr = new char[charArr.length];
         
         for(int i = 0; i < charArr.length - 1; i++) {
             if(i > 0) {
            	if(charArr[i] != ' ' || (charArr[i] == ' ' && charArr[i - 1] != ' ')) {
                	resultCharArr[count] = charArr[i];
                	count++;
                }
             }
             else {
				if(charArr[i] != ' ') {
					resultCharArr[count] = charArr[i];
					count++;
				}
			}
         }
         
    	return String.copyValueOf(resultCharArr).trim();
    }
    
    
    //check if the text has special sign 
    public boolean hasSpecialSign (String input) {
    	char[] testCharArr = input.toCharArray();
    	
    	for(int i = 0; i < testCharArr.length - 1; i++) {
    		if(testCharArr[i] != 32 && testCharArr[i] < 65 && testCharArr[i] > 90 && testCharArr[i] < 97 && testCharArr[i] > 122) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    
    //convert String to byte[]
    public static byte[] convertStringToByteArray(String input) {
    	return input.getBytes(StandardCharsets.UTF_8);
    }
    
    
    //convert byte[] to String
    public static String convertByteToString(byte[] input) {
    	return new String(input, StandardCharsets.UTF_8);
    }
}
