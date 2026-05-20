package co.edu.local.gestionIcfes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.local.gestionIcfes.model.MaterialEstudio;

public interface MaterialEstudioRepositorio extends JpaRepository<MaterialEstudio, Long> {

    List<MaterialEstudio> findByInstitucionIdOrderBySemanaDesc(Long institucionId);

    List<MaterialEstudio> findByInstitucionIdAndSemana(Long institucionId, Integer semana);
}
