package co.edu.local.gestionIcfes.enums;

/**
 * Estados posibles de una asistencia estudiantil.
 * <p>
 * Se utiliza en {@link co.edu.local.gestionIcfes.model.Asistencia} para registrar
 * si el estudiante asistió puntualmente, llegó tarde o no asistió a la clase.
 * </p>
 */
public enum EstadoAsistencia {

	/** El estudiante asistió y llegó a tiempo. */
	PRESENTE,

	/** El estudiante no se presentó a la clase. */
	AUSENTE,

	/** El estudiante asistió pero llegó después de la hora de inicio. */
	TARDE
}
