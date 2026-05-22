package co.edu.local.gestionIcfes.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.local.gestionIcfes.model.Rol;
import co.edu.local.gestionIcfes.repository.RolRepositorio;
import co.edu.local.gestionIcfes.services.RolServices;

/**
 * Implementación de {@link co.edu.local.gestionIcfes.services.RolServices}.
 * <p>
 * Delega directamente en {@link co.edu.local.gestionIcfes.repository.RolRepositorio}.
 * Se usa principalmente al crear usuarios para asignarles el rol correspondiente
 * según el valor numérico de {@code PersonaDTO.rol}.
 * </p>
 */
@Service
public class RolServicesImpl implements RolServices{

	@Autowired
	private RolRepositorio rolRepository;
	
	@Override
	public List<Rol> listarRoles() {
		return rolRepository.findAll();
	}
	
	@Override
	public Rol encontrarRol(String nombre) {
		return rolRepository.findByNombre(nombre);
	}
	
	@Override
	public Rol encontrarRol(Long id) {
		return rolRepository.findById(id).get();
	}
}
