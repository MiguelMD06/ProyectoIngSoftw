package co.edu.local.gestionIcfes.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import co.edu.local.gestionIcfes.model.ResultadoSimulacro;

public interface ResultadoSimulacroService {

    ResultadoSimulacro subirResultado(String documentoIdentidad, Long simulacroId, MultipartFile archivo,
            Integer puntajeSociales, Integer puntajeNaturales, Integer puntajeMatematicas,
            Integer puntajelecturaCritica, Integer puntajeIngles, Integer puestoSalon);

    List<ResultadoSimulacro> listarResultadosPorEstudiante(String documentoIdentidad);
}
