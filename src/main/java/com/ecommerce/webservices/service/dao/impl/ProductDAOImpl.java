package com.ecommerce.webservices.service.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ecommerce.webservices.service.dao.domain.Product;
import com.ecommerce.webservices.service.dao.domain.ProductRowMapper;

@Repository
public class ProductDAOImpl
{
	private JdbcTemplate jdbcTemplate;
	
	public ProductDAOImpl( JdbcTemplate jdbcTemplate )
	{
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Product> findProductById( String id )
	{
		String sql = "SELECT PRODUCT_ID, PRODUCT_NAME, PRODUCT_PRICE, CAT_ID " +
	                 "FROM PRODUCT " +
	                 "WHERE PRODUCT_ID=?";
		return jdbcTemplate.query(sql, 
				                  new Object[] { id }, 
				                  new ProductRowMapper());
	}
	
	public void addProduct( String productId, String productName, double productPrice, String catId )
	{
		String sql = "INSERT INTO PRODUCT(PRODUCT_ID,PRODUCT_NAME,PRODUCT_PRICE,CAT_ID,CREATE_DATE) VALUES (?, ?, ?, ?, now())";
		jdbcTemplate.update(sql, new Object[] { productId, productName, productPrice, catId });
	}
	
	public void deleteProduct( String productId )
	{
		String sql = "DELETE FROM PRODUCT WHERE PRODUCT_ID=?";
		jdbcTemplate.update( sql, new Object[] { productId } );
	}
	
	public void updateProduct( String productId, String productName, double productPrice, String catId )
	{
		String sql = "UPDATE PRODUCT SET PRODUCT_NAME=?, PRODUCT_PRICE=?, CAT_ID=? WHERE PRODUCT_ID=?";
		jdbcTemplate.update( sql, new Object[] { productName, productPrice, catId, productId } );
	}
	
	public void updateProductCatId( String oldCatId, String newCatId )
	{
		String sql = "UPDATE PRODUCT SET CAT_ID=? WHERE CAT_ID=?";
		jdbcTemplate.update( sql, new Object[] { newCatId, oldCatId } );
	}
}
