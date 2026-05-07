package co.edu.local.gestionIcfes.dto;

import java.util.List;

import co.edu.local.gestionIcfes.enums.EstadoInstitucion;
import co.edu.local.gestionIcfes.model.Docente;
import co.edu.local.gestionIcfes.model.Estudiante;

import co.edu.local.gestionIcfes.model.Salon;
import co.edu.local.gestionIcfes.model.Simulacro;
import co.edu.local.gestionIcfes.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstitucionDTO {
	private Long id;
	private String nombre;
	private String direccion;
	private EstadoInstitucion estado;
	private List<Usuario> usuarios;

	private List<Estudiante> estudiantes;
	private List<Docente> docentes;
	private List<Salon> salones;
	private List<Simulacro> simulacros;

}
