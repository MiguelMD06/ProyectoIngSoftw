package co.edu.local.gestionIcfes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.local.gestionIcfes.model.Simulacro;

/**
 * Repositorio JPA para la entidad {@link co.edu.local.gestionIcfes.model.Simulacro}.
 */
public interface SimulacroRepositorio extends JpaRepository<Simulacro, Long> {

	/**
	 * Busca un simulacro por su título exacto.
	 *
	 * @param titulo título del simulacro.
	 * @return {@code Optional} con el simulacro si existe.
	 */
	Optional<Simulacro> findByTitulo(String titulo);

	/**
	 * Lista todos los simulacros asociados a una institución.
	 *
	 * @param institucionId identificador de la institución.
	 * @return lista de simulacros de la institución.
	 */
	List<Simulacro> findByInstitucionId(Long institucionId);
}
