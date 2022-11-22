package com.example.demo.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Staff;
import com.example.demo.respository.CustomerRepository;

public class LoginState {
    public static Account currentAccount;
    
    public static Staff currentStaff = new Staff();
    
    public static Customer currentCustomer = new Customer();
    
}
