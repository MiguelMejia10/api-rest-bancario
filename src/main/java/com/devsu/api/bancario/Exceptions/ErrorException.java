package com.devsu.api.bancario.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ErrorException extends RuntimeException
{
	public ErrorException(String message)
	{
		super(message);
	}

}
