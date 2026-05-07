package co.edu.local.gestionIcfes.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class Persona {

	@Id
	private String documentoIdentidad;
	
	@Column(name = "primer_nombre", nullable = false)
	private String primerNombre;
	
	@Column(name = "segundo_nombre", nullable = true)
	private String segundoNombre;
	
	@Column(name = "primer_apellido", nullable = false)
	private String primerApellido;
	
	@Column(name = "segundo_apellido", nullable = true)
	private String segundoApellido;
	
	@Column(name = "celular", nullable = false)
	private String celular;
}
