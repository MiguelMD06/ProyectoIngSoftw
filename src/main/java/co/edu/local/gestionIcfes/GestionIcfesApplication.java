package co.edu.local.gestionIcfes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Punto de entrada de la aplicación GestionIcfes.
 * <p>
 * {@code @EnableScheduling} activa el soporte de tareas programadas de Spring,
 * requerido por {@link co.edu.local.gestionIcfes.servicesImpl.CierreInstitucionScheduler}
 * para el cierre automático diario de instituciones vencidas.
 * </p>
 */
@SpringBootApplication
@EnableScheduling
public class GestionIcfesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionIcfesApplication.class, args);
	}

}
