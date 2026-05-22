package co.edu.local.gestionIcfes.servicesImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.local.gestionIcfes.model.LogCambio;
import co.edu.local.gestionIcfes.repository.LogRepository;
import co.edu.local.gestionIcfes.services.LogService;

/**
 * Implementación de {@link co.edu.local.gestionIcfes.services.LogService}.
 * <p>
 * {@link #listarLogs()} retorna únicamente los últimos 5 registros para no sobrecargar
 * el panel de administración. El constructor de dos argumentos de
 * {@link co.edu.local.gestionIcfes.model.LogCambio} asigna automáticamente la fecha y hora.
 * </p>
 */
@Service
public class LogServiceImpl implements LogService{

	@Autowired
	private LogRepository logRepository;
	
	@Override
	public LogCambio registrarLog(String origenCambio, String descripcion) {
		LogCambio log = new LogCambio(origenCambio, descripcion);
		return logRepository.save(log);
	}
	
	@Override
	public List<LogCambio> listarLogs() {
		List<LogCambio> todos = logRepository.findAllByOrderByIdDesc();
		if (todos.isEmpty()) return new ArrayList<>();
		return todos.subList(0, Math.min(5, todos.size()));
	}
}
