package com.enigov.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Value("${enigov.app.allowedOrigins}")
    private List<String> allowedOrigins;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // ── Preflight ──
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // ── Public (no auth) ──
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers("/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/ws/**").permitAll()

                        // ── ROLE_DELEGUE: full access to everything ──
                        // (delegue can also hit the student endpoints below, so order doesn't matter)

                        // ── ROLE_ETUDIANT: read-only on content endpoints ──
                        .requestMatchers(HttpMethod.GET, "/api/announcements/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/events/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/regulations/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/polls/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/decisions/**").authenticated()

                        // ── ROLE_ETUDIANT: submit complaints ──
                        .requestMatchers(HttpMethod.POST, "/api/complaints").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/complaints/my").authenticated()

                        // ── ROLE_ETUDIANT: vote on polls ──
                        .requestMatchers(HttpMethod.POST, "/api/polls/*/vote").authenticated()

                        // ── ROLE_ETUDIANT: messaging with delegates ──
                        .requestMatchers("/api/messages/**").authenticated()

                        // ── ROLE_ETUDIANT: user profile (own) ──
                        .requestMatchers("/api/users/me").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/users/profile").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/users/photo").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/users/password").authenticated()

                        // ── Write endpoints (create/update/delete) → DELEGUE only ──
                        .requestMatchers(HttpMethod.POST, "/api/announcements/**").hasRole("DELEGUE")
                        .requestMatchers(HttpMethod.PUT, "/api/announcements/**").hasRole("DELEGUE")
                        .requestMatchers(HttpMethod.DELETE, "/api/announcements/**").hasRole("DELEGUE")

                        .requestMatchers(HttpMethod.POST, "/api/events/**").hasRole("DELEGUE")
                        .requestMatchers(HttpMethod.PUT, "/api/events/**").hasRole("DELEGUE")
                        .requestMatchers(HttpMethod.DELETE, "/api/events/**").hasRole("DELEGUE")

                        .requestMatchers(HttpMethod.POST, "/api/polls").hasRole("DELEGUE")
                        .requestMatchers(HttpMethod.PUT, "/api/polls/**").hasRole("DELEGUE")
                        .requestMatchers(HttpMethod.DELETE, "/api/polls/**").hasRole("DELEGUE")

                        .requestMatchers(HttpMethod.POST, "/api/decisions/**").hasRole("DELEGUE")
                        .requestMatchers(HttpMethod.PUT, "/api/decisions/**").hasRole("DELEGUE")
                        .requestMatchers(HttpMethod.DELETE, "/api/decisions/**").hasRole("DELEGUE")

                        .requestMatchers(HttpMethod.GET, "/api/complaints").hasRole("DELEGUE")
                        .requestMatchers(HttpMethod.PUT, "/api/complaints/**").hasRole("DELEGUE")
                        .requestMatchers(HttpMethod.DELETE, "/api/complaints/**").hasRole("DELEGUE")

                        // ── Everything else: must be authenticated ──
                        .anyRequest().authenticated());

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
