package co.edu.local.gestionIcfes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.local.gestionIcfes.model.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

	public Usuario findByUsername(String nombre);
}
