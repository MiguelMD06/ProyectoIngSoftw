package co.edu.local.gestionIcfes.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "logs")
public class LogCambio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="origen_cambio")
	private String origenCambio;
	
	@Column(name = "descripcion")
	private String descripcionCambio;

	@Column(name = "fecha_hora")
	private LocalDateTime fechaHora;

	public LogCambio(String origenCambio, String descripcionCambio) {
		super();
		this.origenCambio = origenCambio;
		this.descripcionCambio = descripcionCambio;
		this.fechaHora = LocalDateTime.now();
	}
	
	

}
