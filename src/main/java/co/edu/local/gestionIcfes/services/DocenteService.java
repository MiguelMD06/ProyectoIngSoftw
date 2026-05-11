package co.edu.local.gestionIcfes.services;

import java.util.List;

import co.edu.local.gestionIcfes.dto.PersonaDTO;
import co.edu.local.gestionIcfes.model.Docente;

public interface DocenteService {

	 	public List<Docente> listarDocentes();
	    public Docente obtenerDocentePorId(String documentoIdentidad);
	    public Docente guardarDocente(Docente docente);
	    public Docente actualizarDocente(PersonaDTO personaDTO);
	    public void eliminarDocente(String documentoIdentidad);
}
