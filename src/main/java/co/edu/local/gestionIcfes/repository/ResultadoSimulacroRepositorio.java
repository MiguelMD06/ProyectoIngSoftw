package co.edu.local.gestionIcfes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.local.gestionIcfes.model.ResultadoSimulacro;

/**
 * Repositorio JPA para la entidad {@link co.edu.local.gestionIcfes.model.ResultadoSimulacro}.
 * <p>
 * Los métodos de eliminación masiva se usan en la eliminación en cascada de instituciones
 * y simulacros para garantizar la integridad referencial antes de borrar las entidades padre.
 * </p>
 */
public interface ResultadoSimulacroRepositorio extends JpaRepository<ResultadoSimulacro, Long> {

    /**
     * Lista todos los resultados de simulacro de un estudiante.
     *
     * @param documentoIdentidad documento del estudiante.
     * @return lista de resultados del estudiante.
     */
    List<ResultadoSimulacro> findByEstudianteDocumentoIdentidad(String documentoIdentidad);

    /**
     * Busca el resultado de un estudiante en un simulacro específico.
     *
     * @param documentoIdentidad documento del estudiante.
     * @param simulacroId        identificador del simulacro.
     * @return {@code Optional} con el resultado si existe.
     */
    Optional<ResultadoSimulacro> findByEstudianteDocumentoIdentidadAndSimulacroId(
            String documentoIdentidad, Long simulacroId);

    /**
     * Elimina todos los resultados de los estudiantes de una institución.
     * Se llama antes de eliminar la institución.
     *
     * @param institucionId identificador de la institución.
     */
    void deleteByEstudianteInstitucionId(Long institucionId);

    /**
     * Elimina todos los resultados de los simulacros de una institución.
     * Se llama antes de eliminar la institución para limpiar resultados
     * vinculados por el lado del simulacro.
     *
     * @param institucionId identificador de la institución.
     */
    void deleteBySimulacroInstitucionId(Long institucionId);

    /**
     * Elimina todos los resultados asociados a un simulacro específico.
     * Se invoca antes de eliminar el simulacro.
     *
     * @param simulacroId identificador del simulacro.
     */
    void deleteBySimulacroId(Long simulacroId);
}
