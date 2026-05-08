package co.edu.local.gestionIcfes.dto;

import co.edu.local.gestionIcfes.enums.TipoIdentificacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
