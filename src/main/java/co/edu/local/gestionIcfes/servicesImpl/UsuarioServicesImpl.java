package co.edu.local.gestionIcfes.servicesImpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.local.gestionIcfes.dto.PersonaDTO;
import co.edu.local.gestionIcfes.dto.UsuarioDTO;
import co.edu.local.gestionIcfes.model.Docente;
import co.edu.local.gestionIcfes.model.Estudiante;
import co.edu.local.gestionIcfes.model.Institucion;
import co.edu.local.gestionIcfes.model.ResultadoSimulacro;
import co.edu.local.gestionIcfes.model.Rol;
import co.edu.local.gestionIcfes.model.Usuario;
import co.edu.local.gestionIcfes.repository.DocenteRepositorio;
import co.edu.local.gestionIcfes.repository.EstudianteRepositorio;
import co.edu.local.gestionIcfes.repository.ResultadoSimulacroRepositorio;
import co.edu.local.gestionIcfes.repository.UsuarioRepositorio;
import co.edu.local.gestionIcfes.services.InstitucionService;
import co.edu.local.gestionIcfes.services.LogService;
import co.edu.local.gestionIcfes.services.RolServices;
import co.edu.local.gestionIcfes.services.UsuarioServices;
import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioServicesImpl implements UsuarioServices {

	@Autowired
	private UsuarioRepositorio usuarioRepository;

	@Autowired
	private EstudianteRepositorio estudianteRepository;

	@Autowired
	private DocenteRepositorio docenteRepository;
	
	@Autowired
	private ResultadoSimulacroRepositorio resultadoSimulacroRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RolServices rolServicio;
	
	@Autowired
	private LogService logService;

	@Autowired
	private InstitucionService institucionServicio;

	@Autowired
	private HttpSession session;

	@Override
	public Usuario crearAdmin(UsuarioDTO usuarioDTO) {
		Institucion institucion = usuarioDTO.getInstitucionId() != null
				? institucionServicio.buscarPorId(usuarioDTO.getInstitucionId())
				: null;
		Usuario usuario = new Usuario(usuarioDTO.getUsername(), passwordEncoder.encode(usuarioDTO.getPassword()), true,
				institucion, null, Arrays.asList(rolServicio.encontrarRol("ROLE_ADMIN")));
		return usuarioRepository.save(usuario);
	}

	@Override
	public Estudiante crearEstudiante(PersonaDTO personaDTO) {
		String nombreUsuario = personaDTO.getPrimerNombre() + personaDTO.getPrimerApellido().substring(0, 3)
				+ (obtenerIdMasAlto(usuarioRepository.findAll()) + 1);
		Usuario usuario = new Usuario(nombreUsuario, passwordEncoder.encode(personaDTO.getDocumentoIdentidad()), true,
				institucionServicio.buscarPorId(personaDTO.getInstitucion()), personaDTO.getSalon(),
				Arrays.asList(rolServicio.encontrarRol(personaDTO.getRol())));
		if (validarUsername(nombreUsuario)) {
			usuarioRepository.save(usuario);
			Estudiante estudiante = new Estudiante();
			estudiante.setTipoIdentificacion(personaDTO.getTipoIdentificacion());
			estudiante.setDocumentoIdentidad(personaDTO.getDocumentoIdentidad());
			estudiante.setPrimerNombre(personaDTO.getPrimerNombre());
			estudiante.setSegundoNombre(personaDTO.getSegundoNombre());
			estudiante.setPrimerApellido(personaDTO.getPrimerApellido());
			estudiante.setSegundoApellido(personaDTO.getSegundoApellido());
			estudiante.setCelular(personaDTO.getCelular());
			estudiante.setUsuario(usuario);
			estudiante.setInstitucion(institucionServicio.buscarPorId(personaDTO.getInstitucion()));
			logService.registrarLog("estudiante", "Registro nuevo estudiante " + personaDTO.getPrimerNombre());
			return estudianteRepository.save(estudiante);
		}
		return null;
	}
	
	@Override
	public void eliminarUsuario(Long id) {
		usuarioRepository.deleteById(id);	
		logService.registrarLog("usuario", "Usuario eliminado");
	}

	@Override
	public Docente crearDocente(PersonaDTO personaDTO) {
		String nombreUsuario = personaDTO.getPrimerNombre() + personaDTO.getPrimerApellido().substring(0, 3)
				+ (obtenerIdMasAlto(usuarioRepository.findAll()) + 1);
		Usuario usuario = new Usuario(nombreUsuario, passwordEncoder.encode(personaDTO.getDocumentoIdentidad()), true,
				institucionServicio.buscarPorId(personaDTO.getInstitucion()), personaDTO.getSalon(),
				Arrays.asList(rolServicio.encontrarRol(personaDTO.getRol())));
		if (validarUsername(nombreUsuario)) {
			usuarioRepository.save(usuario);
			Docente docente = new Docente();
			docente.setTipoIdentificacion(personaDTO.getTipoIdentificacion());
			docente.setDocumentoIdentidad(personaDTO.getDocumentoIdentidad());
			docente.setPrimerNombre(personaDTO.getPrimerNombre());
			docente.setSegundoNombre(personaDTO.getSegundoNombre());
			docente.setPrimerApellido(personaDTO.getPrimerApellido());
			docente.setSegundoApellido(personaDTO.getSegundoApellido());
			docente.setCelular(personaDTO.getCelular());
			docente.setEspecialidad(personaDTO.getEspecialidad());
			docente.setUsuario(usuario);
			docente.setInstitucion(institucionServicio.buscarPorId(personaDTO.getInstitucion()));
			logService.registrarLog("docente", "Registro nuevo docente " + personaDTO.getPrimerNombre());
			return docenteRepository.save(docente);
		}
		return null;
	}

	@Override
	public Long obtenerIdMasAlto(List<Usuario> usuarios) {
		return usuarios.stream().mapToLong(Usuario::getId).max().orElse(-1);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByUsername(username);
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuario o Password Inválidos");
		}
		session.setAttribute("idusuario", usuario.getId());
		boolean enabled = Boolean.TRUE.equals(usuario.getEnabled());
		return new User(usuario.getUsername(), usuario.getPassword(),
				enabled, true, true, true,
				mapearAutoridadRoles(usuario.getRoles()));
	}

	@Override
	public boolean validarUsername(String username) {
		Usuario usuario = usuarioRepository.findByUsername(username);

		if (usuario == null)
			return true;

		return false;
	}

	@Override
	public List<Estudiante> listarEstudiantes() {
		return estudianteRepository.findAll();
	}
	
	public List<Estudiante> listarEstudiantesConResultados() {
	    return estudianteRepository.listarEstudiantesConResultados();
	}
	
	@Override
	@Transactional(readOnly = true)
	public ResultadoSimulacro obtenerResultadoPorId(Long id) {
	    return resultadoSimulacroRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Resultado no encontrado"));
	}

	private Collection<? extends GrantedAuthority> mapearAutoridadRoles(Collection<Rol> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
	}
	
	@Override
	public Usuario editarSalonInstitucion(String salon, Institucion institucion, Long id) {
		Usuario usuario = usuarioRepository.findById(id).get();
		usuario.setSalon(salon);
		usuario.setInstitucion(institucion);
		logService.registrarLog("usuario", "Actualización datos usuario");
		return usuarioRepository.save(usuario);
	}
	

	@Override
	public Long cantidadDocentes() {
		return usuarioRepository.countByRoles_Nombre("ROLE_DOCENTE");
	}

	@Override
	public Long cantidadEstudiantes() {
		return usuarioRepository.countByRoles_Nombre("ROLE_ESTUDIANTE");
	}

	@Override
	public Long cantidadEstudiantesActivos() {
		return usuarioRepository.countByRoles_NombreAndEnabled("ROLE_ESTUDIANTE", true);
	}

	@Override
	public Long cantidadDocentesActivos() {
		return usuarioRepository.countByRoles_NombreAndEnabled("ROLE_DOCENTE", true);
	}

	@Override
	public List<Usuario> listarTodosUsuarios() {
		return usuarioRepository.findAll();
	}

	@Override
	public boolean cambiarPassword(Long id, String passwordActual, String passwordNueva) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		if (!passwordEncoder.matches(passwordActual, usuario.getPassword())) {
			return false;
		}
		usuario.setPassword(passwordEncoder.encode(passwordNueva));
		usuarioRepository.save(usuario);
		logService.registrarLog("usuario", "Contraseña actualizada: " + usuario.getUsername());
		return true;
	}

	@Override
	public void cambiarPasswordAdmin(Long id, String passwordNueva) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		usuario.setPassword(passwordEncoder.encode(passwordNueva));
		usuarioRepository.save(usuario);
		logService.registrarLog("usuario", "Contraseña cambiada por admin: " + usuario.getUsername());
	}

	@Override
	public void toggleActivo(Long id) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		boolean actual = Boolean.TRUE.equals(usuario.getEnabled());
		usuario.setEnabled(!actual);
		usuarioRepository.save(usuario);
		logService.registrarLog("usuario",
				"Estado cambiado a " + (!actual ? "activo" : "inactivo") + ": " + usuario.getUsername());
	}

	@Override
	public boolean cambiarUsername(Long id, String nuevoUsername) {
		if (!validarUsername(nuevoUsername)) return false;
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		usuario.setUsername(nuevoUsername);
		usuarioRepository.save(usuario);
		logService.registrarLog("usuario", "Username cambiado a: " + nuevoUsername);
		return true;
	}

	@Override
	public boolean restablecerPassword(Long id) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		String nuevaPassword = null;
		Estudiante est = estudianteRepository.findByUsuarioUsername(usuario.getUsername()).orElse(null);
		if (est != null) nuevaPassword = est.getDocumentoIdentidad();
		if (nuevaPassword == null) {
			Docente doc = docenteRepository.findByUsuarioUsername(usuario.getUsername()).orElse(null);
			if (doc != null) nuevaPassword = doc.getDocumentoIdentidad();
		}
		if (nuevaPassword == null) return false;
		usuario.setPassword(passwordEncoder.encode(nuevaPassword));
		usuarioRepository.save(usuario);
		logService.registrarLog("usuario", "Contraseña restablecida: " + usuario.getUsername());
		return true;
	}

}
