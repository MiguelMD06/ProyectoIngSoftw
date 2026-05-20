package co.edu.local.gestionIcfes.services;

import java.util.List;

import co.edu.local.gestionIcfes.model.Institucion;

public interface InstitucionService {
	
	public List<Institucion> listarInstituciones();
    Institucion guardarInstitucion(Institucion institucion);
	public Institucion buscarPorId(Long id);
	public Institucion actualizarInstitucion(Institucion institucion);
	public void eliminarInstitucion(Long id);
	public Long cantidadInstitucionesActivas();
}
