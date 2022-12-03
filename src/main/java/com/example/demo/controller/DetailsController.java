package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Account;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.entity.dto.AddToCartProductInfo;
import com.example.demo.entity.dto.ProductComment;
import com.example.demo.respository.CartRepository;
import com.example.demo.respository.CommentRepository;
import com.example.demo.respository.CustomerRepository;
import com.example.demo.respository.ProductRepository;
import com.example.demo.util.GlobalStaticValues;
import com.example.demo.util.LoginState;
import com.example.demo.util.ValueRender;

@SessionAttributes("currentuser")
@Controller
public class DetailsController {
    @Autowired
    ProductRepository productRepo;
    
    @Autowired 
    CommentRepository commentRepo;
    
    @Autowired
    CustomerRepository customerRepo;
    
    @Autowired
    CartRepository cartRepo;
    
    Integer[] ratingStarArr = {1,2,3,4,5};
    Product currentProduct;
    AddToCartProductInfo addToCartProduct = new AddToCartProductInfo();
    int defaultQuantity = 1;
    
    
    public void renderToProductDetails(HttpSession session, Model model, String realProductName, Product productDetail, HttpServletRequest request) {
        List<Comment> comments = commentRepo.getCommentByProductName(realProductName);
        
        List<String> sizeList = productRepo.getAllSizesOfProductByNameAndColor(realProductName, productDetail.getColor());
        List<String> colorList = productRepo.getAllColorsOfProductByName(realProductName);
        List<String> cateList = productRepo.getAllCatalogsByProductName(realProductName);
        List<ProductComment> commentList = new ArrayList<ProductComment>();
        
        if(comments.size() > 0) {            
            for(int i = 0; i < comments.size(); i++) {
                Customer customer = customerRepo.getCustomerById(comments.get(i).getCustomer().getId());
                
                if(customer != null) {
                    String cusName = customer.getName();
                    String cusImgUrl = customer.getImage();
                    String content = comments.get(i).getContent();
                    
                    commentList.add(new ProductComment(cusName, content, cusImgUrl));
                }
            }
        }
        
        Account currentAcount = (Account)session.getAttribute("currentuser");
	    
	    if(currentAcount != null) {
		    Customer Ccustomer = customerRepo.getCustomerByAccountId(currentAcount.getId());
		    
		    model.addAttribute("curentcusImage",Ccustomer.getImage());
		    model.addAttribute("curentcusName",Ccustomer.getName());
		    model.addAttribute("userid", Ccustomer.getId());
	    }
    	
        model.addAttribute("defaultQuantity", defaultQuantity);
        model.addAttribute("productDetail", productDetail);
        model.addAttribute("ratingStarArr", ratingStarArr);
        model.addAttribute("sizeList", sizeList);
        model.addAttribute("colorList", colorList);
        model.addAttribute("cateList", cateList);
        model.addAttribute("commentList", commentList);
        model.addAttribute("addToCartProduct", addToCartProduct);
    }
    
    
    @GetMapping("/shop-details_name={productName}")
    public String showDefaultProductDetails(HttpSession session, Model model, @PathVariable("productName") String productName, HttpServletRequest request) {
        String realProductName = ValueRender.linkToString(productName);
        currentProduct = productRepo.getDefaultProductDetailsByName(realProductName);

        renderToProductDetails(session, model, realProductName, currentProduct, request);

        return "shopdetails";
    }
    
    
    @PostMapping("/shop-details_name={productName}")
    public String addToCartFromDefaultDetailPage(@RequestParam(value="action") String action, HttpSession session, Model model, @PathVariable("productName") String productName, HttpServletRequest request, @ModelAttribute("productDetail") Product productDetail, @ModelAttribute("addToCartProduct") AddToCartProductInfo modelAddToCartProductInfo) {
        String realProductName = ValueRender.linkToString(productName);
        
        if(modelAddToCartProductInfo.getProductSize() == null) {
        	GlobalStaticValues.message = "Please choose a size first !!";
        }
        else if(action.equals("logged in - add to cart")) {        	
        	int id = cartRepo.getLastestCartId() + 1;
        	int quantity = 0;
        	
        	Account currentAcount = (Account)session.getAttribute("currentuser");
        	Product product = productRepo.getProductDetailsByNameAndColorAndSize(realProductName, currentProduct.getColor(), modelAddToCartProductInfo.getProductSize());
        	int availableQuantity = productRepo.getAvailableQuantityById(product.getId());
        	
        	Cart tmpCart = cartRepo.getCartByProductIdAndCustomerId(product.getId(), GlobalStaticValues.currentCustomer.getId());
        	System.out.println(currentProduct.getId() + " " + GlobalStaticValues.currentCustomer.getId());
        	
        	//if this product has already been in the cart --> quantity = this product's quantity
        	if(tmpCart != null) {
        		quantity = tmpCart.getQuantity();
        		id = tmpCart.getId();
        	} 
        	
        	//if available quantity of product > selected product quantity --> save cart
        	if(availableQuantity > quantity + modelAddToCartProductInfo.getQuantity()) {
        		Customer customer = customerRepo.getCustomerByAccountId(currentAcount.getId());
        	
        		Cart newCart = new Cart(id, quantity + modelAddToCartProductInfo.getQuantity(), 0, 0, customer, product);
        		cartRepo.save(newCart);
        	} else {
        		GlobalStaticValues.message = "Oops! We are having only " + String.valueOf(availableQuantity) + " available products";
        	}
        	
        }
        else if(action.equals("need to login")) {
        	return "redirect:/loginpage";
        }

        renderToProductDetails(session, model, realProductName, currentProduct, request);
        
    	return "shopdetails";
    }
    
    
    
