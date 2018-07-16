package com.ecommerce.webservices.service.dao.domain;

import lombok.Data;

@Data
public class CategoryProduct
{
	private String catId;
	private String parentCatId;
	private String catName;
	private String productId;
	private String productName;
	private double productPrice;
}
