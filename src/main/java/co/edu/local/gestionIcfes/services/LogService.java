package co.edu.local.gestionIcfes.services;

import co.edu.local.gestionIcfes.model.LogCambio;

public interface LogService {

	public LogCambio registrarLog(String origenCambio, String descripcion);
}
