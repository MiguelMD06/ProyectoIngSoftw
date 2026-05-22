package co.edu.local.gestionIcfes.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que describe un simulacro de examen ICFES dentro de una institución.
 * <p>
 * Almacena únicamente los metadatos del simulacro (título, descripción, nombre del PDF
 * de enunciado y fecha de aplicación). El contenido binario de los resultados individuales
 * de cada estudiante se guarda en {@link co.edu.local.gestionIcfes.model.ResultadoSimulacro}.
 * </p>
 * <p>
 * Al eliminar un simulacro, {@link co.edu.local.gestionIcfes.servicesImpl.SimulacroServiceImpl}
 * borra primero todos sus {@code ResultadoSimulacro} asociados para respetar la integridad
 * referencial.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "simulacros")
public class Simulacro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String titulo;
	
	private String descripcion;
	
	private String archivoPdf;
	
	private LocalDate fechaAplicacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institucion_id")
    private Institucion institucion;

    /*
     * UN SIMULACRO -> MUCHOS RESULTADOS
     */

    @OneToMany(mappedBy = "simulacro")
    private List<ResultadoSimulacro> resultados;
}
