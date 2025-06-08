package v1.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import v1.Security.JWT.JwtAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //clase de configuration metodos con anotación bean
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // medida de seguridad por defecto que se utiliza para agregar a los POST una autenticacion basada en tokens csrf valido
                .authorizeHttpRequests( authRequest ->
                        authRequest
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/api/v1/fotografias/All/**").permitAll()
                                .requestMatchers("/concurso").permitAll()
                                .requestMatchers("/votos").permitAll()
                                .requestMatchers("/votos/**").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManager ->
                        sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
