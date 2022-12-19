package com.example.demo.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ProductManagement;

@Repository
public interface ProductManagementRepository extends JpaRepository<ProductManagement, Integer>{
	@Query(value = "select * from products_management where product_id = :idVal order by id desc limit 1", nativeQuery = true)
	ProductManagement getLastestProductManagementInfoByProductId(@Param("idVal") int productId);
	
	
	@Query(value = "select id from products_management order by id desc limit 1", nativeQuery = true)
	int getLastProductManagementId();
}
