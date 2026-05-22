package co.edu.local.gestionIcfes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.local.gestionIcfes.model.Usuario;

/**
 * Repositorio JPA para la entidad {@link co.edu.local.gestionIcfes.model.Usuario}.
 * <p>
 * Usado por {@link co.edu.local.gestionIcfes.servicesImpl.UsuarioServicesImpl}
 * como proveedor de autenticación de Spring Security.
 * </p>
 */
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

	/**
	 * Busca un usuario por su nombre de usuario. Usado por Spring Security en el
	 * proceso de autenticación.
	 *
	 * @param nombre nombre de usuario único.
	 * @return el usuario encontrado, o {@code null} si no existe.
	 */
	public Usuario findByUsername(String nombre);

	/**
	 * Cuenta el total de usuarios que tienen un rol específico.
	 *
	 * @param nombre nombre del rol (p.ej., {@code "ROLE_ESTUDIANTE"}).
	 * @return cantidad total de usuarios con ese rol.
	 */
	public Long countByRoles_Nombre(String nombre);

	/**
	 * Cuenta los usuarios activos o inactivos que tienen un rol específico.
	 *
	 * @param rolNombre nombre del rol.
	 * @param enabled   {@code true} para contar activos, {@code false} para inactivos.
	 * @return cantidad de usuarios con ese rol y estado.
	 */
	public Long countByRoles_NombreAndEnabled(String rolNombre, Boolean enabled);

	/**
	 * Obtiene el primer usuario administrador vinculado a una institución.
	 * Se usa en los paneles de estudiante y docente para mostrar el nombre del admin.
	 *
	 * @param institucionId identificador de la institución.
	 * @param rolNombre     nombre del rol buscado.
	 * @return {@code Optional} con el primer usuario que cumpla las condiciones.
	 */
	public Optional<Usuario> findFirstByInstitucionIdAndRoles_Nombre(Long institucionId, String rolNombre);

	/**
	 * Lista todos los usuarios vinculados a una institución.
	 *
	 * @param institucionId identificador de la institución.
	 * @return lista de usuarios de la institución.
	 */
	public List<Usuario> findByInstitucionId(Long institucionId);
}
