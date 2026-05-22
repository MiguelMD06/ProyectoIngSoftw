package co.edu.local.gestionIcfes.services;

import co.edu.local.gestionIcfes.dto.PersonaDTO;
import co.edu.local.gestionIcfes.model.Estudiante;

/**
 * Contrato del servicio de gestión de estudiantes.
 * <p>
 * Cubre el ciclo CRUD completo del estudiante, incluyendo la eliminación en cascada
 * de sus asistencias y resultados de simulacro, y la conversión entre entidad y DTO
 * para los formularios de edición.
 * </p>
 */
public interface EstudianteService {

	/**
	 * Elimina un estudiante y todos sus datos dependientes (asistencias, resultados
	 * de simulacro y el usuario de acceso asociado).
	 *
	 * @param documentoIdentidad clave primaria del estudiante.
	 */
	public void eliminarEstudiante(String documentoIdentidad);

	/**
	 * Busca un estudiante por su documento de identidad.
	 *
	 * @param documentoIdentidad clave primaria del estudiante.
	 * @return el estudiante encontrado.
	 */
	public Estudiante buscarEstudiante(String documentoIdentidad);

	/**
	 * Busca el estudiante vinculado al nombre de usuario de sesión actual.
	 *
	 * @param username nombre de usuario del login.
	 * @return el estudiante correspondiente.
	 * @throws RuntimeException si no existe un estudiante con ese usuario.
	 */
	public Estudiante buscarPorUsername(String username);

	/**
	 * Convierte una entidad {@link Estudiante} en un {@link PersonaDTO} listo para
	 * poblar el formulario de edición.
	 *
	 * @param estudiante entidad a convertir.
	 * @return DTO con los datos del estudiante.
	 */
	public PersonaDTO convertirEstudiantePersona(Estudiante estudiante);

	/**
	 * Actualiza los datos de un estudiante a partir de los valores del formulario.
	 * También actualiza el salón y la institución de su usuario de acceso.
	 *
	 * @param personaDTO datos actualizados desde el formulario.
	 * @return el estudiante actualizado y persistido.
	 */
	public Estudiante actualizarEstudiante(PersonaDTO personaDTO);
}
