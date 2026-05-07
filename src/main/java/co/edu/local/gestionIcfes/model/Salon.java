package co.edu.local.gestionIcfes.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "salones")
public class Salon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	private LocalDate fechaInicio;
	
	private LocalDate fechaFin;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institucion_id")
    private Institucion institucion;

    /*
     * UN SALON -> MUCHOS ESTUDIANTES
     */

    @OneToMany(mappedBy = "salon")
    private List<Estudiante> estudiantes;

    /*
     * MUCHOS DOCENTES -> MUCHOS SALONES
     */

    @ManyToMany
    @JoinTable(
        name = "docentes_salones",
        joinColumns = @JoinColumn(name = "salon_id"),
        inverseJoinColumns = @JoinColumn(name = "docente_id")
    )
    private List<Docente> docentes;

}
