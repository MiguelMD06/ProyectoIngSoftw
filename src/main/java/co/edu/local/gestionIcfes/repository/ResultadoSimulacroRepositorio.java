package co.edu.local.gestionIcfes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.local.gestionIcfes.model.ResultadoSimulacro;

public interface ResultadoSimulacroRepositorio extends JpaRepository<ResultadoSimulacro, Long> {

    List<ResultadoSimulacro> findByEstudianteDocumentoIdentidad(String documentoIdentidad);

    Optional<ResultadoSimulacro> findByEstudianteDocumentoIdentidadAndSimulacroId(
            String documentoIdentidad, Long simulacroId);

    void deleteByEstudianteInstitucionId(Long institucionId);

    void deleteBySimulacroInstitucionId(Long institucionId);

    void deleteBySimulacroId(Long simulacroId);
}
