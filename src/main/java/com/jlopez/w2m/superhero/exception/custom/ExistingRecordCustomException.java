package com.jlopez.w2m.superhero.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Record Exists")
public class ExistingRecordCustomException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private static final String DESCRIPTION = "Record Exists Exception";

	public ExistingRecordCustomException(String detail) {
		super(DESCRIPTION + ". " + detail);
	}

}
