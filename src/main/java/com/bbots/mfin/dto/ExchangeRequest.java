package com.bbots.mfin.dto;

public class ExchangeRequest {
	 private Integer product;

	    public ExchangeRequest() {}

	    public ExchangeRequest(Integer product) {
	        this.product = product;
	    }

	    public Integer getProductCode() { return product; }
	    public void setProductCode(Integer product) { this.product = product; }

}
