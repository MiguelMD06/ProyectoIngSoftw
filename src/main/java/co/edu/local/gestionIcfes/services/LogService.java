package co.edu.local.gestionIcfes.services;

import java.util.List;

import co.edu.local.gestionIcfes.model.LogCambio;

public interface LogService {

	public LogCambio registrarLog(String origenCambio, String descripcion);
	public List<LogCambio> listarLogs();
}
