package co.edu.local.gestionIcfes.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import co.edu.local.gestionIcfes.dto.PersonaDTO;
import co.edu.local.gestionIcfes.dto.UsuarioDTO;
import co.edu.local.gestionIcfes.model.Docente;
import co.edu.local.gestionIcfes.model.Estudiante;
import co.edu.local.gestionIcfes.model.Institucion;
import co.edu.local.gestionIcfes.model.ResultadoSimulacro;
import co.edu.local.gestionIcfes.model.Usuario;

public interface UsuarioServices extends UserDetailsService{

	public Usuario crearAdmin(UsuarioDTO usuarioDTO);
	public Estudiante crearEstudiante(PersonaDTO personaDTO);
	public Docente crearDocente(PersonaDTO personaDTO);
	public void eliminarUsuario(Long id);
	public boolean validarUsername(String username);
	public Long obtenerIdMasAlto(List<Usuario> usuarios);
	public List<Estudiante> listarEstudiantes();
	public List<Estudiante> listarEstudiantesConResultados();
	public ResultadoSimulacro obtenerResultadoPorId(Long id);
	public Usuario editarSalonInstitucion(String salon,Institucion institucion, Long id);
	public Long cantidadEstudiantes();
	public Long cantidadDocentes();
	public Long cantidadEstudiantesActivos();
	public Long cantidadDocentesActivos();
	public List<Usuario> listarTodosUsuarios();
	public boolean cambiarPassword(Long id, String passwordActual, String passwordNueva);
	public void cambiarPasswordAdmin(Long id, String passwordNueva);
	public void toggleActivo(Long id);
	public boolean restablecerPassword(Long id);
	public boolean cambiarUsername(Long id, String nuevoUsername);
}