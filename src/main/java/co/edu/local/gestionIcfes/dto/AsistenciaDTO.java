package co.edu.local.gestionIcfes.dto;



import java.time.LocalDate;

import co.edu.local.gestionIcfes.enums.EstadoAsistencia;
import co.edu.local.gestionIcfes.model.Docente;
import co.edu.local.gestionIcfes.model.Estudiante;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsistenciaDTO {

	private Long id;

	private LocalDate fecha;

	private Estudiante estudiante;

	private Docente docente;

	private EstadoAsistencia estado;
    

}

