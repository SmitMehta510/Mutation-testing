package com.TranquilMind.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig implements WebMvcConfigurer {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    //swagger link : http://localhost:8082/api/swagger-ui/index.html#/
    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/api/public/**",
            "/api/public/authenticate",
            "/actuator/*",
            "/swagger-ui/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Authorize -> Authorize
//                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers("/patient/register").permitAll()
                        .requestMatchers("/doctor/register").permitAll()
                        .requestMatchers("/patient/**").hasAuthority("PATIENT")
                        .requestMatchers("/doctor/**").hasAuthority("DOCTOR")
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/moderator/**").hasAuthority("MODERATOR")
                        .requestMatchers("/responder/**").hasAuthority("RESPONDER")
                        .requestMatchers("/appointment/**").hasAnyAuthority("ADMIN","PATIENT","DOCTOR")
                        .requestMatchers("/course/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/course/patient/**").hasAuthority("PATIENT")
                        .requestMatchers("/course/**").hasAnyAuthority("ADMIN","PATIENT")
                        .requestMatchers("/quiz-question/**").hasAnyAuthority("ADMIN","PATIENT","DOCTOR")
                        .requestMatchers("/quiz/**").permitAll()
                        .requestMatchers("/post/**").permitAll()
                        .requestMatchers("/question/**").permitAll()
                );
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
