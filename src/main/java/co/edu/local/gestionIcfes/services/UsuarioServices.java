package co.edu.local.gestionIcfes.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import co.edu.local.gestionIcfes.dto.UsuarioDTO;
import co.edu.local.gestionIcfes.model.Usuario;

public interface UsuarioServices extends UserDetailsService{

	public Usuario crearUsuario(UsuarioDTO usuarioDTO);
	public boolean validarUsername(UsuarioDTO usuarioDTO);
}