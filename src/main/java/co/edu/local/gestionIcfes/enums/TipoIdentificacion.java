package co.edu.local.gestionIcfes.enums;

/**
 * Tipos de documento de identidad reconocidos por el sistema, basados en la
 * clasificación oficial colombiana.
 * <p>
 * Se almacena como {@code STRING} en la base de datos mediante
 * {@code @Enumerated(EnumType.STRING)} en la entidad {@link co.edu.local.gestionIcfes.model.Persona}.
 * Cada constante expone una descripción legible a través de {@link #getDescripcion()}.
 * </p>
 */
public enum TipoIdentificacion {

	RI("Registro Civil de Nacimiento"),
	TI("Tarjeta de Identidad"),
	CC("Cedula de Ciudadanía"),
	CE("Cedula de Extranjería"),
	PP("Pasaporte"),
	PEP("Permiso especial de permanencia");
	
	private String descripcion;
	
	private TipoIdentificacion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
}