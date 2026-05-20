package co.edu.local.gestionIcfes.servicesImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.local.gestionIcfes.enums.EstadoAsistencia;
import co.edu.local.gestionIcfes.model.Asistencia;
import co.edu.local.gestionIcfes.model.Estudiante;
import co.edu.local.gestionIcfes.repository.AsistenciaRepositorio;
import co.edu.local.gestionIcfes.repository.EstudianteRepositorio;
import co.edu.local.gestionIcfes.services.AsistenciaService;
import co.edu.local.gestionIcfes.services.LogService;

@Service
@Transactional
public class AsistenciaServiceImpl implements AsistenciaService {

    @Autowired
    private AsistenciaRepositorio asistenciaRepositorio;

    @Autowired
    private EstudianteRepositorio estudianteRepositorio;

    @Autowired
    private LogService logService;

    @Override
    public void registrarOActualizar(LocalDate fecha, List<String> documentos, List<EstadoAsistencia> estados) {
        for (int i = 0; i < documentos.size(); i++) {
            String doc = documentos.get(i);
            EstadoAsistencia estado = estados.get(i);

            Optional<Asistencia> existente = asistenciaRepositorio
                    .findByEstudianteDocumentoIdentidadAndFecha(doc, fecha);

            if (existente.isPresent()) {
                existente.get().setEstado(estado);
                asistenciaRepositorio.save(existente.get());
            } else {
                Estudiante estudiante = estudianteRepositorio.findById(doc)
                        .orElseThrow(() -> new RuntimeException("Estudiante no encontrado: " + doc));
                Asistencia nueva = new Asistencia();
                nueva.setFecha(fecha);
                nueva.setEstado(estado);
                nueva.setEstudiante(estudiante);
                asistenciaRepositorio.save(nueva);
            }
        }
        logService.registrarLog("asistencia",
                "Asistencias registradas para " + documentos.size() + " estudiante(s) — fecha: " + fecha);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Asistencia> listarPorEstudiante(String documentoIdentidad) {
        return asistenciaRepositorio.findByEstudianteDocumentoIdentidadOrderByFechaAsc(documentoIdentidad);
    }
}
