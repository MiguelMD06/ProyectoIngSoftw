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

        if (usuarioRepositorio.findByUsername("Aldemar") == null) {
            Rol rolAdmin = rolRepositorio.findByNombre("ROLE_ADMIN");
            Usuario admin = new Usuario("Aldemar", passwordEncoder.encode("AFC"), true, List.of(rolAdmin));
            usuarioRepositorio.save(admin);
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
