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



	    @GetMapping("/pAdmin")
	    public String mostrarPanelAdmin(Model model) {
	    	model.addAttribute("numeroEstudiantes", usuarioService.cantidadEstudiantes());
	    	model.addAttribute("numeroDocentes", usuarioService.cantidadDocentes());
	    	model.addAttribute("numeroSimulacros", simulacroService.cantidadSimulacros());
	    	model.addAttribute("logs", logService.listarLogs());  
	    			
	        return "admin/pAdmin";
	    }


	    @GetMapping("/AdminEstudiante")
	    public String mostrarPanelEstudiantes(Model model) {
	        model.addAttribute("estudiantes", usuarioService.listarEstudiantesConResultados() );
	        model.addAttribute("instituciones", institucionService.listarInstituciones());
	        model.addAttribute("simulacros", simulacroService.listarSimulacros());

	        model.addAttribute("institucion", new Institucion());
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
	    

	    
	    @PostMapping("/crearInstitucion")
	    public String crearInstitucion(@ModelAttribute Institucion institucion, RedirectAttributes redirectAttributes) {
	    	  try {
	    	        institucionService.guardarInstitucion(institucion);
	    	        redirectAttributes.addFlashAttribute("exitoInstitucion", "Institución registrada exitosamente");
	    	    } catch (Exception e) {
	    	        redirectAttributes.addFlashAttribute("errorInstitucion", "Error al registrar institución. Intente nuevamente.");
	    	    }
	        return "redirect:/admin/AdminEstudiante";
	    }
	    
	    @PostMapping("/uploadSimulacro")
	    public String uploadSimulacro(@RequestParam("documentoIdentidad") String documentoIdentidad,
	    								@RequestParam("simulacroId") Long simulacroId,
	                                  @RequestParam("archivo") MultipartFile archivo,
	                                  RedirectAttributes redirectAttributes) {
	        try {
	      
	            resultadoSimulacroService.subirResultado(documentoIdentidad, simulacroId, archivo);
	            redirectAttributes.addFlashAttribute("exitoInstitucion", "Resultado de simulacro subido correctamente.");
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("errorInstitucion", "Error al subir el resultado: " + e.getMessage());
	        }
	        return "redirect:/admin/AdminEstudiante";
	    }


	    @GetMapping("/AdminSimulacro")
	    public String mostrarFormularioSimulacro(Model model) {
	        model.addAttribute("simulacro", new Simulacro());
	        return "admin/AdminSimulacro";
	    }

	    @PostMapping("/crearSimulacro")
	    public String crearSimulacro(@ModelAttribute Simulacro simulacro,
	                                 RedirectAttributes redirectAttributes) {
	        simulacroService.crearSimulacro(simulacro);
	        redirectAttributes.addFlashAttribute("exitoSimulacro", "Simulacro registrado correctamente.");
	        return "redirect:/admin/AdminSimulacro";
	    }
	    
	    @GetMapping("/descargarResultado/{id}")
	    @Transactional(readOnly = true)
	    public ResponseEntity<byte[]> descargarResultado(@PathVariable Long id) {

	        ResultadoSimulacro resultado = resultadoSimulacroRepositorio.findById(id)
	                .orElseThrow(() -> new RuntimeException("Resultado no encontrado"));

	        // Forzar la lectura del LOB dentro de la transacción
	        byte[] archivo = resultado.getDatos();

	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION,
	                        "attachment; filename=\"" + resultado.getNombreArchivo() + "\"")
	                .contentType(MediaType.APPLICATION_PDF)
	                .contentLength(archivo.length)
	                .body(archivo);
	    }
	    
	    @GetMapping("/AdminEstudiante/modificar/{id}")
		public String mostrarActualizar(@PathVariable String id,Model model) {
			model.addAttribute("persona", estudianteService.convertirEstudiantePersona(estudianteService.buscarEstudiante(id)));
			model.addAttribute("tiposIdentificaciones", TipoIdentificacion.values());
			model.addAttribute("instituciones", institucionService.listarInstituciones());
			return "admin/AdminActualizar";
		}
		
		@PostMapping("/modificar")
		public String registrarUsuario(@ModelAttribute("persona") PersonaDTO personaDTO) {
			boolean exito = false;
			
			if (personaDTO.getRol() == 2) {
				exito =  (docenteService.actualizarDocente(personaDTO) == null) ? false : true; 
				return exito ? "redirect:/admin/AdminDocente/modificar/"+personaDTO.getDocumentoIdentidad()+"?exito" : "redirect:/admin/AdminDocente/modificar/"+personaDTO.getDocumentoIdentidad()+"?error";
			}else if (personaDTO.getRol() == 3) {
				exito =  estudianteService.actualizarEstudiante(personaDTO) == null ? false : true; 
				return exito ? "redirect:/admin/AdminEstudiante/modificar/"+personaDTO.getDocumentoIdentidad()+"?exito" : "redirect:/admin/AdminEstudiante/modificar/"+personaDTO.getDocumentoIdentidad()+"?error";
			}
			return "";
			
		}
		
		@GetMapping("/AdminDocente/modificar/{id}")
		public String mostrarActualizarDocente(@PathVariable String id,Model model) {
			model.addAttribute("persona", docenteService.convertirDocentePersona(docenteService.obtenerDocentePorId(id)));
			model.addAttribute("tiposIdentificaciones", TipoIdentificacion.values());
			model.addAttribute("instituciones", institucionService.listarInstituciones());
			return "admin/AdminActualizar";
		}
		
	    
	    
	    @GetMapping("/AdminAsistencia")
	    public String mostrarAsistencia(
	            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
	            Model model, Authentication authentication) {

	        final LocalDate fechaBusqueda = (fecha != null) ? fecha : LocalDate.now();

	        Usuario admin = usuarioRepositorio.findByUsername(authentication.getName());
	        List<Estudiante> estudiantes = new ArrayList<>();
	        if (admin != null && admin.getInstitucion() != null) {
	            estudiantes = estudianteRepositorio.findByInstitucionId(admin.getInstitucion().getId());
	        }

	        Map<String, EstadoAsistencia> estadosPorDoc = new HashMap<>();
	        for (Estudiante est : estudiantes) {
	            asistenciaService.listarPorEstudiante(est.getDocumentoIdentidad()).stream()
	                    .filter(a -> a.getFecha().equals(fechaBusqueda))
	                    .findFirst()
	                    .ifPresent(a -> estadosPorDoc.put(est.getDocumentoIdentidad(), a.getEstado()));
	        }

	        model.addAttribute("fecha", fechaBusqueda);
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
	

	
	}

