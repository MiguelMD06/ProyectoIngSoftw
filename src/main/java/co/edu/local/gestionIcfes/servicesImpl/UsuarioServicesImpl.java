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

import co.edu.local.gestionIcfes.dto.PersonaDTO;
import co.edu.local.gestionIcfes.dto.UsuarioDTO;
import co.edu.local.gestionIcfes.model.Docente;
import co.edu.local.gestionIcfes.model.Estudiante;
import co.edu.local.gestionIcfes.model.Rol;
import co.edu.local.gestionIcfes.model.Usuario;
import co.edu.local.gestionIcfes.repository.DocenteRepositorio;
import co.edu.local.gestionIcfes.repository.EstudianteRepositorio;
import co.edu.local.gestionIcfes.repository.UsuarioRepositorio;
import co.edu.local.gestionIcfes.services.RolServices;
import co.edu.local.gestionIcfes.services.UsuarioServices;
import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioServicesImpl implements UsuarioServices{
	
	@Autowired
	private UsuarioRepositorio usuarioRepository;
	
	@Autowired
	private EstudianteRepositorio estudianteRepository;
	
	@Autowired
	private DocenteRepositorio docenteRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RolServices rolServicio;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public Usuario crearAdmin(UsuarioDTO usuarioDTO) {
		Usuario usuario = new Usuario(usuarioDTO.getUsername(), passwordEncoder.encode(usuarioDTO.getPassword()), true, Arrays.asList(rolServicio.encontrarRol("ROLE_ADMIN")));
		return usuarioRepository.save(usuario);
	}

	@Override
	public Estudiante crearEstudiante(PersonaDTO personaDTO) {
		String nombreUsuario = personaDTO.getPrimerNombre().substring(0, 2) + personaDTO.getPrimerApellido() + personaDTO.getSegundoApellido().substring(0, 2) + (obtenerIdMasAlto(usuarioRepository.findAll())+1);
		Usuario usuario = new Usuario(nombreUsuario, passwordEncoder.encode(personaDTO.getDocumentoIdentidad()),true,personaDTO.getInstitucion(),personaDTO.getSalon(),Arrays.asList(personaDTO.getRol()));
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
			estudiante.setInstitucion(personaDTO.getInstitucion());
			return estudianteRepository.save(estudiante);
		}
		return null;
	}
		
	
	@Override
	public Docente crearDocente(PersonaDTO personaDTO) {
		String nombreUsuario = personaDTO.getPrimerNombre().substring(0, 2) + personaDTO.getPrimerApellido() + personaDTO.getSegundoApellido().substring(0, 2) + (obtenerIdMasAlto(usuarioRepository.findAll())+1);
		Usuario usuario = new Usuario(nombreUsuario, passwordEncoder.encode(personaDTO.getDocumentoIdentidad()),true,personaDTO.getInstitucion(),personaDTO.getSalon(),Arrays.asList(personaDTO.getRol()));
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
			docente.setUsuario(usuario);
			docente.setInstitucion(personaDTO.getInstitucion());
			return docenteRepository.save(docente);
		}
		return null;
	}
	
	@Override
	public Long obtenerIdMasAlto(List<Usuario> usuarios) {
	    return usuarios.stream()
	            .mapToLong(Usuario::getId)
	            .max()
	            .orElse(-1); 
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByUsername(username);
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuario o Password Inválidos");
		}
		session.setAttribute("idusuario", usuario.getId());
		return new User(usuario.getUsername(),usuario.getPassword(), mapearAutoridadRoles(usuario.getRoles()));
	}
	@Override
	public boolean validarUsername(String username) {
		Usuario usuario = usuarioRepository.findByUsername(username);
		
		if (usuario == null)
			return true;
		
		return false;
	}
	
	
	private Collection<? extends GrantedAuthority> mapearAutoridadRoles(Collection<Rol> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
	}
}
