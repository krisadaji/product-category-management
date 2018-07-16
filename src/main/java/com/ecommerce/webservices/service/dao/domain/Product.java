package com.ecommerce.webservices.service.dao.domain;

import lombok.Data;

@Data
public class Product
{
	private String productId;
	private String productName;
	private double productPrice;
	private String catId;
}
