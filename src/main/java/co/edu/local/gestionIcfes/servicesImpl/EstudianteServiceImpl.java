package co.edu.local.gestionIcfes.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.local.gestionIcfes.dto.PersonaDTO;
import co.edu.local.gestionIcfes.model.Estudiante;
import co.edu.local.gestionIcfes.repository.EstudianteRepositorio;
import co.edu.local.gestionIcfes.services.EstudianteService;
import co.edu.local.gestionIcfes.services.InstitucionService;
import co.edu.local.gestionIcfes.services.UsuarioServices;

@Service
public class EstudianteServiceImpl implements EstudianteService{

	@Autowired
	private EstudianteRepositorio estudianteRepository;
	
	@Autowired
	private UsuarioServices usuarioService;
	
	@Autowired
	private InstitucionService institucionService;
	
	@Override
	public Estudiante buscarEstudiante(String documentoIdentidad) {
		return estudianteRepository.findById(documentoIdentidad).get();
	}
	
	@Override
	public void eliminarEstudiante(String documentoIdentidad) {
		Estudiante est = buscarEstudiante(documentoIdentidad);
		estudianteRepository.deleteById(documentoIdentidad);
		usuarioService.eliminarUsuario(est.getUsuario().getId());
	}
	
	@Override
	public Estudiante actualizarEstudiante(PersonaDTO personaDTO) {
		Estudiante estudiante = buscarEstudiante(personaDTO.getDocumentoIdentidad());
		estudiante.setTipoIdentificacion(personaDTO.getTipoIdentificacion());
		estudiante.setDocumentoIdentidad(personaDTO.getDocumentoIdentidad());
		estudiante.setPrimerNombre(personaDTO.getPrimerNombre());
		estudiante.setSegundoNombre(personaDTO.getSegundoNombre());
		estudiante.setPrimerApellido(personaDTO.getPrimerApellido());
		estudiante.setSegundoApellido(personaDTO.getSegundoApellido());
		estudiante.setCelular(personaDTO.getCelular());
		estudiante.setInstitucion(institucionService.buscarPorId(personaDTO.getInstitucion()));
		usuarioService.editarSalonInstitucion(personaDTO.getSalon(), institucionService.buscarPorId(personaDTO.getInstitucion()), estudiante.getUsuario().getId());
		return estudianteRepository.save(estudiante);
	}
	
	@Override
	public PersonaDTO convertirEstudiantePersona(Estudiante estudiante) {
		PersonaDTO persona = new PersonaDTO();
		persona.setDocumentoIdentidad(estudiante.getDocumentoIdentidad());
		persona.setCelular(estudiante.getCelular());
		persona.setInstitucion(estudiante.getInstitucion().getId());
		persona.setPrimerApellido(estudiante.getPrimerApellido());
		persona.setSegundoApellido(estudiante.getSegundoApellido());
		persona.setPrimerNombre(estudiante.getPrimerNombre());
		persona.setSegundoNombre(estudiante.getSegundoNombre());
		persona.setSalon(estudiante.getUsuario().getSalon());
		persona.setTipoIdentificacion(estudiante.getTipoIdentificacion());
		return persona;
	}
}
