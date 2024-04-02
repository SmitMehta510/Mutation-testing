package com.TranquilMind.config;

import com.TranquilMind.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig implements WebMvcConfigurer {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Authorize -> Authorize
//                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
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
                        .requestMatchers("/question/**").hasAnyAuthority("ADMIN","PATIENT","DOCTOR")
                        .requestMatchers("/quiz/**").hasAnyAuthority("ADMIN","PATIENT","DOCTOR")
//                        .requestMatchers("/superadmin/**").hasAuthority("SUPERADMIN")
                );
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(Authorize -> Authorize
//                        .requestMatchers("/user/**").permitAll()
//                        .requestMatchers("/patient/**").hasAuthority("PATIENT")
//                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
//                        .requestMatchers("/superadmin/**").hasAuthority("SUPERADMIN"));
//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000") // Replace with your allowed origin(s)
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowedHeaders("*")
//                .allowCredentials(true);
//    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*") // Allow all origins
//                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed HTTP methods
//                .allowedHeaders("*"); // Allowed headers
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
