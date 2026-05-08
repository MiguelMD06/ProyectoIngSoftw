package co.edu.local.gestionIcfes.servicesImpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.local.gestionIcfes.dto.UsuarioDTO;
import co.edu.local.gestionIcfes.model.Rol;
import co.edu.local.gestionIcfes.model.Usuario;
import co.edu.local.gestionIcfes.repository.RolRepositorio;
import co.edu.local.gestionIcfes.repository.UsuarioRepositorio;
import co.edu.local.gestionIcfes.services.UsuarioServices;
import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioServicesImpl implements UsuarioServices{
	
	@Autowired
	private UsuarioRepositorio usuarioRepository;
	
	@Autowired
	private RolRepositorio rolRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private HttpSession session;

	@Override
	public Usuario crearUsuario(UsuarioDTO usuarioDTO) {
		if (usuarioRepository.findAll().size() == 0) {
			Usuario usuario = new Usuario(usuarioDTO.getUsername(),passwordEncoder.encode(usuarioDTO.getPassword()),true,Arrays.asList(rolRepository.findByNombre("ROLE_ADMIN")));
			return usuarioRepository.save(usuario);
		}
		Usuario usuario = new Usuario(usuarioDTO.getUsername(), passwordEncoder.encode(usuarioDTO.getPassword()), true, usuarioDTO.getInstitucion(), Arrays.asList(usuarioDTO.getRoles()));
		return usuarioRepository.save(usuario);
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
	public boolean validarUsername(UsuarioDTO usuarioDTO) {
		Usuario usuario = usuarioRepository.findByUsername(usuarioDTO.getUsername());
		
		if (usuario == null)
			return true;
		
		return false;
	}
	
	
	private Collection<? extends GrantedAuthority> mapearAutoridadRoles(Collection<Rol> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
	}
}
