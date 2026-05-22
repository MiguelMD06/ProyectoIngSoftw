package co.edu.local.gestionIcfes.services;

import java.util.List;

import co.edu.local.gestionIcfes.model.LogCambio;

/**
 * Contrato del servicio de auditoría de cambios.
 * <p>
 * Todos los servicios que modifican datos llaman a {@link #registrarLog} para dejar
 * trazabilidad. La vista del panel admin muestra únicamente los últimos 5 registros.
 * </p>
 */
public interface LogService {

	/**
	 * Crea y persiste un registro de auditoría con la fecha y hora actuales.
	 *
	 * @param origenCambio módulo que originó el cambio ({@code "estudiante"}, {@code "docente"},
	 *                     {@code "simulacro"}, {@code "asistencia"}, {@code "usuario"},
	 *                     {@code "institucion"} o {@code "material"}).
	 * @param descripcion  texto descriptivo de la operación realizada.
	 * @return el registro de log persistido.
	 */
	public LogCambio registrarLog(String origenCambio, String descripcion);

	/**
	 * Devuelve los últimos 5 registros de auditoría ordenados por ID descendente.
	 *
	 * @return lista de hasta 5 registros recientes.
	 */
	public List<LogCambio> listarLogs();
}
