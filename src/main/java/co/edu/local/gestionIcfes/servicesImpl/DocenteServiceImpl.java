package co.edu.local.gestionIcfes.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.local.gestionIcfes.dto.PersonaDTO;
import co.edu.local.gestionIcfes.model.Docente;
import co.edu.local.gestionIcfes.repository.DocenteRepositorio;
import co.edu.local.gestionIcfes.services.DocenteService;

@Service
public class DocenteServiceImpl implements DocenteService{
	
	 @Autowired
	    private DocenteRepositorio docenteRepository;

	    @Override
	    public List<Docente> listarDocentes() {
	        return docenteRepository.findAll();
	    }

	    @Override
	    public Docente obtenerDocentePorId(String id) {
	        return docenteRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
	    }

	    @Override
	    public Docente guardarDocente(Docente docente) {
	        return docenteRepository.save(docente);
	    }

	    @Override
	    public Docente actualizarDocente(PersonaDTO personaDTO) {
	        Docente docente = docenteRepository.findById(personaDTO.getDocumentoIdentidad())
	                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

	        docente.setPrimerNombre(personaDTO.getPrimerNombre());
	        docente.setSegundoNombre(personaDTO.getSegundoNombre());
	        docente.setPrimerApellido(personaDTO.getPrimerApellido());
	        docente.setSegundoApellido(personaDTO.getSegundoApellido());
	        docente.setCelular(personaDTO.getCelular());

	        return docenteRepository.save(docente);
	    }

	    @Override
	    public void eliminarDocente(String id) {
	        docenteRepository.deleteById(id);
	    }
}
