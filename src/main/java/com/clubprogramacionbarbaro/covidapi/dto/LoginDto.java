package com.clubprogramacionbarbaro.covidapi.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginDto {
	
	@NotBlank(message = "El correo no puede ser nulo o vacio")
	@Email
	private String email;
	
	@NotBlank(message = "La contraseña es necesaria")
	private String contrasena;
}
