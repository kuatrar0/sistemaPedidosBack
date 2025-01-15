package sistema.pedidos.init.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
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