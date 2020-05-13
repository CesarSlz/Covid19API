package com.clubprogramacionbarbaro.covidapi.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.clubprogramacionbarbaro.covidapi.dao.UsuarioRepository;
import com.clubprogramacionbarbaro.covidapi.model.Usuario;
import com.clubprogramacionbarbaro.covidapi.dto.LoginDto;
import com.clubprogramacionbarbaro.covidapi.dto.MessageDto;
import com.clubprogramacionbarbaro.covidapi.dto.TokenDto;
import com.clubprogramacionbarbaro.covidapi.error.UnauthorizedException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/autenticacion")
@AllArgsConstructor
public class AuthenticationController {

	private UsuarioRepository usuarioRepository;
	
	@PostMapping
	public ResponseEntity<?> aunthenticate(@Valid @RequestBody LoginDto loginDto) {
		Usuario usuario = usuarioRepository.findByEmailAndContrasena(loginDto.getEmail(), loginDto.getContrasena());
		
		if(usuario != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR, 1);
			
			String jwt = Jwts.builder()
					.setSubject(usuario.getEmail())
					.claim("role", "user")
					.setExpiration(calendar.getTime())
					.signWith(SignatureAlgorithm.HS256, "12345678")
					.compact();
			
			TokenDto tokenDto = new TokenDto(jwt);
			
			return new ResponseEntity<>(tokenDto, HttpStatus.OK);
		}else {
			throw new UnauthorizedException();	
		}
		/*}else {
			MessageDto messageDto = new MessageDto("Error", "Correo o contraseña incorrectos");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageDto);
		}*/
		
	}
	
	/*@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String, Object> ValidationBeansException(MethodArgumentNotValidException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", HttpStatus.BAD_REQUEST.value());
		body.put("error", HttpStatus.BAD_REQUEST);
		body.put("message", ex.getBindingResult().getFieldError().getDefaultMessage());
		
		return body;
	}*/
}
