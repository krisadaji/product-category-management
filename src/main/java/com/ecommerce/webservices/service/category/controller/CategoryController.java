package com.ecommerce.webservices.service.category.controller;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.webservices.service.controller.BaseAPIRestController;
import com.ecommerce.webservices.service.dao.domain.Category;
import com.ecommerce.webservices.service.dao.impl.CategoryDAOImpl;
import com.ecommerce.webservices.service.dao.impl.ProductDAOImpl;
import com.ecommerce.webservices.service.request.dto.CategoryRequestDTO;
import com.ecommerce.webservices.service.response.ErrorResponse;
import com.ecommerce.webservices.service.response.SuccessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class CategoryController extends BaseAPIRestController
{
	@Autowired
	private CategoryDAOImpl categoryDAOImpl;
	
	@Autowired
	private ProductDAOImpl productDAOImpl;
	
	@GetMapping("/categories")
	public ResponseEntity getCategoryResponse() throws JsonProcessingException
	{
		List<Category> products = categoryDAOImpl.findAllCategories();
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString( products );	
		return new ResponseEntity<String>( jsonStr, HttpStatus.OK );
	}
	
	@PostMapping("/categories")
	public ResponseEntity addCategoryResponse( @RequestBody CategoryRequestDTO categoryRequestDTO ) throws JsonProcessingException
	{
		String catId = categoryRequestDTO.getCatId();
		List<Category> categories = categoryDAOImpl.findCategoryById( catId );
		if ( CollectionUtils.isNotEmpty( categories ) )
		{
			ErrorResponse errorResponse = ErrorResponse.builder()
                                                       .errorId( 3 )
                                                       .errorMessage( "Categry ID: " + catId + " exists." )
                                                       .build();
			return new ResponseEntity<ErrorResponse>( errorResponse, HttpStatus.BAD_REQUEST );
		}
		
		categoryDAOImpl.addCategory( categoryRequestDTO.getCatId(),
				                     categoryRequestDTO.getParentCatId(),
				                     categoryRequestDTO.getCatName() );
		
		SuccessResponse successResponse = SuccessResponse.builder()
                                                         .message( "Category ID: " + categoryRequestDTO.getCatId() + " added." )
                                                         .build();
		return new ResponseEntity<SuccessResponse>( successResponse, HttpStatus.OK ); 
	}
	
	@PostMapping("/categories/{id}")
	public ResponseEntity editCategoryResponse( @PathVariable String id,
			                                    @RequestBody CategoryRequestDTO categoryRequestDTO ) throws JsonProcessingException
	{
		List<Category> categories = categoryDAOImpl.findCategoryById( id );
		if ( CollectionUtils.isEmpty( categories ) )
		{
			ErrorResponse errorResponse = ErrorResponse.builder()
					                                   .errorId( 4 )
					                                   .errorMessage( "Categry ID: " + id + " does not exist." )
					                                   .build();
			return new ResponseEntity<ErrorResponse>( errorResponse, HttpStatus.BAD_REQUEST );
		}
		
		String parentCat = categoryRequestDTO.getParentCatId();
		List<Category> parentCategories = categoryDAOImpl.findCategoryById( parentCat );
		if ( CollectionUtils.isEmpty( parentCategories ) )
		{
			ErrorResponse errorResponse = ErrorResponse.builder()
                                                       .errorId( 4 )
                                                       .errorMessage( "Categry ID: " + id + " does not exist." )
                                                       .build();
			return new ResponseEntity<ErrorResponse>( errorResponse, HttpStatus.BAD_REQUEST );
		}
		
		categoryDAOImpl.updateCategory( id,
				                        categoryRequestDTO.getCatId(),
				                        categoryRequestDTO.getParentCatId(),
				                        categoryRequestDTO.getCatName() );
		
		productDAOImpl.updateProductCatId( id, categoryRequestDTO.getCatId() );
		
		SuccessResponse successResponse = SuccessResponse.builder()
				                                         .message( "Category ID: " + id + " updated." )
				                                         .build();
		return new ResponseEntity<SuccessResponse>( successResponse, HttpStatus.OK ); 
	}
	
	@DeleteMapping("/categories/{id}")
	public ResponseEntity deleteProductResponse( @PathVariable String id ) throws JsonProcessingException
	{
		List<Category> categories = categoryDAOImpl.findCategoryById( id );
		if ( CollectionUtils.isEmpty( categories ) )
		{
			ErrorResponse errorResponse = ErrorResponse.builder()
                                                       .errorId( 4 )
                                                       .errorMessage( "Categry ID: " + id + " does not exist." )
                                                       .build();
			return new ResponseEntity<ErrorResponse>( errorResponse, HttpStatus.BAD_REQUEST );
		}
		
		categoryDAOImpl.deleteCategory( id );
		
		SuccessResponse successResponse = SuccessResponse.builder()
                                                         .message( "Category ID: " + id + " deleted." )
                                                         .build();
		return new ResponseEntity<SuccessResponse>( successResponse, HttpStatus.OK );
	}
}
