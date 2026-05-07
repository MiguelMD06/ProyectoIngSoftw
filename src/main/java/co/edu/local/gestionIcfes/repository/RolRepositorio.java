package co.edu.local.gestionIcfes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.local.gestionIcfes.model.Rol;

public interface RolRepositorio extends JpaRepository<Rol, Long> {

	public Rol findByNombre(String nombre);
}
