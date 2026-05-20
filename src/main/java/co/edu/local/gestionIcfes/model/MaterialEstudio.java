package co.edu.local.gestionIcfes.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "materiales_estudio")
public class MaterialEstudio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descripcion;

    private Integer semana;

    private LocalDate fechaSubida;

    private String nombreArchivo;

    private String tipoArchivo;

    @Column(name = "archivo", columnDefinition = "BYTEA")
    private byte[] archivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institucion_id")
    private Institucion institucion;
}
