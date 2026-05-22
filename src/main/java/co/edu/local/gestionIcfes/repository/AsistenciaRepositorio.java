package co.edu.local.gestionIcfes.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.local.gestionIcfes.model.Asistencia;

/**
 * Repositorio JPA para la entidad {@link co.edu.local.gestionIcfes.model.Asistencia}.
 */
public interface AsistenciaRepositorio extends JpaRepository<Asistencia, Long> {

    /**
     * Lista las asistencias de un estudiante ordenadas cronológicamente.
     *
     * @param documentoIdentidad documento del estudiante.
     * @return lista de asistencias del estudiante, de la más antigua a la más reciente.
     */
    List<Asistencia> findByEstudianteDocumentoIdentidadOrderByFechaAsc(String documentoIdentidad);

    /**
     * Busca la asistencia de un estudiante para una fecha específica.
     * Se usa para decidir si crear un nuevo registro o actualizar el existente.
     *
     * @param documentoIdentidad documento del estudiante.
     * @param fecha              fecha de la clase.
     * @return {@code Optional} con la asistencia si ya existe para esa fecha.
     */
    Optional<Asistencia> findByEstudianteDocumentoIdentidadAndFecha(String documentoIdentidad, LocalDate fecha);

    /**
     * Lista las asistencias de un día para todos los estudiantes de un salón
     * específico dentro de una institución. Usado en el panel del docente.
     *
     * @param fecha         fecha de la clase.
     * @param institucionId identificador de la institución.
     * @param salon         salón ("1" o "2").
     * @return lista de asistencias del salón en esa fecha.
     */
    List<Asistencia> findByFechaAndEstudiante_Institucion_IdAndEstudiante_Usuario_Salon(LocalDate fecha, Long institucionId, String salon);

    /**
     * Elimina todas las asistencias de los estudiantes de una institución.
     * Se invoca antes de eliminar la institución para respetar la integridad referencial.
     *
     * @param institucionId identificador de la institución.
     */
    void deleteByEstudianteInstitucionId(Long institucionId);
}
