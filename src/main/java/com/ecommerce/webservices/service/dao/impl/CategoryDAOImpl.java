package com.ecommerce.webservices.service.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ecommerce.webservices.service.dao.domain.Category;
import com.ecommerce.webservices.service.dao.domain.CategoryRowMapper;

@Repository
public class CategoryDAOImpl
{
private JdbcTemplate jdbcTemplate;
	
	public CategoryDAOImpl( JdbcTemplate jdbcTemplate )
	{
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Category> findAllCategories()
	{
		String sql = "SELECT CAT_ID, PARENT_CAT_ID, CAT_NAME " +
	                 "FROM CATEGORY " +
	                 "ORDER BY CAT_ID ";
		return jdbcTemplate.query(sql,  
				                  new CategoryRowMapper() );
	}
	
	public List<Category> findCategoryById( String id )
	{
		String sql = "SELECT CAT_ID, PARENT_CAT_ID, CAT_NAME " +
	                 "FROM CATEGORY " +
	                 "WHERE CAT_ID=?";
		return jdbcTemplate.query(sql,
				                  new Object[] { id },
				                  new CategoryRowMapper() );
	}
	
	public void addCategory( String catId, String parentCatId, String catName )
	{
		String sql = "INSERT INTO CATEGORY(CAT_ID,PARENT_CAT_ID,CAT_NAME,CREATE_DATE) VALUES (?, ?, ?, now())";
		jdbcTemplate.update(sql, new Object[] { catId, parentCatId, catName });
	}
	
	public void deleteCategory( String catID )
	{
		String sql = "DELETE FROM PRODUCT WHERE CAT_ID=?";
		jdbcTemplate.update( sql, new Object[] { catID } );
		
		sql = "DELETE FROM CATEGORY WHERE CAT_ID=?";
		jdbcTemplate.update( sql, new Object[] { catID } );
		
		sql = "UPDATE CATEGORY SET PARENT_CAT_ID=NULL WHERE PARENT_CAT_ID=?";
		jdbcTemplate.update( sql, new Object[] { catID } );
	}
	
	public void updateCategory( String catId, String newCatId, String parentCatId, String catName )
	{
		String sql = "UPDATE CATEGORY SET CAT_ID=?, PARENT_CAT_ID=?, CAT_NAME=? WHERE CAT_ID=?";
		jdbcTemplate.update( sql, new Object[] { newCatId, parentCatId, catName, catId } );
		
		sql = "SELECT CAT_ID, PARENT_CAT_ID, CAT_NAME " +
              "FROM CATEGORY " +
              "WHERE CAT_ID=? AND PARENT_CAT_ID=NULL";
		List<Category> categories = jdbcTemplate.query(sql,
                                                       new Object[] { catId },
                                                       new CategoryRowMapper() );
		if ( CollectionUtils.isNotEmpty( categories ) )
		{
			sql = "UPDATE CATEGORY SET CAT_ID=?, CAT_NAME=? WHERE CAT_ID=? AND PARENT_CAT_ID=NULL";
			jdbcTemplate.update( sql, new Object[] { newCatId, catName, catId } );
		}
	}
	
	public void updateParentCategory( String catId, String parentCatId )
	{
		String sql = "UPDATE CATEGORY SET PARENT_CAT_ID=? WHERE CAT_ID=?";
		jdbcTemplate.update( sql );
	}
}
