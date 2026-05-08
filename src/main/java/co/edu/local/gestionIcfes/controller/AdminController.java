package co.edu.local.gestionIcfes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {


	    @GetMapping("/pAdmin")
	    public String mostrarPanelAdmin() {
	        return "admin/pAdmin";
	    }


	    @GetMapping("/AdminEstudiante")
	    public String mostrarPanelEstudiantes() {
	        return "admin/AdminEstudiante";
	    }

	
	    @GetMapping("/pDocente")
	    public String mostrarDocentes() {
	        return "docente/pDocente";
	    }

	
	}

