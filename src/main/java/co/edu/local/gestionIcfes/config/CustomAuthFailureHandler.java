package co.edu.local.gestionIcfes.config;

import java.io.IOException;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Manejador de autenticación fallida que distingue entre credenciales incorrectas
 * y cuenta deshabilitada.
 * <ul>
 *   <li>{@link org.springframework.security.authentication.DisabledException} →
 *       redirige a {@code /login?disabled} (cuenta inactiva).</li>
 *   <li>Cualquier otro error → redirige a {@code /login?error} (credenciales inválidas).</li>
 * </ul>
 */
@Component
public class CustomAuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        if (exception instanceof DisabledException) {
            response.sendRedirect(request.getContextPath() + "/login?disabled");
        } else {
            response.sendRedirect(request.getContextPath() + "/login?error");
        }
    }
}
