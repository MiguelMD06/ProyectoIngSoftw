package co.edu.local.gestionIcfes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entidad que representa un rol de seguridad dentro del sistema.
 * <p>
 * Los roles son sembrados automáticamente en el arranque por
 * {@link co.edu.local.gestionIcfes.config.DataInitializer}: {@code ROLE_ADMIN},
 * {@code ROLE_DOCENTE} y {@code ROLE_ESTUDIANTE}. Se asocian a
 * {@link co.edu.local.gestionIcfes.model.Usuario} mediante la tabla intermedia
 * {@code usuarios_roles} con una relación {@code @ManyToMany}.
 * </p>
 */
@Data
@Entity
@Table(name = "roles")
public class Rol {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String nombre;
}
