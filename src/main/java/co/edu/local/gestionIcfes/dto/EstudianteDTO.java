package co.edu.local.gestionIcfes.dto;


import java.util.List;

import co.edu.local.gestionIcfes.model.Asistencia;
import co.edu.local.gestionIcfes.model.Institucion;
import co.edu.local.gestionIcfes.model.Persona;
import co.edu.local.gestionIcfes.model.ResultadoSimulacro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de proyección que extiende {@link co.edu.local.gestionIcfes.model.Persona} con
 * los datos propios de un estudiante para su uso en las vistas.
 * <p>
 * Incluye las colecciones de {@link co.edu.local.gestionIcfes.model.Asistencia} y
 * {@link co.edu.local.gestionIcfes.model.ResultadoSimulacro} del estudiante, así como
 * su {@link co.edu.local.gestionIcfes.model.Institucion} asociada.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteDTO extends Persona {
	private Boolean activo = true;
    private Institucion institucion;
    private List<Asistencia> asistencias;
    private List<ResultadoSimulacro> resultados;

	
}

