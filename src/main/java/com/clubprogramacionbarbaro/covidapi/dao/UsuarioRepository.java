package com.clubprogramacionbarbaro.covidapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clubprogramacionbarbaro.covidapi.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

	Usuario findByEmailAndContrasena(String email, String contrasena);
}
