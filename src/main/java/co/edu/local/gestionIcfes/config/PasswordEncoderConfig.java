package co.edu.local.gestionIcfes.config;




import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuración para el manejo de codificación de contraseñas dentro de la aplicación XFit.
 * <p>
 * Esta clase define el bean encargado de codificar contraseñas utilizando
 * {@link BCryptPasswordEncoder}, un algoritmo seguro y ampliamente utilizado
 * en aplicaciones modernas por su resistencia a ataques de fuerza bruta.
 * </p>
 *
 * <p>
 * La anotación {@link Configuration} indica que esta clase declara uno o más
 * beans que serán administrados por el contenedor de Spring.
 * </p>
 *
 * <h2>Propósito</h2>
 * <ul>
 *     <li>Proveer un {@link PasswordEncoder} seguro para el sistema de autenticación.</li>
 *     <li>Codificar contraseñas antes de almacenarlas en la base de datos.</li>
 *     <li>Comparar contraseñas ingresadas con sus versiones codificadas.</li>
 * </ul>
 *
 * @author  Miguel Medina Diaz
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class PasswordEncoderConfig {

	/**
     * Crea y expone un bean de tipo {@link PasswordEncoder} basado en el algoritmo BCrypt.
     * <p>
     * BCrypt genera un hash aleatorio y seguro, y añade un "salt" de forma automática,
     * haciendo más difícil que las contraseñas sean vulnerables ante ataques de
     * diccionario, rainbow tables o fuerza bruta.
     * </p>
     *
     * @return una instancia de {@link BCryptPasswordEncoder} utilizada para codificar contraseñas.
     */
	  @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
}
