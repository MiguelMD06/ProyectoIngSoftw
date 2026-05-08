package co.edu.local.gestionIcfes.services;

import java.util.List;

import co.edu.local.gestionIcfes.model.Institucion;

public interface InstitucionService {
	
	public List<Institucion> listarInstituciones();
	public Institucion buscarPorId(Long id);
}
