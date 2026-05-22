package co.edu.local.gestionIcfes.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.edu.local.gestionIcfes.model.Asistencia;
import co.edu.local.gestionIcfes.model.Estudiante;
import co.edu.local.gestionIcfes.model.Institucion;
import co.edu.local.gestionIcfes.model.MaterialEstudio;
import co.edu.local.gestionIcfes.model.ResultadoSimulacro;
import co.edu.local.gestionIcfes.repository.ResultadoSimulacroRepositorio;
import co.edu.local.gestionIcfes.repository.UsuarioRepositorio;
import co.edu.local.gestionIcfes.services.AsistenciaService;
import co.edu.local.gestionIcfes.services.EstudianteService;
import co.edu.local.gestionIcfes.services.MaterialEstudioServices;
import co.edu.local.gestionIcfes.services.UsuarioServices;

/**
 * Controlador del panel del estudiante. Requiere {@code ROLE_ESTUDIANTE}.
 * <p>
 * Gestiona el dashboard con promedios de puntajes por área y progreso del curso,
 * la vista de simulacros con resultados descargables, el historial de asistencias
 * con conteos por estado, los materiales de estudio de su institución y la
 * pantalla de configuración de cuenta.
 * </p>
 * <p>
 * Los métodos de descarga de archivos están anotados con {@code @Transactional(readOnly = true)}
 * para mantener la sesión de Hibernate abierta mientras se transmite el campo {@code BYTEA}.
 * La descarga de resultados valida que el resultado pertenezca al estudiante autenticado
 * antes de enviarlo, retornando HTTP 403 en caso contrario.
 * </p>
 */
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

    @Autowired
    private MaterialEstudioServices materialEstudioServices;

    @Autowired
    private UsuarioServices usuarioServices;

    @GetMapping("/pEstudiante")
    public String mostrarPanelEstudiante(Model model, Authentication authentication,
            @RequestParam(required = false) Long simulacroSeleccionadoId) {
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

        // Resultados de simulacros
        List<ResultadoSimulacro> resultados = resultadoSimulacroRepositorio
                .findByEstudianteDocumentoIdentidad(estudiante.getDocumentoIdentidad());

        ResultadoSimulacro resultadoSeleccionado = null;
        if (simulacroSeleccionadoId != null) {
            resultadoSeleccionado = resultadoSimulacroRepositorio
                    .findByEstudianteDocumentoIdentidadAndSimulacroId(
                            estudiante.getDocumentoIdentidad(), simulacroSeleccionadoId)
                    .orElse(null);
        }

        double promedioSociales = 0.0, promedioNaturales = 0.0, promedioMatematicas = 0.0;
        double promedioLecturaCritica = 0.0, promedioIngles = 0.0, promedioGlobal = 0.0;

        if (!resultados.isEmpty()) {
            promedioSociales    = Math.round(resultados.stream().filter(r -> r.getPuntajeSociales() != null)
                    .mapToInt(ResultadoSimulacro::getPuntajeSociales).average().orElse(0.0) * 10.0) / 10.0;
            promedioNaturales   = Math.round(resultados.stream().filter(r -> r.getPuntajeNaturales() != null)
                    .mapToInt(ResultadoSimulacro::getPuntajeNaturales).average().orElse(0.0) * 10.0) / 10.0;
            promedioMatematicas = Math.round(resultados.stream().filter(r -> r.getPuntajeMatematicas() != null)
                    .mapToInt(ResultadoSimulacro::getPuntajeMatematicas).average().orElse(0.0) * 10.0) / 10.0;
            promedioLecturaCritica = Math.round(resultados.stream().filter(r -> r.getPuntajelecturaCritica() != null)
                    .mapToInt(ResultadoSimulacro::getPuntajelecturaCritica).average().orElse(0.0) * 10.0) / 10.0;
            promedioIngles      = Math.round(resultados.stream().filter(r -> r.getPuntajeIngles() != null)
                    .mapToInt(ResultadoSimulacro::getPuntajeIngles).average().orElse(0.0) * 10.0) / 10.0;
            promedioGlobal      = Math.round(resultados.stream().filter(r -> r.getPuntajeGlobal() != null)
                    .mapToInt(ResultadoSimulacro::getPuntajeGlobal).average().orElse(0.0) * 10.0) / 10.0;
        }

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("salon", salon);
        model.addAttribute("institucionNombre", institucionNombre);
        model.addAttribute("adminNombre", adminNombre);
        model.addAttribute("progresoCurso", progresoCurso);
        model.addAttribute("diasRestantes", diasRestantes);
        model.addAttribute("resultados", resultados);
        model.addAttribute("simulacroSeleccionadoId", simulacroSeleccionadoId);
        model.addAttribute("resultadoSeleccionado", resultadoSeleccionado);
        model.addAttribute("promedioSociales", promedioSociales);
        model.addAttribute("promedioNaturales", promedioNaturales);
        model.addAttribute("promedioMatematicas", promedioMatematicas);
        model.addAttribute("promedioLecturaCritica", promedioLecturaCritica);
        model.addAttribute("promedioIngles", promedioIngles);
        model.addAttribute("promedioGlobal", promedioGlobal);
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

    @GetMapping("/materialEstudiante")
    public String mostrarMaterial(Model model, Authentication authentication) {
        Estudiante estudiante = estudianteService.buscarPorUsername(authentication.getName());
        Long institucionId = estudiante.getInstitucion() != null ? estudiante.getInstitucion().getId() : null;
        model.addAttribute("estudiante", estudiante);
        if (institucionId != null) {
            model.addAttribute("materiales", materialEstudioServices.listarPorInstitucion(institucionId));
        } else {
            model.addAttribute("materiales", java.util.Collections.emptyList());
        }
        return "estudiante/materialEstudiante";
    }

    @GetMapping("/configuracion")
    public String mostrarConfiguracion(Model model, Authentication authentication) {
        Estudiante estudiante = estudianteService.buscarPorUsername(authentication.getName());
        model.addAttribute("estudiante", estudiante);
        return "estudiante/configuracionEstudiante";
    }

    @PostMapping("/configuracion/cambiarPassword")
    public String cambiarPassword(@RequestParam Long id,
            @RequestParam String passwordActual,
            @RequestParam String passwordNueva,
            @RequestParam String confirmarPassword,
            RedirectAttributes redirectAttributes) {
        if (!passwordNueva.equals(confirmarPassword)) {
            redirectAttributes.addFlashAttribute("errorPassword", "Las contraseñas nuevas no coinciden.");
            return "redirect:/estudiante/configuracion";
        }
        boolean exito = usuarioServices.cambiarPassword(id, passwordActual, passwordNueva);
        if (exito) {
            redirectAttributes.addFlashAttribute("exitoPassword", "Contraseña actualizada correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("errorPassword", "La contraseña actual es incorrecta.");
        }
        return "redirect:/estudiante/configuracion";
    }

    @PostMapping("/configuracion/cambiarUsername")
    public String cambiarUsername(@RequestParam Long id,
            @RequestParam String nuevoUsername,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        boolean exito = usuarioServices.cambiarUsername(id, nuevoUsername);
        if (exito) {
            SecurityContextHolder.clearContext();
            session.invalidate();
            return "redirect:/login";
        }
        redirectAttributes.addFlashAttribute("errorUsername", "Ese nombre de usuario ya está en uso.");
        return "redirect:/estudiante/configuracion";
    }

    @GetMapping("/descargarMaterial/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> descargarMaterial(@PathVariable Long id) {
        MaterialEstudio material = materialEstudioServices.descargarMaterial(id);
        byte[] archivo = material.getArchivo();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + material.getNombreArchivo() + "\"")
                .contentType(MediaType.parseMediaType(material.getTipoArchivo()))
                .contentLength(archivo.length)
                .body(archivo);
    }
}
