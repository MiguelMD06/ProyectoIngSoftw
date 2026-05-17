package co.edu.local.gestionIcfes.services;

import java.time.LocalDate;
import java.util.List;

import co.edu.local.gestionIcfes.enums.EstadoAsistencia;
import co.edu.local.gestionIcfes.model.Asistencia;

public interface AsistenciaService {

    void registrarOActualizar(LocalDate fecha, List<String> documentos, List<EstadoAsistencia> estados);
    List<Asistencia> listarPorEstudiante(String documentoIdentidad);
}
