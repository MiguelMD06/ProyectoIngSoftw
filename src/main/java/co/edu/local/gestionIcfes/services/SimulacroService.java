package co.edu.local.gestionIcfes.services;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import co.edu.local.gestionIcfes.model.ResultadoSimulacro;
import co.edu.local.gestionIcfes.model.Simulacro;

public interface SimulacroService {
    Simulacro crearSimulacro(Simulacro simulacro);
    ResultadoSimulacro subirResultado(String documentoIdentidad, Long simulacroId, MultipartFile archivo);
    List<Simulacro> listarSimulacros();
    Simulacro obtenerSimulacroPorId(Long simulacroId);
    Simulacro actualizarSimulacro(Simulacro simulacro);
    void eliminarSimulacro(Long id);
    public Long cantidadSimulacros();
}