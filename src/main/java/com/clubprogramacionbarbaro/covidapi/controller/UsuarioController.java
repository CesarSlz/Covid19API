package com.clubprogramacionbarbaro.covidapi.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clubprogramacionbarbaro.covidapi.dao.UsuarioRepository;
import com.clubprogramacionbarbaro.covidapi.model.Usuario;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {

	private UsuarioRepository usuarioRepository;
	
	@PostMapping
	public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
		usuario.setCreatedAt(new Date());
		return new ResponseEntity<Usuario>(usuarioRepository.save(usuario), HttpStatus.CREATED);
		
	}
}
