package com.ecommerce.webservices.service.dao.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CategoryRowMapper implements RowMapper<Category>
{
	@Override
	public Category mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		Category cat = new Category();
		cat.setCatId( rs.getString("CAT_ID") );
		cat.setParentCatId( rs.getString("PARENT_CAT_ID") );
		cat.setCatName( rs.getString("CAT_NAME") );
		return cat;
	}
}
