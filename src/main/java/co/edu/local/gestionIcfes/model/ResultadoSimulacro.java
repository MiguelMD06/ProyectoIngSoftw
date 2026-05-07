package co.edu.local.gestionIcfes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "resultado_simulacros")
public class ResultadoSimulacro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Double puntajeGlobal;

	private Double matematicas;

	private Double lecturaCritica;

	private Double cienciasNaturales;

	private Double sociales;

	private Double ingles;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    /*
     * MUCHOS RESULTADOS -> UN SIMULACRO
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "simulacro_id")
    private Simulacro simulacro;
}
