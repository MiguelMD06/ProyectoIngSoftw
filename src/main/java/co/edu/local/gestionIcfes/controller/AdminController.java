package co.edu.local.gestionIcfes.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.edu.local.gestionIcfes.dto.PersonaDTO;
import co.edu.local.gestionIcfes.enums.EstadoAsistencia;
import co.edu.local.gestionIcfes.enums.TipoIdentificacion;
import co.edu.local.gestionIcfes.model.Asistencia;
import co.edu.local.gestionIcfes.model.Docente;
import co.edu.local.gestionIcfes.model.Estudiante;
import co.edu.local.gestionIcfes.model.Institucion;
import co.edu.local.gestionIcfes.model.MaterialEstudio;
import co.edu.local.gestionIcfes.model.ResultadoSimulacro;
import co.edu.local.gestionIcfes.model.Simulacro;
import co.edu.local.gestionIcfes.model.Usuario;
import co.edu.local.gestionIcfes.repository.EstudianteRepositorio;
import co.edu.local.gestionIcfes.repository.ResultadoSimulacroRepositorio;
import co.edu.local.gestionIcfes.repository.UsuarioRepositorio;
import co.edu.local.gestionIcfes.services.AsistenciaService;
import co.edu.local.gestionIcfes.services.DocenteService;
import co.edu.local.gestionIcfes.services.EstudianteService;
import co.edu.local.gestionIcfes.services.InstitucionService;
import co.edu.local.gestionIcfes.services.LogService;
import co.edu.local.gestionIcfes.services.MaterialEstudioServices;
import co.edu.local.gestionIcfes.services.ResultadoSimulacroService;
import co.edu.local.gestionIcfes.services.SimulacroService;
import co.edu.local.gestionIcfes.services.UsuarioServices;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioServices usuarioService;

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private InstitucionService institucionService;

    @Autowired
    private ResultadoSimulacroService resultadoSimulacroService;

    @Autowired
    private SimulacroService simulacroService;

    @Autowired
    private ResultadoSimulacroRepositorio resultadoSimulacroRepositorio;

    @Autowired
    private DocenteService docenteService;

    @Autowired
    private LogService logService;

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private EstudianteRepositorio estudianteRepositorio;

    @Autowired
    private MaterialEstudioServices materialEstudioServices;

    @GetMapping("/pAdmin")
    public String mostrarPanelAdmin(Model model) {
        model.addAttribute("numeroEstudiantes", usuarioService.cantidadEstudiantesActivos());
        model.addAttribute("numeroDocentes", usuarioService.cantidadDocentesActivos());
        model.addAttribute("numeroSimulacros", simulacroService.cantidadSimulacros());
        model.addAttribute("numeroInstituciones", institucionService.cantidadInstitucionesActivas());
        model.addAttribute("logs", logService.listarLogs());
        return "admin/pAdmin";
    }

    @GetMapping("/AdminEstudiante")
    public String mostrarPanelEstudiantes(Model model) {
        model.addAttribute("estudiantes", usuarioService.listarEstudiantesConResultados());
        model.addAttribute("instituciones", institucionService.listarInstituciones());
        model.addAttribute("simulacros", simulacroService.listarSimulacros());
        return "admin/AdminEstudiante";
    }

    @GetMapping("/AdminEstudiante/{id}")
    public String eliminarUsuario(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            estudianteService.eliminarEstudiante(id);
            redirectAttributes.addFlashAttribute("exitoEliminar", "Estudiante eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorEliminar", "Error al eliminar estudiante. Intente nuevamente.");
        }
        return "redirect:/admin/AdminEstudiante";
    }

    @GetMapping("/AdminInstitucion")
    public String mostrarInstituciones(Model model) {
        model.addAttribute("instituciones", institucionService.listarInstituciones());
        model.addAttribute("nuevaInstitucion", new Institucion());
        return "admin/AdminInstitucion";
    }

    @PostMapping("/actualizarInstitucion")
    public String actualizarInstitucion(@ModelAttribute Institucion institucion, RedirectAttributes redirectAttributes) {
        try {
            institucionService.actualizarInstitucion(institucion);
            redirectAttributes.addFlashAttribute("exitoInstitucion", "Institución actualizada exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorInstitucion", "Error al actualizar la institución.");
        }
        return "redirect:/admin/AdminInstitucion";
    }

    @GetMapping("/eliminarInstitucion/{id}")
    public String eliminarInstitucion(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            institucionService.eliminarInstitucion(id);
            redirectAttributes.addFlashAttribute("exitoInstitucion", "Institución eliminada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorInstitucion", "Error al eliminar la institución: " + e.getMessage());
        }
        return "redirect:/admin/AdminInstitucion";
    }

    @PostMapping("/crearInstitucion")
    public String crearInstitucion(@ModelAttribute Institucion institucion,
                                   @RequestParam(required = false) String origen,
                                   RedirectAttributes redirectAttributes) {
        try {
            institucionService.guardarInstitucion(institucion);
            redirectAttributes.addFlashAttribute("exitoInstitucion", "Institución registrada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorInstitucion", "Error al registrar institución. Intente nuevamente.");
        }
        return "instituciones".equals(origen)
                ? "redirect:/admin/AdminInstitucion"
                : "redirect:/admin/AdminEstudiante";
    }

    @PostMapping("/uploadSimulacro")
    public String uploadSimulacro(
            @RequestParam("documentoIdentidad") String documentoIdentidad,
            @RequestParam("simulacroId") Long simulacroId,
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam("puntajeSociales") Integer puntajeSociales,
            @RequestParam("puntajeNaturales") Integer puntajeNaturales,
            @RequestParam("puntajeMatematicas") Integer puntajeMatematicas,
            @RequestParam("puntajelecturaCritica") Integer puntajelecturaCritica,
            @RequestParam("puntajeIngles") Integer puntajeIngles,
            @RequestParam("puestoSalon") Integer puestoSalon,
            RedirectAttributes redirectAttributes) {
        try {
            resultadoSimulacroService.subirResultado(documentoIdentidad, simulacroId, archivo,
                    puntajeSociales, puntajeNaturales, puntajeMatematicas,
                    puntajelecturaCritica, puntajeIngles, puestoSalon);
            redirectAttributes.addFlashAttribute("exitoInstitucion", "Resultado de simulacro subido correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorInstitucion", "Error al subir el resultado: " + e.getMessage());
        }
        return "redirect:/admin/AdminEstudiante";
    }

    @GetMapping("/eliminarResultado/{id}")
    public String eliminarResultado(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            resultadoSimulacroRepositorio.deleteById(id);
            logService.registrarLog("Resultado Simulacro", "Resultado simulacro eliminado (id=" + id + ")");
            redirectAttributes.addFlashAttribute("exitoInstitucion", "Resultado eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorInstitucion", "Error al eliminar el resultado.");
        }
        return "redirect:/admin/AdminEstudiante";
    }

    @GetMapping("/AdminSimulacro")
    public String mostrarFormularioSimulacro(Model model) {
        model.addAttribute("simulacros", simulacroService.listarSimulacros());
        return "admin/AdminSimulacro";
    }

    @PostMapping("/crearSimulacro")
    public String guardarSimulacro(@ModelAttribute Simulacro simulacro,
                                   RedirectAttributes redirectAttributes) {
        if (simulacro.getId() != null) {
            simulacroService.actualizarSimulacro(simulacro);
            redirectAttributes.addFlashAttribute("exitoSimulacro", "Simulacro actualizado correctamente.");
        } else {
            simulacroService.crearSimulacro(simulacro);
            redirectAttributes.addFlashAttribute("exitoSimulacro", "Simulacro registrado correctamente.");
        }
        return "redirect:/admin/AdminSimulacro";
    }

    @GetMapping("/eliminarSimulacro/{id}")
    public String eliminarSimulacro(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            simulacroService.eliminarSimulacro(id);
            redirectAttributes.addFlashAttribute("exitoSimulacro", "Simulacro eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorSimulacro", "Error al eliminar el simulacro: " + e.getMessage());
        }
        return "redirect:/admin/AdminSimulacro";
    }

    @GetMapping("/descargarResultado/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> descargarResultado(@PathVariable Long id) {
        ResultadoSimulacro resultado = resultadoSimulacroRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Resultado no encontrado"));
        byte[] archivo = resultado.getDatos();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resultado.getNombreArchivo() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(archivo.length)
                .body(archivo);
    }

    @GetMapping("/AdminEstudiante/modificar/{id}")
    public String mostrarActualizar(@PathVariable String id, Model model) {
        model.addAttribute("persona", estudianteService.convertirEstudiantePersona(estudianteService.buscarEstudiante(id)));
        model.addAttribute("tiposIdentificaciones", TipoIdentificacion.values());
        model.addAttribute("instituciones", institucionService.listarInstituciones());
        return "admin/AdminActualizar";
    }

    @PostMapping("/modificar")
    public String registrarUsuario(@ModelAttribute("persona") PersonaDTO personaDTO) {
        boolean exito = false;
        if (personaDTO.getRol() == 2) {
            exito = (docenteService.actualizarDocente(personaDTO) == null) ? false : true;
            return exito
                    ? "redirect:/admin/AdminDocente/modificar/" + personaDTO.getDocumentoIdentidad() + "?exito"
                    : "redirect:/admin/AdminDocente/modificar/" + personaDTO.getDocumentoIdentidad() + "?error";
        } else if (personaDTO.getRol() == 3) {
            exito = estudianteService.actualizarEstudiante(personaDTO) == null ? false : true;
            return exito
                    ? "redirect:/admin/AdminEstudiante/modificar/" + personaDTO.getDocumentoIdentidad() + "?exito"
                    : "redirect:/admin/AdminEstudiante/modificar/" + personaDTO.getDocumentoIdentidad() + "?error";
        }
        return "";
    }

    @GetMapping("/AdminDocente/modificar/{id}")
    public String mostrarActualizarDocente(@PathVariable String id, Model model) {
        model.addAttribute("persona", docenteService.convertirDocentePersona(docenteService.obtenerDocentePorId(id)));
        model.addAttribute("tiposIdentificaciones", TipoIdentificacion.values());
        model.addAttribute("instituciones", institucionService.listarInstituciones());
        return "admin/AdminActualizar";
    }

    @GetMapping("/AdminAsistencia")
    public String mostrarAsistencia(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(required = false) Long institucionId,
            @RequestParam(required = false) String salon,
            Model model, Authentication authentication) {

        final LocalDate fechaBusqueda = (fecha != null) ? fecha : LocalDate.now();

        List<Estudiante> estudiantes = new ArrayList<>();
        if (institucionId != null && salon != null && !salon.isEmpty()) {
            estudiantes = estudianteRepositorio.findByInstitucionIdAndUsuarioSalon(institucionId, salon);
        }

        Map<String, EstadoAsistencia> estadosPorDoc = new HashMap<>();
        for (Estudiante est : estudiantes) {
            asistenciaService.listarPorEstudiante(est.getDocumentoIdentidad()).stream()
                    .filter(a -> a.getFecha().equals(fechaBusqueda))
                    .findFirst()
                    .ifPresent(a -> estadosPorDoc.put(est.getDocumentoIdentidad(), a.getEstado()));
        }

        model.addAttribute("fecha", fechaBusqueda);
        model.addAttribute("instituciones", institucionService.listarInstituciones());
        model.addAttribute("institucionId", institucionId);
        model.addAttribute("salon", salon);
        model.addAttribute("estudiantes", estudiantes);
        model.addAttribute("estadosPorDoc", estadosPorDoc);
        model.addAttribute("estadosDisponibles", EstadoAsistencia.values());
        return "admin/AdminAsistencia";
    }

    @PostMapping("/registrarAsistencias")
    public String registrarAsistencias(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(required = false) List<String> documentos,
            @RequestParam(required = false) List<String> estados,
            RedirectAttributes redirectAttributes) {

        if (documentos != null && estados != null) {
            List<EstadoAsistencia> estadosEnum = estados.stream()
                    .map(EstadoAsistencia::valueOf)
                    .toList();
            asistenciaService.registrarOActualizar(fecha, documentos, estadosEnum);
        }
        redirectAttributes.addFlashAttribute("exitoAsistencia", "Asistencias registradas correctamente.");
        return "redirect:/admin/AdminAsistencia?fecha=" + fecha;
    }

    @GetMapping("/AdminDocente")
    public String mostrarDocentes(Model model) {
        model.addAttribute("docentes", docenteService.listarDocentes());
        model.addAttribute("docente", new Docente());
        model.addAttribute("instituciones", institucionService.listarInstituciones());
        return "admin/AdminDocente";
    }

    @GetMapping("/eliminarDocente/{id}")
    public String eliminarDocente(@PathVariable String id) {
        docenteService.eliminarDocente(id);
        return "redirect:/admin/AdminDocente";
    }

    @PostMapping("/guardarDocente")
    public String guardarDocente(@ModelAttribute Docente docente) {
        docenteService.guardarDocente(docente);
        return "redirect:/admin/AdminDocente?exito";
    }

    @GetMapping("/configuracion")
    public String mostrarConfiguracion(Model model, Authentication authentication) {
        Usuario adminActual = usuarioRepositorio.findByUsername(authentication.getName());
        model.addAttribute("adminActual", adminActual);
        model.addAttribute("usuarios", usuarioService.listarTodosUsuarios());
        model.addAttribute("instituciones", institucionService.listarInstituciones());
        return "admin/AdminConfiguracion";
    }

    @PostMapping("/configuracion/cambiarPassword")
    public String cambiarPassword(
            @RequestParam Long id,
            @RequestParam String passwordActual,
            @RequestParam String passwordNueva,
            @RequestParam String confirmarPassword,
            RedirectAttributes redirectAttributes) {
        if (!passwordNueva.equals(confirmarPassword)) {
            redirectAttributes.addFlashAttribute("errorPassword", "Las contraseñas nuevas no coinciden.");
            return "redirect:/admin/configuracion";
        }
        boolean exito = usuarioService.cambiarPassword(id, passwordActual, passwordNueva);
        if (exito) {
            redirectAttributes.addFlashAttribute("exitoPassword", "Contraseña actualizada correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("errorPassword", "La contraseña actual es incorrecta.");
        }
        return "redirect:/admin/configuracion";
    }

    @PostMapping("/configuracion/cambiarPasswordUsuario")
    public String cambiarPasswordUsuario(
            @RequestParam Long id,
            @RequestParam String passwordNueva,
            @RequestParam String confirmarPassword,
            RedirectAttributes redirectAttributes) {
        if (!passwordNueva.equals(confirmarPassword)) {
            redirectAttributes.addFlashAttribute("errorUsuarios", "Las contraseñas no coinciden.");
            return "redirect:/admin/configuracion";
        }
        usuarioService.cambiarPasswordAdmin(id, passwordNueva);
        redirectAttributes.addFlashAttribute("exitoUsuarios", "Contraseña actualizada correctamente.");
        return "redirect:/admin/configuracion";
    }

    @GetMapping("/configuracion/toggleActivo/{id}")
    public String toggleActivo(@PathVariable Long id, Authentication authentication,
                               RedirectAttributes redirectAttributes) {
        Usuario adminActual = usuarioRepositorio.findByUsername(authentication.getName());
        if (adminActual.getId().equals(id)) {
            redirectAttributes.addFlashAttribute("errorUsuarios", "No puedes desactivar tu propia cuenta.");
            return "redirect:/admin/configuracion";
        }
        usuarioService.toggleActivo(id);
        return "redirect:/admin/configuracion";
    }

    @GetMapping("/configuracion/restablecerPassword/{id}")
    public String restablecerPassword(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean exito = usuarioService.restablecerPassword(id);
        if (exito) {
            redirectAttributes.addFlashAttribute("exitoUsuarios", "Contraseña restablecida al número de documento.");
        } else {
            redirectAttributes.addFlashAttribute("errorUsuarios", "No se puede restablecer: el usuario admin no tiene documento asociado.");
        }
        return "redirect:/admin/configuracion";
    }

    @GetMapping("/AdminMaterial")
    public String mostrarMaterial(
            @RequestParam(required = false) Long institucionId,
            Model model) {
        model.addAttribute("instituciones", institucionService.listarInstituciones());
        model.addAttribute("institucionSeleccionada", institucionId);
        if (institucionId != null) {
            model.addAttribute("materiales", materialEstudioServices.listarPorInstitucion(institucionId));
        } else {
            model.addAttribute("materiales", java.util.Collections.emptyList());
        }
        return "admin/AdminMaterial";
    }

    @PostMapping("/subirMaterial")
    public String subirMaterial(
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam("titulo") String titulo,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("semana") Integer semana,
            @RequestParam("institucionId") Long institucionId,
            RedirectAttributes redirectAttributes) {
        try {
            materialEstudioServices.subirMaterial(archivo, titulo, descripcion, semana, institucionId);
            logService.registrarLog("material", "Material semana " + semana + " subido: " + titulo);
            redirectAttributes.addFlashAttribute("exitoMaterial", "Material subido correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMaterial", "Error al subir el material: " + e.getMessage());
        }
        return "redirect:/admin/AdminMaterial?institucionId=" + institucionId;
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

    @GetMapping("/eliminarMaterial/{id}")
    public String eliminarMaterial(@PathVariable Long id,
                                   @RequestParam(required = false) Long institucionId,
                                   RedirectAttributes redirectAttributes) {
        try {
            materialEstudioServices.eliminarMaterial(id);
            logService.registrarLog("material", "Material eliminado (id=" + id + ")");
            redirectAttributes.addFlashAttribute("exitoMaterial", "Material eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMaterial", "Error al eliminar el material.");
        }
        String redirect = "redirect:/admin/AdminMaterial";
        if (institucionId != null) redirect += "?institucionId=" + institucionId;
        return redirect;
    }
}
