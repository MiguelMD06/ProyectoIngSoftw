package co.edu.local.gestionIcfes.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "estudiantes")
public class Estudiante extends Persona{
	
	


	public Estudiante(Boolean activo, Institucion institucion) {
		super();
		this.activo = activo;
		this.institucion = institucion;
	}

	private Boolean activo = true;
	
	/*
     * MUCHOS ESTUDIANTES -> UNA INSTITUCION
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institucion_id")
    private Institucion institucion;


    /*
     * UN ESTUDIANTE -> MUCHAS ASISTENCIAS
     */

    @OneToMany(mappedBy = "estudiante")
    private List<Asistencia> asistencias;

    /*
     * UN ESTUDIANTE -> MUCHOS RESULTADOS
     */

    @OneToMany(mappedBy = "estudiante")
    private List<ResultadoSimulacro> resultados;
}
