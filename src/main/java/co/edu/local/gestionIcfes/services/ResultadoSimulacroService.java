package co.edu.local.gestionIcfes.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import co.edu.local.gestionIcfes.model.ResultadoSimulacro;

public interface ResultadoSimulacroService {
	
	ResultadoSimulacro subirResultado(String documentoIdentidad, Long simulacroId, MultipartFile archivo);
    List<ResultadoSimulacro> listarResultadosPorEstudiante(String documentoIdentidad);
}
