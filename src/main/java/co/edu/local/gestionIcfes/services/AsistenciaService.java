package co.edu.local.gestionIcfes.services;

import java.time.LocalDate;
import java.util.List;

import co.edu.local.gestionIcfes.enums.EstadoAsistencia;
import co.edu.local.gestionIcfes.model.Asistencia;

/**
 * Contrato del servicio de registro de asistencias.
 */
public interface AsistenciaService {

    /**
     * Registra o actualiza las asistencias de una lista de estudiantes para una fecha dada.
     * Si ya existe un registro para el par (estudiante, fecha), actualiza el estado;
     * de lo contrario crea uno nuevo.
     *
     * @param fecha      fecha de la clase.
     * @param documentos lista de documentos de identidad de los estudiantes.
     * @param estados    lista paralela de estados ({@link EstadoAsistencia}) correspondientes.
     */
    void registrarOActualizar(LocalDate fecha, List<String> documentos, List<EstadoAsistencia> estados);

    /**
     * Lista todas las asistencias de un estudiante en orden cronológico ascendente.
     *
     * @param documentoIdentidad documento del estudiante.
     * @return lista de asistencias ordenada por fecha.
     */
    List<Asistencia> listarPorEstudiante(String documentoIdentidad);
}
