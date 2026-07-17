package com.bbots.mfin.dto;

public class AuthRequest {

    private String email;
    private String password;
    private Integer productCode;
   
	// Default constructor (Needed for Jackson to deserialize JSON)
    public AuthRequest() {
    }

    public AuthRequest(String email, String password, Integer productCode) {
        this.email = email;
        this.password = password;
        this.productCode = productCode;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public Integer getProductCode() {
		return productCode;
	}

	public void setProductCode(Integer product) {
		this.productCode = product;
	}
}