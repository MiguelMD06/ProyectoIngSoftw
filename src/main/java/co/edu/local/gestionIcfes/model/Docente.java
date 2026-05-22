package co.edu.local.gestionIcfes.model;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa a un docente vinculado a una institución.
 * <p>
 * Extiende {@link co.edu.local.gestionIcfes.model.Persona} con el campo {@code especialidad},
 * que describe el área de conocimiento que imparte. Cada docente está asignado a una
 * {@link co.edu.local.gestionIcfes.model.Institucion} y tiene acceso al sistema a través
 * de su {@link co.edu.local.gestionIcfes.model.Usuario} (heredado de {@code Persona}).
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "docentes")
public class Docente extends Persona{

	private String especialidad;
	
	/*
     * MUCHOS DOCENTES -> UNA INSTITUCION
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institucion_id")
    private Institucion institucion;
    
    

	
}
