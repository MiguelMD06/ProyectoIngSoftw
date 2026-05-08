package co.edu.local.gestionIcfes.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import co.edu.local.gestionIcfes.dto.PersonaDTO;
import co.edu.local.gestionIcfes.dto.UsuarioDTO;
import co.edu.local.gestionIcfes.enums.TipoIdentificacion;
import co.edu.local.gestionIcfes.model.Docente;
import co.edu.local.gestionIcfes.services.InstitucionService;
import co.edu.local.gestionIcfes.services.RolServices;
import co.edu.local.gestionIcfes.services.UsuarioServices;


@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioServices usuarioServicio;
	
	@Autowired
	private InstitucionService institucionService;
	
	@Autowired
	private RolServices rolService;
	
	
	@GetMapping("/login")
	public String mostrarLogin() {
		return "auth/login";
	}
	
	
	@ModelAttribute("persona")
	public PersonaDTO nuevaPersona() {
		return new PersonaDTO();
	}
	
	@ModelAttribute("usuario")
	public UsuarioDTO nuevoUsuario() {
		return new UsuarioDTO();
	}
	
	@GetMapping("/registroAdmin")
	public String mostrarRegistroAdmin(Model model) {
		model.addAttribute("usuario", new UsuarioDTO());
		return "auth/registro";
	}
	
	@PostMapping("/registroAdmin")
	public String registrarUsuarioAdmin(@ModelAttribute("usuario") UsuarioDTO usuarioDTO) {
		boolean exito;
		if (usuarioServicio.validarUsername(usuarioDTO.getUsername()))
			exito =  usuarioServicio.crearAdmin(usuarioDTO) == null ? false : true; 
		else
			exito = false;
		return exito ? "redirect:/registroAdmin?exito" : "redirect:/registroAdmin?error";
	}
	
	@GetMapping("/registro")
	public String mostrarRegistro(Model model) {
		model.addAttribute("persona", new PersonaDTO());
		model.addAttribute("tiposIdentificaciones", TipoIdentificacion.values());
		model.addAttribute("instituciones", institucionService.listarInstituciones());
		model.addAttribute("roles", rolService.listarRoles());
		return "admin/AdminRegistro";
	}
	
	@PostMapping("/registro")
	public String registrarUsuario(@ModelAttribute("persona") PersonaDTO personaDTO) {
		boolean exito;
		if (personaDTO.getRol() == 2) {
			exito =  (usuarioServicio.crearDocente(personaDTO) == null) ? false : true; 
		}else if (personaDTO.getRol() == 3) {
			exito =  usuarioServicio.crearEstudiante(personaDTO) == null ? false : true; 
		}
		else {
			exito = false;
		}
		return exito ? "redirect:/registro?exito" : "redirect:/registro?error";
	}
	

}
