package sistema.pedidos.init.security;

import sistema.pedidos.init.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http
                .cors() // Habilitar CORS
                .and()
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()  // Estas rutas están abiertas
                                .anyRequest().authenticated()  // Resto de las rutas requieren autenticación JWT
                )
                .csrf(csrf -> csrf.disable())  // Deshabilitar CSRF (muy importante para las APIs REST)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // No mantener estado en las sesiones
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);  // Añadir el filtro JWT

        return http.build();
    }
}