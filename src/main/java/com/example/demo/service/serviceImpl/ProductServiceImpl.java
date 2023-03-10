package com.example.demo.service.serviceImpl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Catalog;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductManagement;
import com.example.demo.entity.dto.ProductInfo;
import com.example.demo.respository.CartRemoveRepository;
import com.example.demo.respository.CatalogRepository;
import com.example.demo.respository.CommentRepository;
import com.example.demo.respository.ProductCustomQueryRepository;
import com.example.demo.respository.ProductManagementRepository;
import com.example.demo.respository.ProductRemoveRepository;
import com.example.demo.respository.ProductRepository;
import com.example.demo.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepo;
    
    @Autowired
    private ProductRemoveRepository productRemoveRepo;
    
    @Autowired
    private CartRemoveRepository cartRemoveRepo;
    
    @Autowired
    private CatalogRepository catalogRepo;
    
    @Autowired
    private ProductManagementRepository productMngRepo;
    
    @Autowired
    private ProductCustomQueryRepository productCustomQueryRepo;
    
    private List<Product> products;
    
    
    
    @Override
    public Page<Product> findPaginated(Pageable pageable) {
        products = productRepo.getAllProducts();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startProduct = pageSize * currentPage;
        List<Product> list;
        
        if(products.size() < startProduct) {
            list = Collections.emptyList();
        }
        else {
            int toIndex = Math.min(startProduct + pageSize, products.size());
            list = products.subList(startProduct, toIndex);
        }
        
        return new PageImpl<Product>(list, PageRequest.of(currentPage, pageSize), products.size());
    }
    
    
    @Override
    public Page<Product> searchProduct(Pageable pageable, String Name ) {
        products = productRepo.getProductsByName(Name);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startProduct = pageSize * currentPage;
        List<Product> list;
        
        if(products.size() < startProduct) {
            list = Collections.emptyList();
        }
        else {
            int toIndex = Math.min(startProduct + pageSize, products.size());
            list = products.subList(startProduct, toIndex);
        }
        
        return new PageImpl<Product>(list, PageRequest.of(currentPage, pageSize), products.size());
    }


    @Override
    public void deleteProduct(int productId) {
        Product product = productRepo.getProductById(productId);
        String productName = product.getName();
        
        productRemoveRepo.deleteFromProductProductsManagement(productId);
        productRemoveRepo.deleteFromProductComments(productId);
        cartRemoveRepo.deleteProductFromCartByProductId(productId);
        productRemoveRepo.deleteProductFromCatalogWithProducts(productName);
        
        productRepo.deleteById(productId);
    }


	@Override
	public Page<Product> findByFilters(Pageable pageable, String query) {
		ProductCustomQueryRepository filterRepo = new ProductCustomQueryRepository();
		
		List<Object[]> objList = filterRepo.getFilteredProducts(query);
		products = new ArrayList<Product>();
		Iterator<Object[]> iterator = objList.iterator();
		
		while(iterator.hasNext()) {
			Object[] line = (Object[]) iterator.next();
		    Product product = new Product();
		    
//		    for(int i = 0; i < line.length; i++) {
//		    	System.out.println(line[i].toString());
//		    }
		    
		    product.setId(((BigInteger)line[0]).intValue());
		    product.setName((String)line[1]);
		    product.setPrice((double)line[2]);
		    product.setOriginalPrice((double)line[3]);
		    product.setColor((String)line[4]);
		    product.setSize((String)line[5]);
		    product.setAvailableQuantity((int)line[6]);
		    product.setSoldQuantity(((BigInteger)line[7]).intValue());
		    product.setOneStarQuantity((int)line[8]);
		    product.setTwoStarQuantity((int)line[9]);
		    product.setThreeStarQuantity((int)line[10]);
		    product.setFourStarQuantity((int)line[11]);
		    product.setFiveStarQuantity((int)line[12]);
		    product.setBrand((String)line[13]);
		    product.setDiscount((double)line[14]);
		    product.setImage1((String)line[15]);
		    product.setImage2((String)line[16]);
		    product.setImage3((String)line[17]);
		    product.setImage4((String)line[18]);
		    product.setDescription((String)line[19]);
		    
		    products.add(product);
		}
		
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startProduct = pageSize * currentPage;
        List<Product> list;
        
        if(products.size() < startProduct) {
            list = Collections.emptyList();
        }
        else {
            int toIndex = Math.min(startProduct + pageSize, products.size());
            list = products.subList(startProduct, toIndex);
        }
        
        return new PageImpl<Product>(list, PageRequest.of(currentPage, pageSize), products.size());
	}


	@Override
	public void ratingProduct(int ratingPoint, String name, String color) {
		List<Product> productList = productRepo.getProductListByNameAndColor(name, color);
		
		for(int i = 0; i < productList.size(); i++) {
			switch (ratingPoint) {
				case 1: {
					int num = productList.get(i).getOneStarQuantity();
					productList.get(i).setOneStarQuantity(num + 1);
					break;
				}
				case 2: {
					int num = productList.get(i).getTwoStarQuantity();
					productList.get(i).setTwoStarQuantity(num + 1);
					break;
				}
				case 3: {
					int num = productList.get(i).getThreeStarQuantity();
					productList.get(i).setThreeStarQuantity(num + 1);
					break;
				}
				case 4: {
					int num = productList.get(i).getFourStarQuantity();
					productList.get(i).setFourStarQuantity(num + 1);
					break;
				}
				case 5: {
					int num = productList.get(i).getFiveStarQuantity();
					productList.get(i).setFiveStarQuantity(num + 1);
					break;
				}
	    	}
			productRepo.save(productList.get(i));
		}
	}


	@Override
	public void addNewProduct(ProductInfo newProductInfo) {
		List<Catalog> catalogList = new ArrayList<Catalog>();
		
		for(int i = 0; i < newProductInfo.getCatalogList().length; i++) {
			String catalogName = newProductInfo.getCatalogList()[i];
			Catalog catalog = catalogRepo.getCatalogByName(catalogName);
			catalogList.add(catalog);
		}
		
		for(int i = 0; i < newProductInfo.getSizeList().length; i++) {
			Product product = new Product(
					newProductInfo.getColor(), 
					newProductInfo.getSizeList()[i],
					newProductInfo.getName(), 
					newProductInfo.getSellingPrice(), 
					newProductInfo.getOriginalPrice(), 
					newProductInfo.getAvailableQuantityList()[i],
					0, 0, 0, 0, 0, 0, 0,
					newProductInfo.getBrand(),
					newProductInfo.getImage1Url(),
					newProductInfo.getImage2Url(),
					newProductInfo.getImage3Url(),
					newProductInfo.getImage4Url(),
					newProductInfo.getDescription(),
					catalogList);
			
			ProductManagement newProductMng = new ProductManagement(
					productMngRepo.getLastProductManagementId() + 1, 
					newProductInfo.getImportDate(), 
					newProductInfo.getAvailableQuantityList()[i], 
					null, product);
			
			productRepo.save(product);
			productMngRepo.save(newProductMng);
		}
		
		for(int i = 0; i < catalogList.size(); i++) {
			productCustomQueryRepo.insertCatalogWithProducts(catalogList.get(i).getId(), newProductInfo.getName());
		}
	}
	
	
	@Override
	public ProductInfo getProductInfo(String productName, String color) {
		ProductInfo productInfo = new ProductInfo();
		List<Product> productList = productRepo.getProductListByNameAndColor(productName, color);
		
		int[] productIdArr = new int[productList.size()]; 
		int[] productAvaiQuantArr = new int[productList.size()];
		String[] productCatalogArr = new String[productList.size()];
		String[] productSizeArr = new String[productList.size()];
		
		for(int i = 0; i < productList.size(); i++) {
			productAvaiQuantArr[i] = productList.get(i).getAvailableQuantity();
			productSizeArr[i] = productList.get(i).getSize();
			productIdArr[i] = productList.get(i).getId();
		}
		
		for(int i = 0; i < productList.get(0).getCatalogs().size(); i++) {
			productCatalogArr[i] = productList.get(0).getCatalogs().get(i).getName();
		}
		
		productInfo.setName(productList.get(0).getName());
		productInfo.setBrand(productList.get(0).getBrand());
		productInfo.setColor(productList.get(0).getColor());
		productInfo.setSellingPrice(productList.get(0).getPrice());
		productInfo.setOriginalPrice(productList.get(0).getOriginalPrice());
		productInfo.setDescription(productList.get(0).getDescription());
		productInfo.setAvailableQuantityList(productAvaiQuantArr);
		productInfo.setSizeList(productSizeArr);
		productInfo.setCatalogList(productCatalogArr);
		productInfo.setImage1Url(productList.get(0).getImage1());
		productInfo.setImage2Url(productList.get(0).getImage2());
		productInfo.setImage3Url(productList.get(0).getImage3());
		productInfo.setImage4Url(productList.get(0).getImage4());
		
		return productInfo;
	}


	@Override
	public Product getProduct(int id, ProductInfo productInfo) {
		Product product = new Product();
		
		product.setId(id);
		product.setName(productInfo.getName());
		product.setBrand(productInfo.getBrand());
		product.setColor(productInfo.getColor());
		product.setPrice(productInfo.getSellingPrice());
		product.setOriginalPrice(productInfo.getOriginalPrice());
		product.setDescription(productInfo.getDescription());
		product.getProductManagements().get(product.getProductManagements().size() - 1).setImportDate(productInfo.getImportDate());
		
		return product;
	}


	@Override
	public void editGeneralProduct(ProductInfo initProductInfo, ProductInfo modelProductInfo) {
		if(initProductInfo.getSizeList().length >= modelProductInfo.getSizeList().length) {
			for(int i = 0; i < initProductInfo.getIdList().length; i++) {
				Product product = new Product();
				
				product.setId(modelProductInfo.getIdList()[i]);
				product.setAvailableQuantity(modelProductInfo.getAvailableQuantityList()[i]);
				product.setName(modelProductInfo.getName());
				product.setBrand(modelProductInfo.getBrand());
				product.setColor(modelProductInfo.getColor());
				product.setPrice(modelProductInfo.getSellingPrice());
				product.setOriginalPrice(modelProductInfo.getOriginalPrice());
				product.setDescription(modelProductInfo.getDescription());
				product.setSize(modelProductInfo.getSizeList()[i]);
				product.setImage1(modelProductInfo.getImage1Url());
				product.setImage2(modelProductInfo.getImage2Url());
				product.setImage3(modelProductInfo.getImage3Url());
				product.setImage4(modelProductInfo.getImage4Url());
				
				productRepo.save(product);
			}
			
			productRemoveRepo.deleteProductFromCatalogWithProducts(modelProductInfo.getName());
			
			for(int i = 0; i < modelProductInfo.getCatalogList().length; i++) {
				int catalogId = catalogRepo.getCatalogIdByName(modelProductInfo.getCatalogList()[i]);
				productCustomQueryRepo.insertCatalogWithProducts(catalogId, modelProductInfo.getName());
			}
		}
		else if(initProductInfo.getSizeList().length < modelProductInfo.getSizeList().length) {
			for(int i = 0; i < initProductInfo.getSizeList().length; i++) {
				Product product = new Product();
				
				product.setId(modelProductInfo.getIdList()[i]);
				product.setAvailableQuantity(modelProductInfo.getAvailableQuantityList()[i]);
				product.setName(modelProductInfo.getName());
				product.setBrand(modelProductInfo.getBrand());
				product.setColor(modelProductInfo.getColor());
				product.setPrice(modelProductInfo.getSellingPrice());
				product.setOriginalPrice(modelProductInfo.getOriginalPrice());
				product.setDescription(modelProductInfo.getDescription());
				product.setSize(modelProductInfo.getSizeList()[i]);
				product.setImage1(modelProductInfo.getImage1Url());
				product.setImage2(modelProductInfo.getImage2Url());
				product.setImage3(modelProductInfo.getImage3Url());
				product.setImage4(modelProductInfo.getImage4Url());
				
				productRepo.save(product);
			}
			
			productRemoveRepo.deleteProductFromCatalogWithProducts(modelProductInfo.getName());
			
			for(int i = 0; i < modelProductInfo.getCatalogList().length; i++) {
				int catalogId = catalogRepo.getCatalogIdByName(modelProductInfo.getCatalogList()[i]);
				productCustomQueryRepo.insertCatalogWithProducts(catalogId, modelProductInfo.getName());
			}
			
			for(int i = initProductInfo.getSizeList().length; i < modelProductInfo.getSizeList().length; i++) {
				int id = productRepo.getLastestProductId();
				Product product = new Product();
				
				product.setId(id);
				product.setName(modelProductInfo.getName());
				product.setBrand(modelProductInfo.getBrand());
				product.setColor(modelProductInfo.getColor());
				product.setPrice(modelProductInfo.getSellingPrice());
				product.setOriginalPrice(modelProductInfo.getOriginalPrice());
				product.setDescription(modelProductInfo.getDescription());
				product.setSize(modelProductInfo.getSizeList()[i]);
				product.setImage1(modelProductInfo.getImage1Url());
				product.setImage2(modelProductInfo.getImage2Url());
				product.setImage3(modelProductInfo.getImage3Url());
				product.setImage4(modelProductInfo.getImage4Url());
				
				productRepo.save(product);
			}
		}
	}
}
