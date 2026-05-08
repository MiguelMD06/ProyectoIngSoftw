package co.edu.local.gestionIcfes.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.local.gestionIcfes.model.Institucion;
import co.edu.local.gestionIcfes.repository.InstitucionRepositorio;
import co.edu.local.gestionIcfes.services.InstitucionService;

@Service
public class InstitucionServicesImpl implements InstitucionService{

	@Autowired
	private InstitucionRepositorio institucionRepository;
	
	@Override
	public List<Institucion> listarInstituciones() {
		return institucionRepository.findAll();
	}
}
