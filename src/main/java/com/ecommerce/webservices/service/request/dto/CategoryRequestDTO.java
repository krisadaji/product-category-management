package com.ecommerce.webservices.service.request.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="request")
public class CategoryRequestDTO
{
	private String catId;
	private String parentCatId;
	private String catName;
}
