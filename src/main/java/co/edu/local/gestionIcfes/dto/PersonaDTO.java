package co.edu.local.gestionIcfes.dto;

import co.edu.local.gestionIcfes.enums.TipoIdentificacion;
import co.edu.local.gestionIcfes.model.Institucion;
import co.edu.local.gestionIcfes.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de formulario para crear y actualizar estudiantes y docentes.
 * <p>
 * El campo {@code rol} usa valores numéricos: {@code 2} = {@code ROLE_DOCENTE},
 * {@code 3} = {@code ROLE_ESTUDIANTE}, correspondientes a los IDs sembrados en la
 * tabla {@code roles} por {@link co.edu.local.gestionIcfes.config.DataInitializer}.
 * </p>
 * <p>
 * {@code institucion} es el {@code id} de la {@link co.edu.local.gestionIcfes.model.Institucion}
 * a la que se asignará la persona. {@code salon} indica el aula dentro de esa institución.
 * {@code especialidad} solo se usa cuando el DTO representa a un docente.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaDTO {

	private String documentoIdentidad;

	private TipoIdentificacion tipoIdentificacion;

	private String primerNombre;

	private String segundoNombre;

	private String primerApellido;

	private String segundoApellido;

	private String celular;
	
	private Long rol;

	private String salon;

	private Long institucion;

	private String especialidad;

	public PersonaDTO(String documentoIdentidad, TipoIdentificacion tipoIdentificacion, String primerNombre,
			String segundoNombre, String primerApellido, String segundoApellido, String celular, String salon,
			Long institucion) {
		super();
		this.documentoIdentidad = documentoIdentidad;
		this.tipoIdentificacion = tipoIdentificacion;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.celular = celular;
		this.salon = salon;
		this.institucion = institucion;
	}
	
	
}
