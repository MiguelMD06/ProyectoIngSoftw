package co.edu.local.gestionIcfes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.local.gestionIcfes.model.Simulacro;

public interface SimulacroRepositorio extends JpaRepository<Simulacro, Long> {
	Optional<Simulacro> findByTitulo(String titulo);

}
