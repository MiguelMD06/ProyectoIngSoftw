package co.edu.local.gestionIcfes.dto;


import java.util.List;

import co.edu.local.gestionIcfes.model.Asistencia;
import co.edu.local.gestionIcfes.model.Institucion;
import co.edu.local.gestionIcfes.model.Persona;
import co.edu.local.gestionIcfes.model.ResultadoSimulacro;
import co.edu.local.gestionIcfes.model.Salon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteDTO extends Persona {
	private Boolean activo = true;
    private Institucion institucion;
    private Salon salon;
    private List<Asistencia> asistencias;
    private List<ResultadoSimulacro> resultados;

	
}

