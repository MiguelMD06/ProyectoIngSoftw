package co.edu.local.gestionIcfes.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa a un estudiante matriculado en una institución.
 * <p>
 * Hereda los datos de identidad de {@link co.edu.local.gestionIcfes.model.Persona} y
 * agrega la relación con su {@link co.edu.local.gestionIcfes.model.Institucion}, sus
 * registros de {@link co.edu.local.gestionIcfes.model.Asistencia} y sus
 * {@link co.edu.local.gestionIcfes.model.ResultadoSimulacro}.
 * </p>
 * <p>
 * El campo {@code activo} indica si el estudiante está habilitado en la plataforma;
 * difiere de {@code Usuario.enabled}, que bloquea el acceso al sistema.
 * Los resultados de simulacro se eliminan en cascada si el estudiante es borrado.
 * </p>
 */
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

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResultadoSimulacro> resultados = new ArrayList<>();

}
