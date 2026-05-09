package co.edu.local.gestionIcfes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.edu.local.gestionIcfes.model.Institucion;
import co.edu.local.gestionIcfes.services.InstitucionService;
import co.edu.local.gestionIcfes.services.UsuarioServices;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UsuarioServices usuarioService;
	
	@Autowired 
	private InstitucionService institucionService;


	    @GetMapping("/pAdmin")
	    public String mostrarPanelAdmin() {
	        return "admin/pAdmin";
	    }


	    @GetMapping("/AdminEstudiante")
	    public String mostrarPanelEstudiantes(Model model) {
	        model.addAttribute("estudiantes", usuarioService.listarEstudiantes() );
	        model.addAttribute("institucion", institucionService.listarInstituciones());

	        model.addAttribute("institucion", new Institucion());
	        return "admin/AdminEstudiante";
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
	    
	    
	    @GetMapping("/AdminDocente")
	    public String mostrarDocentes() {
	        return "admin/AdminDocente";
	    }

	
	}

