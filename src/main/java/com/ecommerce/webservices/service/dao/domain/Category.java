package com.ecommerce.webservices.service.dao.domain;

import lombok.Data;

@Data
public class Category
{
	private String catId;
	private String parentCatId;
	private String catName;
}
