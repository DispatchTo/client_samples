package com.chqit.dispatch;

public class Error {
	int errorCode;
	String errorMessage;
	
	public Error(int errorCode, String errorMessage) {
		setErrorCode(errorCode);
		setErrorMessage(errorMessage);
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}