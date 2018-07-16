package com.ecommerce.webservices.service.dao.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ProductRowMapper implements RowMapper<Product>
{
	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		Product product = new Product();
		product.setProductId( rs.getString("PRODUCT_ID") );
		product.setProductName( rs.getString("PRODUCT_NAME") );
		product.setProductPrice( rs.getDouble("PRODUCT_PRICE") );
		product.setCatId( rs.getString("CAT_ID") );
		return product;
	}
}
