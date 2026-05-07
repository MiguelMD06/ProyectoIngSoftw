package co.edu.local.gestionIcfes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.local.gestionIcfes.model.Estudiante;

public interface EstudianteRepositorio extends JpaRepository<Estudiante, String> {

}
