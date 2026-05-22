package co.edu.local.gestionIcfes.dto;



import java.time.LocalDate;

import co.edu.local.gestionIcfes.enums.EstadoAsistencia;
import co.edu.local.gestionIcfes.model.Docente;
import co.edu.local.gestionIcfes.model.Estudiante;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de formulario para el registro de asistencias por parte del administrador o docente.
 * <p>
 * Consolida en un solo objeto la fecha, el estudiante, el docente y el estado
 * ({@link co.edu.local.gestionIcfes.enums.EstadoAsistencia}) de una asistencia.
 * En el flujo real del controlador, los estados se envían como listas paralelas
 * de documentos y estados; este DTO se usa principalmente como objeto de modelo en la vista.
 * </p>
 */
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

