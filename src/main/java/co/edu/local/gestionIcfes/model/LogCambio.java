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

/**
 * Entidad de auditoría que registra cada operación relevante ejecutada en el sistema.
 * <p>
 * {@code origenCambio} identifica el módulo que generó el evento: {@code "estudiante"},
 * {@code "docente"}, {@code "simulacro"}, {@code "asistencia"}, {@code "usuario"},
 * {@code "institucion"} o {@code "material"}.
 * </p>
 * <p>
 * Al usar el constructor de dos argumentos {@link #LogCambio(String, String)},
 * {@code fechaHora} se asigna automáticamente a {@code LocalDateTime.now()}, por lo que
 * nunca debe setearse manualmente.
 * El panel de administrador muestra únicamente los últimos 5 registros (ver
 * {@link co.edu.local.gestionIcfes.servicesImpl.LogServiceImpl#listarLogs()}).
 * </p>
 */
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
