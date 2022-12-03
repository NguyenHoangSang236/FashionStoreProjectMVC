package com.example.demo.entity.embededkey;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class InvoicesWithProductsPrimaryKeys implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "product_id")
    int productId;

    @Column(name = "invoice_id")
    int invoiceId;

    
    
	public InvoicesWithProductsPrimaryKeys(int productId, int invoiceId) {
		super();
		this.productId = productId;
		this.invoiceId = invoiceId;
	}



	public int getProductId() {
		return productId;
	}



	public void setProductId(int productId) {
		this.productId = productId;
	}



	public int getInvoiceId() {
		return invoiceId;
	}



	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}
}
