package co.edu.local.gestionIcfes.services;

import co.edu.local.gestionIcfes.dto.PersonaDTO;
import co.edu.local.gestionIcfes.model.Estudiante;

public interface EstudianteService {
	
	public void eliminarEstudiante(String documentoIdentidad);
	public Estudiante buscarEstudiante(String documentoIdentidad);
	public PersonaDTO convertirEstudiantePersona(Estudiante estudiante);
	public Estudiante actualizarEstudiante(PersonaDTO personaDTO);
}
