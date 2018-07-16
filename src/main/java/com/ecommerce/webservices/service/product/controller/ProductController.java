package com.ecommerce.webservices.service.product.controller;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.webservices.service.controller.BaseAPIRestController;
import com.ecommerce.webservices.service.dao.domain.CategoryProduct;
import com.ecommerce.webservices.service.dao.domain.Product;
import com.ecommerce.webservices.service.dao.impl.CategoryProductDAOImpl;
import com.ecommerce.webservices.service.dao.impl.ProductDAOImpl;
import com.ecommerce.webservices.service.request.dto.ProductRequestDTO;
import com.ecommerce.webservices.service.response.ErrorResponse;
import com.ecommerce.webservices.service.response.SuccessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ProductController extends BaseAPIRestController
{
	@Autowired
	private CategoryProductDAOImpl categoryProductDAOImpl;
	
	@Autowired
	private ProductDAOImpl productDAOImpl;
	
	@GetMapping("/products/cat/{id}")
	public ResponseEntity getProductResponse( @PathVariable String id ) throws JsonProcessingException
	{
		List<CategoryProduct> products = categoryProductDAOImpl.findAllProducts( id );
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString( products );
		return new ResponseEntity<String>( jsonStr, HttpStatus.OK );
	}
	
	@GetMapping("/products/cat/{id}/{no}")
	public ResponseEntity getProductResponse( @PathVariable String id, 
			                                  @PathVariable String no,
			                                  @RequestParam(value="n", required=false) final String name,
			                                  @RequestParam(value="p", required=false) final String price) throws JsonProcessingException
	{
		if ( StringUtils.isBlank( no ) )
		{
			return null;
		}
		
		int offset = (Integer.parseInt(no) - 1) * 10;
		
		String qName = handleName(name);
		String qPrice = handlePrice(price);
		
		List<CategoryProduct> products = categoryProductDAOImpl.findProductsByPageNamePrice( id, 
				                                                                             offset,
				                                                                             qName,
				                                                                             qPrice );
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString( products );
		return new ResponseEntity<String>( jsonStr, HttpStatus.OK );
	}
	
	@PostMapping("/products")
	public ResponseEntity addProductResponse( @RequestBody ProductRequestDTO productRequestDTO ) throws JsonProcessingException
	{
		String productId = productRequestDTO.getProductId();
		List<Product> products = productDAOImpl.findProductById( productRequestDTO.getProductId() );
		if ( CollectionUtils.isNotEmpty( products ) )
		{
			ErrorResponse errorResponse = ErrorResponse.builder()
                                                       .errorId( 1 )
                                                       .errorMessage( "Product ID: " + productId + " exists." )
                                                       .build();
			return new ResponseEntity<ErrorResponse>( errorResponse, HttpStatus.BAD_REQUEST );
		}
		
		productDAOImpl.addProduct( productId,
				                   productRequestDTO.getProductName(),
				                   productRequestDTO.getProductPrice(),
				                   productRequestDTO.getCatId() );
		
		SuccessResponse successResponse = SuccessResponse.builder()
                                                         .message( "Product ID: " + productRequestDTO.getProductId() + " added." )
                                                         .build();
		return new ResponseEntity<SuccessResponse>( successResponse, HttpStatus.OK ); 
	}
	
	@PostMapping("/products/{id}")
	public ResponseEntity editProductResponse( @PathVariable String id,
			                                   @RequestBody ProductRequestDTO productRequestDTO ) throws JsonProcessingException
	{
		List<Product> products = productDAOImpl.findProductById( id );
		if ( CollectionUtils.isEmpty( products ) )
		{
			ErrorResponse errorResponse = ErrorResponse.builder()
                                                       .errorId( 2 )
                                                       .errorMessage( "Product ID: " + id + " does not exist." )
                                                       .build();
			return new ResponseEntity<ErrorResponse>( errorResponse, HttpStatus.BAD_REQUEST );
		}
		
		productDAOImpl.updateProduct( id, 
				                      productRequestDTO.getProductName(),
				                      productRequestDTO.getProductPrice(),
				                      productRequestDTO.getCatId() );
		
		SuccessResponse successResponse = SuccessResponse.builder()
                                                         .message( "Product ID: " + id + " updated." )
                                                         .build();
		return new ResponseEntity<SuccessResponse>( successResponse, HttpStatus.OK ); 
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity deleteProductResponse( @PathVariable String id ) throws JsonProcessingException
	{
		List<Product> products = productDAOImpl.findProductById( id );
		if ( CollectionUtils.isEmpty( products ) )
		{
			ErrorResponse errorResponse = ErrorResponse.builder()
                                                       .errorId( 2 )
                                                       .errorMessage( "Product ID: " + id + " does not exist." )
                                                       .build();
			return new ResponseEntity<ErrorResponse>( errorResponse, HttpStatus.BAD_REQUEST );
		}
		
		productDAOImpl.deleteProduct( id );
		
		SuccessResponse successResponse = SuccessResponse.builder()
                                                         .message( "Product ID: " + id + " deleted." )
                                                         .build();
		return new ResponseEntity<SuccessResponse>( successResponse, HttpStatus.OK );
	}

	private String handleName(String name)
	{
		if ( StringUtils.isNotBlank( name ) )
		{
			return " AND a.PRODUCT_NAME LIKE \'%" + name + "%\' "; 
		}
		return "";
	}

	private String handlePrice(String price)
	{
		if ( StringUtils.isNotBlank( price )  )
		{
			String[] prices = price.split("-");
			int length = prices.length;
			if ( length == 1 )
			{
				return " AND a.PRODUCT_PRICE > " + prices[0] + " ";
			}
			else if ( length == 2 )
			{
				return " AND a.PRODUCT_PRICE BETWEEN " + 
			           ( "".equals( prices[0] ) ? "0" : prices[0] ) + 
			           " AND " +
				       ( "".equals( prices[1] ) ? "0" : prices[1] ) + " ";
			}
		}
		return "";
	}
}
