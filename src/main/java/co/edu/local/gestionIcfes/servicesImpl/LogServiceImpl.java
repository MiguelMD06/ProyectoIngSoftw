package co.edu.local.gestionIcfes.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.local.gestionIcfes.model.LogCambio;
import co.edu.local.gestionIcfes.repository.LogRepository;
import co.edu.local.gestionIcfes.services.LogService;

@Service
public class LogServiceImpl implements LogService{

	@Autowired
	private LogRepository logRepository;
	
	@Override
	public LogCambio registrarLog(String origenCambio, String descripcion) {
		LogCambio log = new LogCambio(origenCambio, descripcion);
		return logRepository.save(log);
	}
}
