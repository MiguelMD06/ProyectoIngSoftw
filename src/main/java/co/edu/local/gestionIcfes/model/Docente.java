package co.edu.local.gestionIcfes.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    /*
     * MUCHOS DOCENTES -> MUCHOS SALONES
     */

    @ManyToMany(mappedBy = "docentes")
    private List<Salon> salones;
	
}
