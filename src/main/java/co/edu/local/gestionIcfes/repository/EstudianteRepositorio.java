package co.edu.local.gestionIcfes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.local.gestionIcfes.model.Estudiante;

/**
 * Repositorio JPA para la entidad {@link co.edu.local.gestionIcfes.model.Estudiante}.
 * <p>
 * La clave primaria es {@code String} (número de documento de identidad).
 * Incluye consultas derivadas y una consulta JPQL personalizada para evitar el
 * problema N+1 al cargar los resultados de simulacro.
 * </p>
 */
public interface EstudianteRepositorio extends JpaRepository<Estudiante, String> {

	/**
	 * Devuelve todos los estudiantes con sus resultados de simulacro cargados
	 * en una sola consulta mediante {@code LEFT JOIN FETCH}.
	 *
	 * @return lista de estudiantes con la colección {@code resultados} inicializada.
	 */
	@Query("""
		    SELECT DISTINCT e
		    FROM Estudiante e
		    LEFT JOIN FETCH e.resultados
		""")
	List<Estudiante> listarEstudiantesConResultados();

	/**
	 * Busca un estudiante por el nombre de usuario de su {@link co.edu.local.gestionIcfes.model.Usuario} asociado.
	 *
	 * @param username nombre de usuario.
	 * @return {@code Optional} con el estudiante, vacío si no existe.
	 */
	Optional<Estudiante> findByUsuarioUsername(String username);

	/**
	 * Lista todos los estudiantes matriculados en una institución.
	 *
	 * @param institucionId identificador de la institución.
	 * @return lista de estudiantes de la institución.
	 */
	List<Estudiante> findByInstitucionId(Long institucionId);

	/**
	 * Lista los estudiantes de una institución filtrados por salón.
	 *
	 * @param institucionId identificador de la institución.
	 * @param salon         salón asignado ("1" o "2").
	 * @return lista de estudiantes que coinciden con institución y salón.
	 */
	List<Estudiante> findByInstitucionIdAndUsuarioSalon(Long institucionId, String salon);
}
