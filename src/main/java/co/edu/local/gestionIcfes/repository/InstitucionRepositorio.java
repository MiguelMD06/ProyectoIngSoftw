package co.edu.local.gestionIcfes.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.local.gestionIcfes.enums.EstadoInstitucion;
import co.edu.local.gestionIcfes.model.Institucion;

public interface InstitucionRepositorio extends JpaRepository<Institucion, Long> {

    List<Institucion> findByFechaFinalLessThanEqualAndFechaFinalIsNotNull(LocalDate fecha);
    Long countByEstado(EstadoInstitucion estado);
}
