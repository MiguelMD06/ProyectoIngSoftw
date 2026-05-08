package co.edu.local.gestionIcfes.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import co.edu.local.gestionIcfes.dto.PersonaDTO;
import co.edu.local.gestionIcfes.services.UsuarioServices;


@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioServices usuarioServicio;
	
	
	
	@GetMapping("/login")
	public String mostrarLogin() {
		return "auth/login";
	}
	
	
	@ModelAttribute("persona")
	public PersonaDTO nuevaPersona() {
		return new PersonaDTO();
	}
	
	@GetMapping("/registro")
	public String mostrarRegistro(Model model) {
		model.addAttribute("persona", new PersonaDTO());
		return "admin/AdminRegistro";
	}
	
	@PostMapping("/registro")
	public String registrarUsuario(@ModelAttribute("persona") PersonaDTO personaDTO) {
		if (personaDTO.getRol().toString() == "ROLE_DOCENTE") {
			usuarioServicio.crearDocente(personaDTO);
			return "redirect:/registro?exito";
		}else if (personaDTO.getRol().toString() == "ROLE_ESTUDIANTE") {
			usuarioServicio.crearEstudiante(personaDTO);
			return "redirect:/registro?exito";
		}
		else {
			return "redirect:/registro?error";
		}
	}
	

}
