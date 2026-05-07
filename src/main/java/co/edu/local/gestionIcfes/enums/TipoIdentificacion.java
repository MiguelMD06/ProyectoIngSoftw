package co.edu.local.gestionIcfes.enums;


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