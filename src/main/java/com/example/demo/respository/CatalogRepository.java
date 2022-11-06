package com.example.demo.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Catalog;


@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Integer>{
    @Query(value = "select count(newTable.id) from (select p.* "
                 + "from products p join catalog_with_products cwp on p.name = cwp.product_name join catalog c on c.id = cwp.catalog_id "
                 + "where c.name = :catalogNameVal "
                 + "group by p.name) as newTable", nativeQuery = true)
    int getTotalProductsNumberByCatalogName(@Param("catalogNameVal") String catalogName);
    
    
    @Query(value = "select name from catalog", nativeQuery = true)
    List<String> getAllCatalogsName();
    
    
    @Query(value = "select * from catalog", nativeQuery = true)
    List<Catalog> getAllCatalogs();
}