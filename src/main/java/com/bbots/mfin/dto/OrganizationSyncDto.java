package com.bbots.mfin.dto;

import java.util.List;

public class OrganizationSyncDto {
	
	private OrganizationDto organization;
	
	private  List<ProductMappedDto> mappedProducts;

	public OrganizationDto getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}

	public List<ProductMappedDto> getMappedProducts() {
		return mappedProducts;
	}

	public void setMappedProducts(List<ProductMappedDto> mappedProducts) {
		this.mappedProducts = mappedProducts;
	}
	
	

}
