package co.edu.local.gestionIcfes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.local.gestionIcfes.model.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

	public Usuario findByUsername(String nombre);
	public Long countByRoles_Nombre(String nombre);
	public Long countByRoles_NombreAndEnabled(String rolNombre, Boolean enabled);
	public Optional<Usuario> findFirstByInstitucionIdAndRoles_Nombre(Long institucionId, String rolNombre);
}
