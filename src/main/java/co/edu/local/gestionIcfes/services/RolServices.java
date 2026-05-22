package co.edu.local.gestionIcfes.services;

import java.util.List;

import co.edu.local.gestionIcfes.model.Rol;

/**
 * Contrato del servicio de roles de seguridad.
 * <p>
 * Los roles son sembrados en el arranque por
 * {@link co.edu.local.gestionIcfes.config.DataInitializer} y no se crean
 * en tiempo de ejecución; este servicio solo los consulta.
 * </p>
 */
public interface RolServices {

	/** @return lista de todos los roles del sistema. */
	public List<Rol> listarRoles();

	/**
	 * Busca un rol por su nombre.
	 *
	 * @param nombre nombre del rol (p.ej., {@code "ROLE_ADMIN"}).
	 * @return el rol encontrado, o {@code null} si no existe.
	 */
	public Rol encontrarRol(String nombre);

	/**
	 * Busca un rol por su ID numérico.
	 *
	 * @param id identificador del rol en la base de datos.
	 * @return el rol encontrado.
	 */
	public Rol encontrarRol(Long id);
}
