package co.edu.local.gestionIcfes.services;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import co.edu.local.gestionIcfes.model.ResultadoSimulacro;
import co.edu.local.gestionIcfes.model.Simulacro;

/**
 * Contrato del servicio de gestión de simulacros ICFES.
 * <p>
 * Diferencia entre {@code crearSimulacro} (metadatos del examen) y {@code subirResultado}
 * (PDF con el resultado de un estudiante). La eliminación de un simulacro borra
 * en cascada todos sus {@link co.edu.local.gestionIcfes.model.ResultadoSimulacro}.
 * </p>
 */
public interface SimulacroService {

    /**
     * Crea un nuevo simulacro con sus metadatos.
     *
     * @param simulacro entidad con título, descripción, fecha de aplicación e institución.
     * @return el simulacro persistido.
     */
    Simulacro crearSimulacro(Simulacro simulacro);

    /**
     * Sube el PDF de resultado de un estudiante en un simulacro (sin puntajes desglosados).
     * Para subir con puntajes usar {@link ResultadoSimulacroService}.
     *
     * @param documentoIdentidad documento del estudiante.
     * @param simulacroId        identificador del simulacro.
     * @param archivo            archivo PDF.
     * @return el resultado persistido.
     */
    ResultadoSimulacro subirResultado(String documentoIdentidad, Long simulacroId, MultipartFile archivo);

    /** @return lista de todos los simulacros registrados. */
    List<Simulacro> listarSimulacros();

    /**
     * Obtiene un simulacro por su ID.
     *
     * @param simulacroId identificador del simulacro.
     * @return el simulacro encontrado.
     * @throws RuntimeException si no existe.
     */
    Simulacro obtenerSimulacroPorId(Long simulacroId);

    /**
     * Actualiza los metadatos de un simulacro existente.
     *
     * @param simulacro entidad con los datos actualizados.
     * @return el simulacro actualizado.
     */
    Simulacro actualizarSimulacro(Simulacro simulacro);

    /**
     * Elimina un simulacro y todos sus resultados asociados.
     *
     * @param id identificador del simulacro.
     */
    void eliminarSimulacro(Long id);

    /** @return total de simulacros registrados en el sistema. */
    public Long cantidadSimulacros();
}