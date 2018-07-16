package com.ecommerce.webservices.service.request.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="request")
public class ProductRequestDTO
{
	private String productId;
	private String productName;
	private double productPrice;
	private String catId;
}
