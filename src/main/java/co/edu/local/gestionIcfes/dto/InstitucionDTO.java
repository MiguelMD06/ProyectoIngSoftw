package co.edu.local.gestionIcfes.dto;

import java.util.List;

import co.edu.local.gestionIcfes.enums.EstadoInstitucion;
import co.edu.local.gestionIcfes.model.Docente;
import co.edu.local.gestionIcfes.model.Estudiante;
import co.edu.local.gestionIcfes.model.Simulacro;
import co.edu.local.gestionIcfes.model.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de proyección para mostrar el resumen de una institución en las vistas administrativas.
 * <p>
 * Contiene las listas de {@link co.edu.local.gestionIcfes.model.Usuario}s,
 * {@link co.edu.local.gestionIcfes.model.Estudiante}s, {@link co.edu.local.gestionIcfes.model.Docente}s
 * y {@link co.edu.local.gestionIcfes.model.Simulacro}s asociados a la institución,
 * útiles para poblar tablas y controles de selección en Thymeleaf.
 * </p>
 */
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
	private List<Simulacro> simulacros;

}
