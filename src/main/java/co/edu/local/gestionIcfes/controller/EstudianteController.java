package co.edu.local.gestionIcfes.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import co.edu.local.gestionIcfes.model.Asistencia;
import co.edu.local.gestionIcfes.model.Estudiante;
import co.edu.local.gestionIcfes.model.Institucion;
import co.edu.local.gestionIcfes.model.ResultadoSimulacro;
import co.edu.local.gestionIcfes.repository.ResultadoSimulacroRepositorio;
import co.edu.local.gestionIcfes.repository.UsuarioRepositorio;
import co.edu.local.gestionIcfes.services.AsistenciaService;
import co.edu.local.gestionIcfes.services.EstudianteService;

@Controller
@RequestMapping("estudiante")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ResultadoSimulacroRepositorio resultadoSimulacroRepositorio;

    @Autowired
    private AsistenciaService asistenciaService;

    @GetMapping("/pEstudiante")
    public String mostrarPanelEstudiante(Model model, Authentication authentication) {
        Estudiante estudiante = estudianteService.buscarPorUsername(authentication.getName());

        String salon = estudiante.getUsuario() != null ? estudiante.getUsuario().getSalon() : "Sin asignar";
        String institucionNombre = "Sin asignar";
        String adminNombre = "Sin asignar";
        int progresoCurso = 0;
        long diasRestantes = 0;

        Institucion institucion = estudiante.getInstitucion();
        if (institucion != null) {
            institucionNombre = institucion.getNombre();
            adminNombre = usuarioRepositorio
                    .findFirstByInstitucionIdAndRoles_Nombre(institucion.getId(), "ROLE_ADMIN")
                    .map(u -> u.getUsername())
                    .orElse("Sin asignar");

            LocalDate inicio = institucion.getFechaInicio();
            LocalDate fin = institucion.getFechaFinal();
            if (inicio != null && fin != null) {
                LocalDate hoy = LocalDate.now();
                long totalDias = ChronoUnit.DAYS.between(inicio, fin);
                long transcurridos = ChronoUnit.DAYS.between(inicio, hoy);
                diasRestantes = Math.max(0, ChronoUnit.DAYS.between(hoy, fin));
                if (totalDias > 0) {
                    progresoCurso = (int) Math.min(100, Math.max(0, transcurridos * 100 / totalDias));
                }
            }
        }

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("salon", salon);
        model.addAttribute("institucionNombre", institucionNombre);
        model.addAttribute("adminNombre", adminNombre);
        model.addAttribute("progresoCurso", progresoCurso);
        model.addAttribute("diasRestantes", diasRestantes);
        return "estudiante/pEstudiante";
    }

    @GetMapping("/simulacrosEstudiante")
    public String mostrarSimulacros(Model model, Authentication authentication) {
        Estudiante estudiante = estudianteService.buscarPorUsername(authentication.getName());
        List<ResultadoSimulacro> resultados = estudiante.getResultados();
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("resultados", resultados);
        return "estudiante/simulacrosEstudiante";
    }

    @GetMapping("/asistenciasEstudiante")
    public String mostrarAsistencias(Model model, Authentication authentication) {
        Estudiante estudiante = estudianteService.buscarPorUsername(authentication.getName());
        List<Asistencia> asistencias = asistenciaService.listarPorEstudiante(estudiante.getDocumentoIdentidad());

        long totalPresente = asistencias.stream()
                .filter(a -> a.getEstado().name().equals("PRESENTE")).count();
        long totalAusente = asistencias.stream()
                .filter(a -> a.getEstado().name().equals("AUSENTE")).count();
        long totalTarde = asistencias.stream()
                .filter(a -> a.getEstado().name().equals("TARDE")).count();

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("asistencias", asistencias);
        model.addAttribute("totalPresente", totalPresente);
        model.addAttribute("totalAusente", totalAusente);
        model.addAttribute("totalTarde", totalTarde);
        return "estudiante/asistenciasEstudiante";
    }

    @GetMapping("/descargarResultado/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> descargarResultado(@PathVariable Long id, Authentication authentication) {
        ResultadoSimulacro resultado = resultadoSimulacroRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Resultado no encontrado"));

        if (!resultado.getEstudiante().getUsuario().getUsername().equals(authentication.getName())) {
            return ResponseEntity.status(403).build();
        }

        byte[] archivo = resultado.getDatos();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resultado.getNombreArchivo() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(archivo.length)
                .body(archivo);
    }
}
