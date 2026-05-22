package co.edu.local.gestionIcfes.services;

import java.util.List;

import co.edu.local.gestionIcfes.dto.PersonaDTO;
import co.edu.local.gestionIcfes.model.Docente;

/**
 * Contrato del servicio de gestión de docentes.
 */
public interface DocenteService {

	/** @return lista de todos los docentes registrados. */
	public List<Docente> listarDocentes();

	/**
	 * Obtiene un docente por su documento de identidad.
	 *
	 * @param documentoIdentidad clave primaria del docente.
	 * @return el docente encontrado.
	 * @throws RuntimeException si no existe.
	 */
	public Docente obtenerDocentePorId(String documentoIdentidad);

	/**
	 * Persiste un nuevo docente. Registra el evento en el log de auditoría.
	 *
	 * @param docente entidad a guardar.
	 * @return el docente persistido.
	 */
	public Docente guardarDocente(Docente docente);

	/**
	 * Actualiza los datos de un docente a partir de los valores del formulario.
	 *
	 * @param personaDTO datos actualizados.
	 * @return el docente actualizado.
	 */
	public Docente actualizarDocente(PersonaDTO personaDTO);

	/**
	 * Elimina un docente y su usuario de acceso asociado.
	 *
	 * @param documentoIdentidad clave primaria del docente.
	 */
	public void eliminarDocente(String documentoIdentidad);

	/**
	 * Convierte un {@link Docente} en {@link PersonaDTO} para poblar el formulario de edición.
	 *
	 * @param docente entidad a convertir.
	 * @return DTO con los datos del docente.
	 */
	public PersonaDTO convertirDocentePersona(Docente docente);

	/**
	 * Busca el docente vinculado al nombre de usuario de la sesión actual.
	 *
	 * @param username nombre de usuario del login.
	 * @return el docente correspondiente.
	 * @throws RuntimeException si no existe un docente con ese usuario.
	 */
	public Docente buscarPorUsername(String username);
}
