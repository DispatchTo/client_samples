package com.chqit.dispatch;

import java.util.List;

public class Result {
	enum ResultEnum {
		VALID,
		INVALID
	};

	String email;
	List<Error> errors;
	ResultEnum result;
	
	public Result(String email, ResultEnum result) {
		setEmail(email);
		setResult(result);
	}
	
	public Result(String email, ResultEnum result, List<Error> errors) {
		setEmail(email);
		setResult(result);
		setErrors(errors);
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Error> getErrors() {
		return errors;
	}
	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
	public ResultEnum getResult() {
		return result;
	}
	public void setResult(ResultEnum result) {
		this.result = result;
	}
}
