package co.edu.local.gestionIcfes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import co.edu.local.gestionIcfes.services.UsuarioServices;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

	@Autowired
	private UsuarioServices usuarioServices;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomAuthSuccessHandler successHandler;

	@Autowired
	private CustomAuthFailureHandler failureHandler;

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider(usuarioServices);
		auth.setPasswordEncoder(passwordEncoder);
		return auth;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authenticationProvider(authenticationProvider())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/estilos/**", "/js/**", "/img/**", "/css/**", "/assets/**", "/login")
						.permitAll()
						.requestMatchers("/registroAdmin").hasRole("ADMIN")
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/docente/**").hasAnyRole("DOCENTE")
						.requestMatchers("/estudiante/**").hasAnyRole("ESTUDIANTE")
						.anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").successHandler(successHandler)
						.failureHandler(failureHandler).permitAll())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login").permitAll())
				.exceptionHandling(exception -> exception.accessDeniedPage("/403"));

		return http.build();
	}
}
