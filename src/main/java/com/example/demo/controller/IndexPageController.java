package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Account;
import com.example.demo.entity.Product;
import com.example.demo.respository.AccountRepository;
import com.example.demo.respository.ProductRepository;

@Controller
public class IndexPageController {
    ProductRepository productRepo;
    Product product = new Product();
    List<Product> top6estSelllerProducts;
    
	@GetMapping("/home")
	public String showHomePage(Model model) {
	    model.addAttribute("Product", product);
		return "index";
	}
	
	@PostMapping("/home")
	public String renderDataToHome(@ModelAttribute("Product") Product product) {
	    top6estSelllerProducts = productRepo.get6BestSellerProducts();
	    
        return "home";
	}
}