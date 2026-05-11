package co.edu.local.gestionIcfes.servicesImpl;

import java.util.ArrayList;
import java.util.List;

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
	
	@Override
	public List<LogCambio> listarLogs() {
		System.out.println(logRepository.findAllByOrderByIdDesc().isEmpty());
		if (logRepository.findAllByOrderByIdDesc().isEmpty()) {
			return new ArrayList<LogCambio>();
		}else if (logRepository.findAllByOrderByIdDesc().size() < 5)
			return logRepository.findAllByOrderByIdDesc();
		return logRepository.findAllByOrderByIdDesc().subList(0, 5);
	}
}
