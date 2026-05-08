package co.edu.local.gestionIcfes.dto;

import java.util.Collection;

import co.edu.local.gestionIcfes.model.Institucion;
import co.edu.local.gestionIcfes.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

	private Long id;
	
	
	private String username;
	
	private String password;
	
	private Boolean enabled;
	
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
