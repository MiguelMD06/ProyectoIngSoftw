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

/**
 * Contrato del servicio de usuarios. Extiende {@link UserDetailsService} para integrarse
 * con Spring Security como proveedor de autenticación.
 * <p>
 * Centraliza la creación de administradores, estudiantes y docentes, la gestión de
 * contraseñas, el cambio de nombre de usuario y las estadísticas de conteo que aparecen
 * en el panel administrativo.
 * </p>
 */
public interface UsuarioServices extends UserDetailsService{

	/**
	 * Crea un nuevo usuario con rol {@code ROLE_ADMIN}.
	 *
	 * @param usuarioDTO datos del nuevo administrador.
	 * @return el usuario persistido.
	 */
	public Usuario crearAdmin(UsuarioDTO usuarioDTO);

	/**
	 * Crea un estudiante con su entidad {@link co.edu.local.gestionIcfes.model.Persona}
	 * y un {@link co.edu.local.gestionIcfes.model.Usuario} generado automáticamente.
	 * El username se construye como {@code primerNombre + primerApellido[0..2] + (maxId+1)}.
	 * La contraseña inicial es el {@code documentoIdentidad}.
	 *
	 * @param personaDTO datos del estudiante.
	 * @return el estudiante persistido, o {@code null} si el username ya existe.
	 */
	public Estudiante crearEstudiante(PersonaDTO personaDTO);

	/**
	 * Crea un docente con la misma lógica de generación de usuario que
	 * {@link #crearEstudiante(PersonaDTO)}.
	 *
	 * @param personaDTO datos del docente.
	 * @return el docente persistido, o {@code null} si el username ya existe.
	 */
	public Docente crearDocente(PersonaDTO personaDTO);

	/**
	 * Elimina un usuario por su ID. No elimina la entidad {@code Persona} vinculada;
	 * esa responsabilidad recae en el servicio de estudiante o docente.
	 *
	 * @param id identificador del usuario.
	 */
	public void eliminarUsuario(Long id);

	/**
	 * Verifica que el nombre de usuario no esté registrado en el sistema.
	 *
	 * @param username nombre de usuario a comprobar.
	 * @return {@code true} si el username está disponible.
	 */
	public boolean validarUsername(String username);

	/**
	 * Obtiene el mayor ID entre una lista de usuarios. Se usa para generar usernames únicos.
	 *
	 * @param usuarios lista completa de usuarios.
	 * @return el ID más alto encontrado, o {@code -1} si la lista está vacía.
	 */
	public Long obtenerIdMasAlto(List<Usuario> usuarios);

	/** @return lista de todos los estudiantes registrados. */
	public List<Estudiante> listarEstudiantes();

	/** @return lista de estudiantes con sus resultados de simulacro precargados (evita N+1). */
	public List<Estudiante> listarEstudiantesConResultados();

	/**
	 * Obtiene un resultado de simulacro por ID dentro de una transacción de solo lectura.
	 *
	 * @param id identificador del resultado.
	 * @return el resultado encontrado.
	 * @throws RuntimeException si no existe el resultado.
	 */
	public ResultadoSimulacro obtenerResultadoPorId(Long id);

	/**
	 * Actualiza el salón e institución asignados a un usuario existente.
	 *
	 * @param salon       nuevo salón ("1" o "2").
	 * @param institucion nueva institución.
	 * @param id          identificador del usuario.
	 * @return el usuario actualizado.
	 */
	public Usuario editarSalonInstitucion(String salon, Institucion institucion, Long id);

	/** @return total de usuarios con rol {@code ROLE_ESTUDIANTE}. */
	public Long cantidadEstudiantes();

	/** @return total de usuarios con rol {@code ROLE_DOCENTE}. */
	public Long cantidadDocentes();

	/** @return total de estudiantes con {@code enabled = true}. */
	public Long cantidadEstudiantesActivos();

	/** @return total de docentes con {@code enabled = true}. */
	public Long cantidadDocentesActivos();

	/** @return lista completa de usuarios del sistema. */
	public List<Usuario> listarTodosUsuarios();

	/**
	 * Cambia la contraseña de un usuario previa verificación de la contraseña actual.
	 *
	 * @param id              identificador del usuario.
	 * @param passwordActual  contraseña actual en texto plano.
	 * @param passwordNueva   nueva contraseña en texto plano.
	 * @return {@code true} si la contraseña fue actualizada correctamente.
	 */
	public boolean cambiarPassword(Long id, String passwordActual, String passwordNueva);

	/**
	 * Cambia la contraseña de un usuario sin requerir la contraseña actual.
	 * Solo accesible desde el panel de administración.
	 *
	 * @param id            identificador del usuario.
	 * @param passwordNueva nueva contraseña en texto plano.
	 */
	public void cambiarPasswordAdmin(Long id, String passwordNueva);

	/**
	 * Alterna el estado {@code enabled} del usuario (activo ↔ inactivo).
	 *
	 * @param id identificador del usuario.
	 */
	public void toggleActivo(Long id);

	/**
	 * Restablece la contraseña de un estudiante o docente a su {@code documentoIdentidad}.
	 *
	 * @param id identificador del usuario.
	 * @return {@code true} si se encontró la entidad persona vinculada y se restableció.
	 */
	public boolean restablecerPassword(Long id);

	/**
	 * Cambia el nombre de usuario si no está en uso. Tras el cambio, el usuario
	 * debe volver a iniciar sesión.
	 *
	 * @param id            identificador del usuario.
	 * @param nuevoUsername nuevo nombre de usuario deseado.
	 * @return {@code true} si el cambio fue exitoso.
	 */
	public boolean cambiarUsername(Long id, String nuevoUsername);
}