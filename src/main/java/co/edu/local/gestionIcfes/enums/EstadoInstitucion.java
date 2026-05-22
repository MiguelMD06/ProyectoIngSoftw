package co.edu.local.gestionIcfes.enums;

/**
 * Estado del ciclo de vida de una institución educativa.
 * <p>
 * {@code CierreInstitucionScheduler} cambia el estado operativo de los usuarios
 * asociados cuando una institución llega a su {@code fechaFinal}; este enum refleja
 * si el curso sigue en curso o ha concluido.
 * </p>
 */
public enum EstadoInstitucion {

	/** La institución está activa y sus usuarios pueden acceder al sistema. */
	ACTIVO,

	/** El período académico de la institución ha concluido. */
	FINALIZADO
}
