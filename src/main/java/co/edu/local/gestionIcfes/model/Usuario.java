package co.edu.local.gestionIcfes.model;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entidad que representa las credenciales de acceso y el perfil de sesión de un usuario.
 * <p>
 * Cada {@link co.edu.local.gestionIcfes.model.Estudiante} y
 * {@link co.edu.local.gestionIcfes.model.Docente} está vinculado a un {@code Usuario}
 * a través de una relación {@code @OneToOne} en {@link Persona}. Los administradores
 * también son {@code Usuario}, pero sin entidad {@code Persona} asociada.
 * </p>
 * <ul>
 *   <li>{@code salon} indica el aula asignada ("1" o "2") dentro de la institución.</li>
 *   <li>{@code enabled = false} bloquea el acceso al sistema sin eliminar el registro.</li>
 *   <li>Los roles se cargan de forma {@code EAGER} para que Spring Security los tenga
 *       disponibles en cada petición sin una transacción activa.</li>
 * </ul>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	private Boolean enabled = true;
	
	private String salon;
	
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institucion_id")
    private Institucion institucion;

    /*
     * MUCHOS USUARIOS -> MUCHOS ROLES
     */

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuarios_roles",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Collection<Rol> roles;

	public Usuario(String username, String password, Boolean enabled, Institucion institucion,String salon, Collection<Rol> roles) {
		super();
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.institucion = institucion;
		this.salon = salon;
		this.roles = roles;
	}
	
	public Usuario(String username, String password, Boolean enabled, Collection<Rol> roles) {
		super();
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.roles = roles;
	}
    
    
	
}
