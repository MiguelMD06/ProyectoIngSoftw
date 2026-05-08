package co.edu.local.gestionIcfes.model;

import co.edu.local.gestionIcfes.enums.TipoIdentificacion;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class Persona {

	@Id
	private String documentoIdentidad;
	@Enumerated(EnumType.STRING)
	@Column(name = "dcte_tipo_identificacion", nullable = false)
	private TipoIdentificacion tipoIdentificacion;
	
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
