package co.edu.local.gestionIcfes.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que almacena el resultado individual de un estudiante en un simulacro ICFES.
 * <p>
 * Contiene el PDF con el resultado completo ({@code datos} como {@code BYTEA}) junto con
 * los puntajes desglosados por área ({@code puntajeSociales}, {@code puntajeNaturales},
 * {@code puntajeMatematicas}, {@code puntajelecturaCritica}, {@code puntajeIngles}) y el
 * puntaje global (suma de los anteriores calculada en
 * {@link co.edu.local.gestionIcfes.servicesImpl.ResultadoSimulacroServiceImpl}).
 * </p>
 * <p>
 * {@code puestoSalon} indica la posición del estudiante dentro de su salón en ese simulacro.
 * Los métodos de descarga requieren {@code @Transactional(readOnly = true)} para que
 * Hibernate mantenga la sesión abierta al leer el campo {@code datos}.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "resultados_simulacro")
public class ResultadoSimulacro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreArchivo;
    private String tipoArchivo;

    @Column(name = "datos", columnDefinition = "BYTEA")
    private byte[] datos;

    private Integer puntajeSociales;
    private Integer puntajeNaturales;
    private Integer puntajeMatematicas;
    private Integer puntajelecturaCritica;
    private Integer puntajeIngles;
    private Integer puntajeGlobal;
    private Integer puestoSalon;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "simulacro_id")
    private Simulacro simulacro;
}


