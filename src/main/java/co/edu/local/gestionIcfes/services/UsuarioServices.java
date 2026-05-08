package co.edu.local.gestionIcfes.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import co.edu.local.gestionIcfes.dto.PersonaDTO;
import co.edu.local.gestionIcfes.model.Docente;
import co.edu.local.gestionIcfes.model.Estudiante;
import co.edu.local.gestionIcfes.model.Usuario;

public interface UsuarioServices extends UserDetailsService{

	public Estudiante crearEstudiante(PersonaDTO personaDTO);
	public Docente crearDocente(PersonaDTO personaDTO);
	public boolean validarUsername(String username);
	public Long obtenerIdMasAlto(List<Usuario> usuarios);
}