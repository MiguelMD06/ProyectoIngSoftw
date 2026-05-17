package co.edu.local.gestionIcfes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.local.gestionIcfes.model.Docente;

public interface DocenteRepositorio extends JpaRepository<Docente, String> {

    Optional<Docente> findByUsuarioUsername(String username);
}
