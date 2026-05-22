package co.edu.local.gestionIcfes.dto;

import co.edu.local.gestionIcfes.model.Institucion;
import co.edu.local.gestionIcfes.model.Persona;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de proyección que extiende {@link co.edu.local.gestionIcfes.model.Persona} con
 * los datos propios de un docente para su uso en las vistas.
 * <p>
 * Agrega la {@code especialidad} del docente y su
 * {@link co.edu.local.gestionIcfes.model.Institucion} asociada.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocenteDTO extends Persona {
	private String especialidad;
    private Institucion institucion;

}
