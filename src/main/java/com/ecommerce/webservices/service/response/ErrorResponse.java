package com.ecommerce.webservices.service.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="response")
public class ErrorResponse
{
	private int errorId;
	private String errorMessage;
}
