package com.example.demo.entity.dto;

import java.util.Date;

public class NewProductInfo {
	String name;
	String[] sizeList;
	String brand;
	String color;
	double sellingPrice;
	double originalPrice;
	String description;
	String[] catalogList;
	Date importDate;
	String image1Url;
	String image2Url;
	String image3Url;
	String image4Url;
	
	
	
	public NewProductInfo() {}
	
	public NewProductInfo(String name, String[] sizeList, String brand, String color, double sellingPrice, double originalPrice,
			String description, String[] catalogList, Date importDate, String image1Url, String image2Url,
			String image3Url, String image4Url) {
		super();
		this.name = name;
		this.sizeList = sizeList;
		this.brand = brand;
		this.sellingPrice = sellingPrice;
		this.originalPrice = originalPrice;
		this.description = description;
		this.catalogList = catalogList;
		this.importDate = importDate;
		this.image1Url = image1Url;
		this.image2Url = image2Url;
		this.image3Url = image3Url;
		this.image4Url = image4Url;
		this.color = color;
	}

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getSizeList() {
		return sizeList;
	}

	public void setSizeList(String[] sizeList) {
		this.sizeList = sizeList;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getCatalogList() {
		return catalogList;
	}

	public void setCatalogList(String[] catalogList) {
		this.catalogList = catalogList;
	}

	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public String getImage1Url() {
		return image1Url;
	}

	public void setImage1Url(String image1Url) {
		this.image1Url = image1Url;
	}

	public String getImage2Url() {
		return image2Url;
	}

	public void setImage2Url(String image2Url) {
		this.image2Url = image2Url;
	}

	public String getImage3Url() {
		return image3Url;
	}

	public void setImage3Url(String image3Url) {
		this.image3Url = image3Url;
	}

	public String getImage4Url() {
		return image4Url;
	}

	public void setImage4Url(String image4Url) {
		this.image4Url = image4Url;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
