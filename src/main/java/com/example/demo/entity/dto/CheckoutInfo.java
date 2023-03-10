package com.example.demo.entity.dto;

public class CheckoutInfo {
	String fullName;
	String country;
	String address;
	String city;
	String phone;
	String email;
	String invoiceNote;
	String paymentIntentNote;
	
	
	
	public CheckoutInfo() {}

	public CheckoutInfo(String fullName,String country, String address, String city, String phone,
			String email, String invoiceNote, String paymentIntentNote) {
		super();
		this.fullName = fullName;
		this.country = country;
		this.address = address;
		this.city = city;
		this.phone = phone;
		this.email = email;
		this.invoiceNote = invoiceNote;
		this.paymentIntentNote = paymentIntentNote;
	}

	
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String firstName) {
		this.fullName = firstName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInvoiceNote() {
		return invoiceNote;
	}

	public void setInvoiceNote(String invoiceNote) {
		this.invoiceNote = invoiceNote;
	}

	public String getPaymentIntentNote() {
		return paymentIntentNote;
	}

	public void setPaymentIntentNote(String paymentIntentNote) {
		this.paymentIntentNote = paymentIntentNote;
	}
}
