package co.edu.local.gestionIcfes.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.local.gestionIcfes.enums.EstadoInstitucion;
import co.edu.local.gestionIcfes.model.Institucion;
import co.edu.local.gestionIcfes.repository.InstitucionRepositorio;
import co.edu.local.gestionIcfes.services.InstitucionService;
import co.edu.local.gestionIcfes.services.LogService;

@Service
public class InstitucionServicesImpl implements InstitucionService{

	@Autowired
	private InstitucionRepositorio institucionRepository;
	
	@Autowired
	private LogService logService;
	
	@Override
	public List<Institucion> listarInstituciones() {
		return institucionRepository.findAll();
	}
	@Override
    public Institucion guardarInstitucion(Institucion institucion) {
		logService.registrarLog("institucion", "Institución registrada");
        return institucionRepository.save(institucion);
    }
	@Override
	public Institucion buscarPorId(Long id) {
		return institucionRepository.findById(id).get();
	}

	@Override
	public Institucion actualizarInstitucion(Institucion institucion) {
		logService.registrarLog("institucion", "Institución actualizada: " + institucion.getNombre());
		return institucionRepository.save(institucion);
	}

	@Override
	public void eliminarInstitucion(Long id) {
		logService.registrarLog("institucion", "Institución eliminada (id=" + id + ")");
		institucionRepository.deleteById(id);
	}

	@Override
	public Long cantidadInstitucionesActivas() {
		return institucionRepository.countByEstado(EstadoInstitucion.ACTIVO);
	}
}
