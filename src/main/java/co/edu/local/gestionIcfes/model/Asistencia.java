package co.edu.local.gestionIcfes.model;

import java.time.LocalDate;

import co.edu.local.gestionIcfes.enums.EstadoAsistencia;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que registra la asistencia de un estudiante a una clase en una fecha determinada.
 * <p>
 * Existe un máximo de un registro por estudiante por día; si ya existe uno para la misma
 * fecha, {@link co.edu.local.gestionIcfes.servicesImpl.AsistenciaServiceImpl#registrarOActualizar}
 * actualiza su estado en lugar de crear un duplicado.
 * El estado posible se define en {@link co.edu.local.gestionIcfes.enums.EstadoAsistencia}.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "asistencias")
public class Asistencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate fecha;

	@Enumerated(EnumType.STRING)
    private EstadoAsistencia estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;
}
