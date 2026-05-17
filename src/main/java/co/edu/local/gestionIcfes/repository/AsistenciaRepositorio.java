package co.edu.local.gestionIcfes.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.local.gestionIcfes.model.Asistencia;

public interface AsistenciaRepositorio extends JpaRepository<Asistencia, Long> {

    List<Asistencia> findByEstudianteDocumentoIdentidadOrderByFechaAsc(String documentoIdentidad);
    Optional<Asistencia> findByEstudianteDocumentoIdentidadAndFecha(String documentoIdentidad, LocalDate fecha);
    List<Asistencia> findByFechaAndEstudiante_Institucion_IdAndEstudiante_Usuario_Salon(LocalDate fecha, Long institucionId, String salon);
}
