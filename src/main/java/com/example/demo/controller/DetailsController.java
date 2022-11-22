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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Account;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.entity.dto.ProductComment;
import com.example.demo.respository.CommentRepository;
import com.example.demo.respository.CustomerRepository;
import com.example.demo.respository.ProductRepository;
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
    
    Integer[] ratingStarArr = {1,2,3,4,5};
    
    
    public void renderToProductDetails(Account Cuser, Model model, String realProductName, Product productDetail, HttpServletRequest request) {
        List<Comment> comments = commentRepo.getCommentByProductName(realProductName);
        
        List<String> sizeList = productRepo.getAllSizesOfProductByName(realProductName);
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
                    
                    System.out.println(comments.get(i).getId() + " " + cusName + " " + content + " " + cusImgUrl + " " + comments.size());
                    
                    commentList.add(new ProductComment(cusName, content, cusImgUrl));
                }
                else {
                    System.out.println(comments.get(i).getProduct().getId());
                }
            }
        }
//        LoginState.isLoggedIn(model, request, customerRepo);
        
        
    	Customer user = customerRepo.getCustomerByAccountId(Cuser.getId());
    	model.addAttribute("userid", user.getId());
        
        model.addAttribute("productDetail", productDetail);
        model.addAttribute("ratingStarArr", ratingStarArr);
        model.addAttribute("sizeList", sizeList);
        model.addAttribute("colorList", colorList);
        model.addAttribute("cateList", cateList);
        model.addAttribute("commentList", commentList);
    }
    
    
    @GetMapping("/shop-details_name={productName}")
    public String showDefaultProductDetails(HttpSession session, Model model, @PathVariable("productName") String productName, HttpServletRequest request) {
        Account Cuser = (Account)session.getAttribute("currentuser");
    	if(Cuser != null) {
        	String realProductName = ValueRender.linkToString(productName);
            Product productDetail = productRepo.getDefaultProductDetailsByName(realProductName);

            renderToProductDetails(Cuser, model, realProductName, productDetail, request);

            return "shopdetails";
        }
        else return "redirect:/loginpage";
    }
    
    
    @GetMapping("/shop-details-by-color_name={productName}__color={color}")
    public String showProductDetails(HttpSession session,Model model, @PathVariable("productName") String productName, @PathVariable("color") String color, HttpServletRequest request) {
    	String realProductName = ValueRender.linkToString(productName);
        Product productDetail = productRepo.getProductByNameAndColor(realProductName, color);
        
        Account Cuser = (Account)session.getAttribute("currentuser");

        renderToProductDetails(Cuser, model, realProductName, productDetail, request);
        
        return "shopdetails";
    }
}
