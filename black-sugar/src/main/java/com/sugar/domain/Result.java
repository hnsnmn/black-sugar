package com.sugar.domain;

public class Result {
	private boolean valid;
	private String errorMessage;
	
	private Result() {
		this(true, null);
	}
	
	private Result(boolean valid, String errorMessage) {
		this.valid = valid;
		this.errorMessage = errorMessage; 
	}
	
	public static Result ok() {
		return new Result(true, null);
	}
	
	public boolean getValid() {
		return valid;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public static Result fail(String errorMessage) {
		return new Result(false, errorMessage);
	}

}
