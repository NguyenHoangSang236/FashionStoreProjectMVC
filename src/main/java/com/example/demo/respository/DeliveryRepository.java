package com.example.demo.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Delivery;


@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer>{
	@Query(value = "select * from delivery where shipper_id = :idVal and current_status = 'shipping'", nativeQuery = true)
	List<Delivery> getShippingDeliveryListByShipperId(@Param("idVal") int id);
	
	
	@Query(value = "select * from staffs where id = :idVal and current_status = 'success'", nativeQuery = true)
	List<Delivery> getSuccessDeliveryByListShipperId(@Param("idVal") int id);
	
	
	@Query(value = "select * from staffs where id = :idVal and current_status = 'failed'", nativeQuery = true)
	List<Delivery> getFailedDeliveryListByShipperId(@Param("idVal") int id);
	
	
	@Query(value = "select * from delivery where id = :idVal and shipper_id = :shipperIdVal", nativeQuery = true)
	Delivery getDeiveryByIdAndShipperId(@Param("idVal") int id, @Param("shipperIdVal") int shipperId);
}
