package co.edu.local.gestionIcfes.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import co.edu.local.gestionIcfes.model.ResultadoSimulacro;

/**
 * Contrato del servicio para subir y consultar resultados detallados de simulacros.
 * <p>
 * A diferencia de {@link SimulacroService#subirResultado}, este servicio recibe los
 * puntajes por área y calcula el puntaje global como su suma antes de persistir.
 * </p>
 */
public interface ResultadoSimulacroService {

    /**
     * Sube el PDF con los puntajes desglosados del resultado de un estudiante.
     * El {@code puntajeGlobal} se calcula internamente sumando los cinco puntajes de área.
     *
     * @param documentoIdentidad    documento del estudiante.
     * @param simulacroId           identificador del simulacro.
     * @param archivo               PDF con el resultado.
     * @param puntajeSociales       puntaje en ciencias sociales.
     * @param puntajeNaturales      puntaje en ciencias naturales.
     * @param puntajeMatematicas    puntaje en matemáticas.
     * @param puntajelecturaCritica puntaje en lectura crítica.
     * @param puntajeIngles         puntaje en inglés.
     * @param puestoSalon           posición del estudiante en su salón.
     * @return el resultado persistido con el puntaje global calculado.
     */
    ResultadoSimulacro subirResultado(String documentoIdentidad, Long simulacroId, MultipartFile archivo,
            Integer puntajeSociales, Integer puntajeNaturales, Integer puntajeMatematicas,
            Integer puntajelecturaCritica, Integer puntajeIngles, Integer puestoSalon);

    /**
     * Lista todos los resultados de simulacro de un estudiante.
     *
     * @param documentoIdentidad documento del estudiante.
     * @return lista de resultados del estudiante.
     */
    List<ResultadoSimulacro> listarResultadosPorEstudiante(String documentoIdentidad);
}
