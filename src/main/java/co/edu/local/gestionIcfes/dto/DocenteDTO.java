package co.edu.local.gestionIcfes.dto;

import java.util.List;

import co.edu.local.gestionIcfes.model.Institucion;
import co.edu.local.gestionIcfes.model.Persona;
import co.edu.local.gestionIcfes.model.Rol;
import co.edu.local.gestionIcfes.model.Salon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocenteDTO extends Persona {
	private String especialidad;
    private Institucion institucion;
    private List<Salon> salones;

}
