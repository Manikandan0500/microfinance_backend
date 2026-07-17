package com.bbots.mfin.dto;

import java.util.List;

/**
 * Response DTO for the /product/products-sync endpoint.
 * Returns all available products alongside the mapped products for the requesting org.
 * Mirrors com.am.dto.ProductSyncResponseDto from AccessManager.
 */
public class ProductSyncResponseDto {

    /** All products in product001 (global list). */
    private List<ProductDto> products;

    /** Products mapped to the specific org in product002. */
    private List<ProductMappedDto> mappedProducts;

    public List<ProductDto> getProducts() { return products; }
    public void setProducts(List<ProductDto> products) { this.products = products; }

    public List<ProductMappedDto> getMappedProducts() { return mappedProducts; }
    public void setMappedProducts(List<ProductMappedDto> mappedProducts) { this.mappedProducts = mappedProducts; }
}
