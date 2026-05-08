package co.edu.local.gestionIcfes.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.local.gestionIcfes.model.Rol;
import co.edu.local.gestionIcfes.repository.RolRepositorio;
import co.edu.local.gestionIcfes.services.RolServices;

@Service
public class RolServicesImpl implements RolServices{

	@Autowired
	private RolRepositorio rolRepository;
	
	@Override
	public List<Rol> listarRoles() {
		return rolRepository.findAll();
	}
}
