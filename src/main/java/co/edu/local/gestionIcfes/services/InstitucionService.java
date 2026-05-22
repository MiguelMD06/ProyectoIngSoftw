package co.edu.local.gestionIcfes.services;

import java.util.List;

import co.edu.local.gestionIcfes.model.Institucion;

/**
 * Contrato del servicio de gestión de instituciones educativas.
 * <p>
 * La eliminación de una institución es una operación en cascada que borra
 * en orden: asistencias, resultados de simulacro, estudiantes, docentes,
 * simulacros, materiales de estudio, usuarios admin vinculados y finalmente
 * la institución (ver {@link co.edu.local.gestionIcfes.servicesImpl.InstitucionServicesImpl#eliminarInstitucion}).
 * </p>
 */
public interface InstitucionService {

	/** @return lista de todas las instituciones registradas. */
	public List<Institucion> listarInstituciones();

	/**
	 * Persiste una nueva institución. Registra el evento en el log de auditoría.
	 *
	 * @param institucion entidad a guardar.
	 * @return la institución persistida.
	 */
	Institucion guardarInstitucion(Institucion institucion);

	/**
	 * Busca una institución por su ID.
	 *
	 * @param id identificador de la institución.
	 * @return la institución encontrada.
	 */
	public Institucion buscarPorId(Long id);

	/**
	 * Actualiza los datos de una institución existente.
	 *
	 * @param institucion entidad con los datos actualizados.
	 * @return la institución actualizada.
	 */
	public Institucion actualizarInstitucion(Institucion institucion);

	/**
	 * Elimina una institución y todos sus datos dependientes en una transacción.
	 *
	 * @param id identificador de la institución.
	 */
	public void eliminarInstitucion(Long id);

	/** @return número de instituciones con estado {@code ACTIVO}. */
	public Long cantidadInstitucionesActivas();
}
