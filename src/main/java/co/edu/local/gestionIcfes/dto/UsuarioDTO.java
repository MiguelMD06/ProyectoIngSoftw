package co.edu.local.gestionIcfes.dto;

import java.util.Collection;

import co.edu.local.gestionIcfes.model.Institucion;
import co.edu.local.gestionIcfes.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de formulario para el registro y visualización de usuarios administrativos.
 * <p>
 * Se utiliza principalmente en el flujo de creación de administradores
 * ({@code GET/POST /registroAdmin}) y en la pantalla de configuración de usuarios.
 * {@code institucionId} se emplea al crear el usuario para resolver la
 * {@link co.edu.local.gestionIcfes.model.Institucion} desde el repositorio;
 * {@code institucion} expone el objeto completo en las vistas que lo necesitan.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

	private Long id;

	private String username;

	private String password;

	private Boolean enabled;

	private Long institucionId;

	private Institucion institucion;

    private Rol roles;

	public UsuarioDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public UsuarioDTO(String username, String password, Institucion institucion, Rol roles) {
		super();
		this.username = username;
		this.password = password;
		this.institucion = institucion;
		this.roles = roles;
	}
	
	
    
    
}
