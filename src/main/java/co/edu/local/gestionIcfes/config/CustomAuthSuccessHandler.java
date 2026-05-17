package co.edu.local.gestionIcfes.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String redirectUrl = "/login?error"; // fallback si no coincide ningún rol

        for (GrantedAuthority authority : authorities) {
            switch (authority.getAuthority()) {
                case "ROLE_ADMIN"      -> redirectUrl = "/admin/pAdmin";
                case "ROLE_DOCENTE"    -> redirectUrl = "/docente/pDocente";
                case "ROLE_ESTUDIANTE" -> redirectUrl = "/estudiante/pEstudiante";
            }
        }

        response.sendRedirect(request.getContextPath() + redirectUrl);

	}

}
