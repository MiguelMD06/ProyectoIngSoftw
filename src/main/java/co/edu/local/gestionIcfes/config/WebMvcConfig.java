package co.edu.local.gestionIcfes.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * Configuración de Spring MVC para la internacionalización (i18n) de la aplicación.
 * <p>
 * Registra un {@link org.springframework.web.servlet.i18n.SessionLocaleResolver} con
 * español como idioma predeterminado, y un
 * {@link org.springframework.web.servlet.i18n.LocaleChangeInterceptor} que cambia el
 * idioma de la sesión cuando se pasa el parámetro {@code ?lang=} en cualquier URL.
 * Idiomas soportados: {@code es} (español), {@code en} (inglés), {@code fr} (francés),
 * {@code it} (italiano).
 * </p>
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(new Locale("es"));
        return resolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
