package co.edu.local.gestionIcfes.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import co.edu.local.gestionIcfes.model.Asistencia;
import co.edu.local.gestionIcfes.model.Docente;
import co.edu.local.gestionIcfes.model.Institucion;
import co.edu.local.gestionIcfes.repository.AsistenciaRepositorio;
import co.edu.local.gestionIcfes.repository.EstudianteRepositorio;
import co.edu.local.gestionIcfes.services.DocenteService;

@Controller
@RequestMapping("docente")
public class DocenteController {

    @Autowired
    private DocenteService docenteService;

    @Autowired
    private EstudianteRepositorio estudianteRepositorio;

    @Autowired
    private AsistenciaRepositorio asistenciaRepositorio;

    @GetMapping("/pDocente")
    public String mostrarPanelDocente(Model model, Authentication authentication) {
        Docente docente = docenteService.buscarPorUsername(authentication.getName());

        String salon = docente.getUsuario() != null ? docente.getUsuario().getSalon() : "Sin asignar";
        String institucionNombre = "Sin asignar";
        int progresoCurso = 0;
        long diasRestantes = 0;
        int totalEstudiantes = 0;
        long presentesHoy = 0;
        long ausentesHoy = 0;
        long tardesHoy = 0;

        Institucion institucion = docente.getInstitucion();
        if (institucion != null) {
            institucionNombre = institucion.getNombre();

            if (salon != null && !salon.isBlank()) {
                final String salonFinal = salon;
                totalEstudiantes = (int) estudianteRepositorio.findByInstitucionId(institucion.getId())
                        .stream()
                        .filter(e -> salonFinal.equals(e.getUsuario() != null ? e.getUsuario().getSalon() : null))
                        .count();

                LocalDate hoy = LocalDate.now();
                List<Asistencia> asistenciasHoy = asistenciaRepositorio
                        .findByFechaAndEstudiante_Institucion_IdAndEstudiante_Usuario_Salon(
                                hoy, institucion.getId(), salon);
                presentesHoy = asistenciasHoy.stream().filter(a -> "PRESENTE".equals(a.getEstado().name())).count();
                ausentesHoy  = asistenciasHoy.stream().filter(a -> "AUSENTE".equals(a.getEstado().name())).count();
                tardesHoy    = asistenciasHoy.stream().filter(a -> "TARDE".equals(a.getEstado().name())).count();
            }

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

        model.addAttribute("docente", docente);
        model.addAttribute("salon", salon);
        model.addAttribute("institucionNombre", institucionNombre);
        model.addAttribute("progresoCurso", progresoCurso);
        model.addAttribute("diasRestantes", diasRestantes);
        model.addAttribute("totalEstudiantes", totalEstudiantes);
        model.addAttribute("presentesHoy", presentesHoy);
        model.addAttribute("ausentesHoy", ausentesHoy);
        model.addAttribute("tardesHoy", tardesHoy);
        return "docente/pDocente";
    }
}
