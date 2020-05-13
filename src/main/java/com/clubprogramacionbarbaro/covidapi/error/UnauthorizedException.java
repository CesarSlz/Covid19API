package com.clubprogramacionbarbaro.covidapi.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException{

	private static final long serialVersionUID = -6558174833541684850L;

	public UnauthorizedException() {
		super("Credenciales no validas");
	}
}
