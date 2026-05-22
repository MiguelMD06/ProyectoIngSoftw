package co.edu.local.gestionIcfes.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import co.edu.local.gestionIcfes.model.Rol;
import co.edu.local.gestionIcfes.model.Usuario;
import co.edu.local.gestionIcfes.repository.RolRepositorio;
import co.edu.local.gestionIcfes.repository.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;

/**
 * Inicializador de datos que se ejecuta automáticamente al arrancar la aplicación.
 * <p>
 * Garantiza que los tres roles base ({@code ROLE_ADMIN}, {@code ROLE_DOCENTE},
 * {@code ROLE_ESTUDIANTE}) existan en la tabla {@code roles}, y que el usuario
 * administrador por defecto {@code Aldemar} (contraseña {@code AFC}) esté creado
 * y con su contraseña correcta. Si la contraseña del usuario hubiese cambiado
 * manualmente en la BD, este inicializador la restablece en cada reinicio.
 * </p>
 */
@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RolRepositorio rolRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        crearRolSiNoExiste("ROLE_ADMIN");
        crearRolSiNoExiste("ROLE_DOCENTE");
        crearRolSiNoExiste("ROLE_ESTUDIANTE");

        Usuario aldemar = usuarioRepositorio.findByUsername("Aldemar");
        if (aldemar == null) {
            Rol rolAdmin = rolRepositorio.findByNombre("ROLE_ADMIN");
            Usuario admin = new Usuario("Aldemar", passwordEncoder.encode("AFC"), true, List.of(rolAdmin));
            usuarioRepositorio.save(admin);
        } else if (!passwordEncoder.matches("AFC", aldemar.getPassword())) {
            aldemar.setPassword(passwordEncoder.encode("AFC"));
            usuarioRepositorio.save(aldemar);
        }
    }

    private void crearRolSiNoExiste(String nombre) {
        if (rolRepositorio.findByNombre(nombre) == null) {
            Rol rol = new Rol();
            rol.setNombre(nombre);
            rolRepositorio.save(rol);
        }
    }
}
