package com.ecommerce.webservices.service.dao.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CategoryProductRowMapper implements RowMapper<CategoryProduct>
{
	@Override
	public CategoryProduct mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		CategoryProduct catProd = new CategoryProduct();
		catProd.setCatId( rs.getString("CAT_ID") );
		catProd.setParentCatId( rs.getString("PARENT_CAT_ID") );
		catProd.setCatName( rs.getString("CAT_NAME") );
		catProd.setProductId( rs.getString("PRODUCT_ID") );
		catProd.setProductName( rs.getString("PRODUCT_NAME") );
		catProd.setProductPrice( rs.getDouble("PRODUCT_PRICE") );
		return catProd;
	}

}
