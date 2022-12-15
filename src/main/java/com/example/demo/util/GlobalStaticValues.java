package com.example.demo.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.entity.Staff;

import ch.qos.logback.core.status.Status;

public class GlobalStaticValues {
	public static int[] customerFullCartIdList = {};
	
	public static int[] customerSelectedCartIdList = {};
	
	public static int[] customerFullCartQuantityList = {};
	
	public static int[] customerFullSelectStatusList = {};
	
	public static int[] customerFullCartAvailableQuantityList = {};
	
	public static String[] customerFullCartSizeList = {};
	
	public static double customerInvoiceTotalPrice;
	
	public static Customer currentCustomer = new Customer();
	
	public static Staff currentStaff = new Staff();
	
	public static Product addToCartProduct = new Product();
	
	public static String message = "";
	
	public static String filterQuery = "";
	
	public static List<Cart> customerFullCartList = new ArrayList<Cart>();
	
	public static boolean[] customerCheckedCartList(int[] selectStatusArr) {
		boolean[] tmpArr = new boolean[selectStatusArr.length];
		
		for(int i = 0; i < tmpArr.length; i++) {
			if(selectStatusArr[i] == 0) {
				tmpArr[i] = false;
			} else {
				tmpArr[i] = true;
			}
		}
		
		return tmpArr;
	}
	
	public static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
	public static EntityManager entityManager = entityManagerFactory.createEntityManager();
}
