package co.edu.local.gestionIcfes.services;

import java.util.List;

import co.edu.local.gestionIcfes.model.Rol;

public interface RolServices {

	public List<Rol> listarRoles();
	public Rol encontrarRol(String nombre);
	public Rol encontrarRol(Long id);
}
