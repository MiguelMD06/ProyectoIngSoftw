package co.edu.local.gestionIcfes.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import co.edu.local.gestionIcfes.dto.UsuarioDTO;
import co.edu.local.gestionIcfes.services.UsuarioServices;


@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioServices usuarioServicio;
	
	
	
	@GetMapping("/login")
	public String mostrarLogin() {
		return "login";
	}
	
	
	@ModelAttribute("usuario")
	public UsuarioDTO NuevoUsuario() {
		return new UsuarioDTO();
	}
	
	@GetMapping("/registro")
	public String mostrarRegistro(Model model) {
		model.addAttribute("usuario", new UsuarioDTO());
		return "AdminRegistro";
	}
	
	@PostMapping("/registro")
	public String registrarUsuario(@ModelAttribute("usuario") UsuarioDTO usuarioDTO) {
		if (usuarioServicio.validarUsername(usuarioDTO)) {
			usuarioServicio.crearUsuario(usuarioDTO);
			return "redirect:/registro?exito";
		}else {
			return "redirect:/registro?error";
		}
	}
	

}
