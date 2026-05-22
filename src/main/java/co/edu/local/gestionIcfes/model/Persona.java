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

/**
 * Superclase abstracta que centraliza los datos de identidad comunes a estudiantes y docentes.
 * <p>
 * No genera tabla propia en la base de datos ({@code @MappedSuperclass}); sus campos se
 * mapean directamente en las tablas {@code estudiantes} y {@code docentes}.
 * La clave primaria {@code documentoIdentidad} es un {@code String} con el número de
 * documento del individuo (ver {@link co.edu.local.gestionIcfes.enums.TipoIdentificacion}).
 * </p>
 */
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
