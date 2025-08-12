package keyin.datastructure.datastructure_sprint_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //Disable Cross-Site Request Forgery
                .csrf(csrf -> csrf.disable())

                //Define authorization rules for HTTP requests
                .authorizeHttpRequests(authz -> authz
                        //Allow anyone to access register and login endpoints
                        .requestMatchers("/api/auth/**").permitAll()
                        //Require authentication for any other request)
                        .anyRequest().authenticated()
                )
                //Use default CORS settings
                .cors(withDefaults())

                //Configure session management to be stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
