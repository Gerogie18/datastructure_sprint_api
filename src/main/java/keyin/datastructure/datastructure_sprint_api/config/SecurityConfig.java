package keyin.datastructure.datastructure_sprint_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable Cross-Site Request Forgery (CSRF)
                .csrf(csrf -> csrf.disable())

                // Define authorization rules
                .authorizeHttpRequests(authz -> authz
                        // Explicitly permit all incoming requests
                        .requestMatchers("/**").permitAll()
                );

        return http.build();
    }
}