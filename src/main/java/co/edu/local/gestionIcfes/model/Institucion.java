package co.edu.local.gestionIcfes.model;

import java.time.LocalDate;
import java.util.List;

import co.edu.local.gestionIcfes.enums.EstadoInstitucion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entidad que representa una institución educativa preparatoria para el ICFES.
 * <p>
 * Actúa como agregado raíz: agrupa a sus {@link co.edu.local.gestionIcfes.model.Estudiante}s,
 * {@link co.edu.local.gestionIcfes.model.Docente}s, {@link co.edu.local.gestionIcfes.model.Usuario}s
 * administrativos y {@link co.edu.local.gestionIcfes.model.Simulacro}s.
 * </p>
 * <p>
 * {@code fechaInicio} y {@code fechaFinal} se usan en los tres paneles de usuario para
 * calcular el porcentaje de progreso del curso. Cuando {@code fechaFinal} se alcanza,
 * {@link co.edu.local.gestionIcfes.servicesImpl.CierreInstitucionScheduler} deshabilita
 * automáticamente todos los usuarios asociados.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "instituciones")
public class Institucion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "inst_nombre",nullable = false)
	private String nombre;
	
	@Column(name = "inst_direccion", nullable = false)
	private String direccion;
	
	private LocalDate fechaInicio;
	
	private LocalDate fechaFinal;
	
	@Enumerated(EnumType.STRING)
	private EstadoInstitucion estado;
	
	//Relaciones
	
	@ToString.Exclude
	@OneToMany(mappedBy = "institucion")
	private List<Usuario> usuarios;
	
	@OneToMany(mappedBy = "institucion")
    private List<Estudiante> estudiantes;

    @OneToMany(mappedBy = "institucion")
    private List<Docente> docentes;

    @OneToMany(mappedBy = "institucion")
    private List<Simulacro> simulacros;
	
}
