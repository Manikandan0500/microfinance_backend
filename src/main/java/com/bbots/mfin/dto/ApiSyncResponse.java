package com.bbots.mfin.dto;

import org.springframework.http.HttpStatus;

public class ApiSyncResponse {
	 private boolean success;
	    private String message;
	    private Integer statusCode;
 
	    public ApiSyncResponse() {
	    }
 
	    public ApiSyncResponse(boolean success, String message, Integer statusCode) {
	        this.success = success;
	        this.message = message;
	        this.statusCode = statusCode;
	    }
 
	    public boolean isSuccess() {
	        return success;
	    }
 
	    public void setSuccess(boolean success) {
	        this.success = success;
	    }
 
	    public String getMessage() {
	        return message;
	    }
 
	    public void setMessage(String message) {
	        this.message = message;
	    }
 
		public Integer getStatusCode() {
			return statusCode;
		}
 
		public void setStatusCode(Integer statusCode) {
			this.statusCode = statusCode;
		}
 
	  
}
 
