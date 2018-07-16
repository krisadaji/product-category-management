package com.ecommerce.webservices.service.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ecommerce.webservices.service.dao.domain.CategoryProduct;
import com.ecommerce.webservices.service.dao.domain.CategoryProductRowMapper;

@Repository
public class CategoryProductDAOImpl
{
	private JdbcTemplate jdbcTemplate;
	
	public CategoryProductDAOImpl( JdbcTemplate jdbcTemplate )
	{
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<CategoryProduct> findAllProducts( String catId )
	{
		String sql = "SELECT b.CAT_ID, b.PARENT_CAT_ID, b.CAT_NAME, " +
	                 "a.PRODUCT_ID, a.PRODUCT_NAME, a.PRODUCT_PRICE " +
	                 "FROM PRODUCT a " +
	                 "INNER JOIN CATEGORY b WHERE b.CAT_ID = a.CAT_ID " +
	                 "AND b.CAT_ID=? " +
	                 "ORDER BY a.PRODUCT_ID " +
	                 "LIMIT 10";
		return jdbcTemplate.query(sql, 
				                  new Object[] { catId }, 
				                  new CategoryProductRowMapper());
	}
	
	public List<CategoryProduct> findProductsByPage( String catId, int offset )
	{
		String sql = "SELECT b.CAT_ID, b.PARENT_CAT_ID, b.CAT_NAME, " +
	                 "a.PRODUCT_ID, a.PRODUCT_NAME, a.PRODUCT_PRICE " +
	                 "FROM PRODUCT a " +
	                 "INNER JOIN CATEGORY b WHERE b.CAT_ID = a.CAT_ID " +
	                 "AND b.CAT_ID=? " +
	                 "ORDER BY a.CREATE_DATE DESC " +
	                 "LIMIT 10 " +
	                 "OFFSET ?";
		return jdbcTemplate.query(sql, 
				                  new Object[] { catId, offset }, 
				                  new CategoryProductRowMapper());
	}
	
	public List<CategoryProduct> findProductsByPageNamePrice( String catId, int offset, String name, String price )
	{
		String sql = "SELECT b.CAT_ID, b.PARENT_CAT_ID, b.CAT_NAME, " +
	                 "a.PRODUCT_ID, a.PRODUCT_NAME, a.PRODUCT_PRICE " +
	                 "FROM PRODUCT a " +
	                 "INNER JOIN CATEGORY b WHERE b.CAT_ID = a.CAT_ID " +
	                 "AND b.CAT_ID=? " + name + price +
	                 "ORDER BY a.CREATE_DATE DESC " +
	                 "LIMIT 10 " +
	                 "OFFSET ?";
		return jdbcTemplate.query(sql, 
				                  new Object[] { catId, offset }, 
				                  new CategoryProductRowMapper());
	}
}
