package co.edu.local.gestionIcfes.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.local.gestionIcfes.dto.PersonaDTO;
import co.edu.local.gestionIcfes.model.Docente;
import co.edu.local.gestionIcfes.model.Estudiante;
import co.edu.local.gestionIcfes.repository.DocenteRepositorio;
import co.edu.local.gestionIcfes.services.DocenteService;
import co.edu.local.gestionIcfes.services.LogService;
import co.edu.local.gestionIcfes.services.UsuarioServices;

@Service
public class DocenteServiceImpl implements DocenteService{
	
	 @Autowired
	 private DocenteRepositorio docenteRepository;
	 
	 @Autowired
	 private UsuarioServices usuarioService;
	 
	 @Autowired
	 private LogService logService;

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
	        logService.registrarLog("docente", "Docente registrado " + docente.getPrimerNombre());
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
	        logService.registrarLog("docente", "Docente actualizado " + docente.getPrimerNombre());
	        return docenteRepository.save(docente);
	    }

	    @Override
	    public void eliminarDocente(String id) {
	    	Docente docente = obtenerDocentePorId(id);
	        docenteRepository.deleteById(id);
	        usuarioService.eliminarUsuario(docente.getUsuario().getId());
	        logService.registrarLog("docente", "Docente eliminado " + docente.getPrimerNombre());
	    }
	    
	    @Override
	    public Docente buscarPorUsername(String username) {
	        return docenteRepository.findByUsuarioUsername(username)
	                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
	    }

	    @Override
		public PersonaDTO convertirDocentePersona(Docente docente) {
			PersonaDTO persona = new PersonaDTO();
			persona.setDocumentoIdentidad(docente.getDocumentoIdentidad());
			persona.setCelular(docente.getCelular());
			persona.setInstitucion(docente.getInstitucion().getId());
			persona.setPrimerApellido(docente.getPrimerApellido());
			persona.setSegundoApellido(docente.getSegundoApellido());
			persona.setPrimerNombre(docente.getPrimerNombre());
			persona.setSegundoNombre(docente.getSegundoNombre());
			persona.setSalon(docente.getUsuario().getSalon());
			persona.setTipoIdentificacion(docente.getTipoIdentificacion());
			persona.setRol(docente.getUsuario().getRoles().stream()
				    .findFirst()
				    .orElse(null).getId());
			return persona;
		}
}