    @GetMapping("/shop-details-by-color_name={productName}__color={color}")
    public String showProductDetails(HttpSession session,Model model, @PathVariable("productName") String productName, @PathVariable("color") String color, HttpServletRequest request) {
    	String realProductName = ValueRender.linkToString(productName);
        currentProduct = productRepo.getProductByNameAndColor(realProductName, color);
        
        renderToProductDetails(session, model, realProductName, currentProduct, request);
        
        return "shopdetails";
    }
    
    
    @PostMapping("/shop-details-by-color_name={productName}__color={color}")
    public String addToCartFromColorDetailPage(@RequestParam(value="action") String action, HttpSession session, Model model , @PathVariable("color") String color, @PathVariable("productName") String productName, HttpServletRequest request, @ModelAttribute("productDetail") Product productDetail, @ModelAttribute("addToCartProduct") AddToCartProductInfo modelAddToCartProductInfo) {
    	String realProductName = ValueRender.linkToString(productName);
        
        if(modelAddToCartProductInfo.getProductSize() == null) {
        	GlobalStaticValues.message = "Please choose a size first !!";
        }
        else if(action.equals("logged in - add to cart")) {        	
        	int id = cartRepo.getLastestCartId() + 1;
        	int quantity = 0;
        	
//        	System.out.println(id);
//        	System.out.println(currentProduct.getColor());
//        	System.out.println(modelAddToCartProductInfo.getProductSize());
        	 
        	Account currentAcount = (Account)session.getAttribute("currentuser");
        	Product product = productRepo.getProductDetailsByNameAndColorAndSize(realProductName, color, modelAddToCartProductInfo.getProductSize());
//        	System.out.println(product.getId());
        	
        	Cart tmpCart = cartRepo.getCartByProductIdAndCustomerId(product.getId(), GlobalStaticValues.currentCustomer.getId());
        	System.out.println(currentProduct.getId() + " " + GlobalStaticValues.currentCustomer.getId());
        	
        	//if this product has already been in the cart --> quantity = this product's quantity
        	if(tmpCart != null) {
        		quantity = tmpCart.getQuantity();
        		id = tmpCart.getId();
        		System.out.println(quantity);
        	}
        	
        	Customer customer = customerRepo.getCustomerByAccountId(currentAcount.getId());
        	Cart newCart = new Cart(id, quantity + modelAddToCartProductInfo.getQuantity(), 0, 0, customer, product);
        	
        	cartRepo.save(newCart);
        }
        else if(action.equals("need to login")) {
        	return "redirect:/loginpage";
        }

        renderToProductDetails(session, model, realProductName, currentProduct, request);
    	
    	return "shopdetails";
    }
}
