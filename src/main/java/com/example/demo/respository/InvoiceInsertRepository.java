package com.example.demo.respository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Invoice;
import com.example.demo.entity.dto.InvoicesWithProducts;

@Repository
public class InvoiceInsertRepository {
	@PersistenceContext
	EntityManager entityManager;
	
	
	@Transactional
	public void insertNewInvoice(Invoice invoice) {
	    entityManager.createNativeQuery("INSERT INTO invoice (id, customer_id, invoice_date, payment_status, delivery_status, refund_percentage, reason, currency, payment_method, description, intent, admin_acceptance, total_price) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)")
	      .setParameter(1, invoice.getId())
	      .setParameter(2, invoice.getCustomer().getId())
	      .setParameter(3, invoice.getInvoiceDate())
	      .setParameter(4, invoice.getPaymentStatus())
	      .setParameter(5, invoice.getDeliveryStatus())
	      .setParameter(6, invoice.getRefundPercentage())
	      .setParameter(7, invoice.getReason())
	      .setParameter(8, invoice.getCurrency())
	      .setParameter(9, invoice.getPaymentMethod())
	      .setParameter(10, invoice.getDescription())
	      .setParameter(11, invoice.getIntent())
	      .setParameter(12, invoice.getAdminAcceptance())
	      .setParameter(13, invoice.getTotalPrice())
	      .executeUpdate();
	}
	
	@Transactional
	public void insertInvoicesWithProducts(InvoicesWithProducts invoicesWithProducts) {
		 entityManager.createNativeQuery("INSERT INTO invoices_with_products (invoice_id, product_id, quantity) VALUES (?,?,?)")
	      .setParameter(1, invoicesWithProducts.getId().getInvoiceId())
	      .setParameter(2, invoicesWithProducts.getId().getProductId())
	      .setParameter(3, invoicesWithProducts.getQuantity())
	      .executeUpdate();
	}
}
