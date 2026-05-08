package co.edu.local.gestionIcfes.model;

import co.edu.local.gestionIcfes.enums.TipoIdentificacion;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@MappedSuperclass
public class Persona {

	@Id
	protected String documentoIdentidad;
	@Enumerated(EnumType.STRING)
	@Column(name = "dcte_tipo_identificacion", nullable = false)
	protected TipoIdentificacion tipoIdentificacion;
	
	@Column(name = "primer_nombre", nullable = false)
	protected String primerNombre;
	
	@Column(name = "segundo_nombre", nullable = true)
	protected String segundoNombre;
	
	@Column(name = "primer_apellido", nullable = false)
	protected String primerApellido;
	
	@Column(name = "segundo_apellido", nullable = true)
	protected String segundoApellido;
	
	@Column(name = "celular", nullable = false)
	protected String celular;
	
	@OneToOne
	@JoinColumn(name = "usuario_id", unique = true)
	protected Usuario usuario;
}
