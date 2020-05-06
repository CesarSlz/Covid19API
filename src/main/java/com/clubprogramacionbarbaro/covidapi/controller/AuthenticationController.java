package com.clubprogramacionbarbaro.covidapi.controller;

import java.util.Calendar;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clubprogramacionbarbaro.covidapi.dao.UsuarioRepository;
import com.clubprogramacionbarbaro.covidapi.model.Usuario;
import com.clubprogramacionbarbaro.dto.LoginDTO;
import com.clubprogramacionbarbaro.dto.MessageDto;
import com.clubprogramacionbarbaro.dto.TokenDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/autenticacion")
@AllArgsConstructor
public class AuthenticationController {

	private UsuarioRepository usuarioRepository;
	
	@PostMapping
	public ResponseEntity<?> aunthenticate(@RequestBody LoginDTO loginDto) {
		Usuario usuario = usuarioRepository.findByEmailAndContrasena(loginDto.getEmail(),loginDto.getContrasena());
		
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
			MessageDto messageDto = new MessageDto("Error", "Correo o contraseña incorrectos");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageDto);
		}
		
	}
}
