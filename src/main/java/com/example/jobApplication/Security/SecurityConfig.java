package com.example.jobApplication.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
            CorsConfigurationSource corsConfigurationSource) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // ================= PUBLIC ENDPOINTS =================
                        .requestMatchers(HttpMethod.POST, "/users/create").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/companies/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/Job/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/reviews/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/recruiters/jobs/**").permitAll()

                        // ================= AUTHENTICATED USER ENDPOINTS =================
                        .requestMatchers(HttpMethod.GET, "/users/login").authenticated()

                        // USER can create initial profiles
                        .requestMatchers(HttpMethod.POST, "/applicants/").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/companies/").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/recruiters/").hasRole("USER")

                        // ================= APPLICANT ENDPOINTS =================
                        .requestMatchers(HttpMethod.GET, "/applicants/**").hasAnyRole("APPLICANT", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/applicants/**").hasAnyRole("APPLICANT", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/applicants/**").hasAnyRole("APPLICANT", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/upload/resume").hasAnyRole("APPLICANT", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/upload/resume").hasAnyRole("APPLICANT", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/upload/resume").hasAnyRole("APPLICANT", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/upload/resume").hasAnyRole("APPLICANT", "ADMIN")

                        // ================= COMPANY ENDPOINTS =================
                        .requestMatchers(HttpMethod.PUT, "/companies/**").hasAnyRole("COMPANY", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/companies/**").hasAnyRole("COMPANY", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/companies/**").hasAnyRole("COMPANY", "ADMIN")

                        // ================= RECRUITER ENDPOINTS =================
                        .requestMatchers(HttpMethod.GET, "/recruiters/").hasAnyRole("COMPANY", "RECRUITER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/recruiters/**").hasAnyRole("RECRUITER", "COMPANY", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/recruiters/**").hasAnyRole("RECRUITER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/recruiters/**").hasAnyRole("RECRUITER", "ADMIN")

                        // ================= JOB ENDPOINTS =================
                        .requestMatchers(HttpMethod.POST, "/Job/**").hasAnyRole("RECRUITER", "COMPANY", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/Job/**").hasAnyRole("RECRUITER", "COMPANY", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/Job/**").hasAnyRole("RECRUITER", "COMPANY", "ADMIN")

                        // ================= APPLICATION ENDPOINTS =================
                        .requestMatchers(HttpMethod.GET, "/applications/recruiter/**")
                        .hasAnyRole("RECRUITER", "COMPANY", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/applications/applicant/**").hasAnyRole("APPLICANT", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/applications/**")
                        .hasAnyRole("APPLICANT", "RECRUITER", "COMPANY", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/applications/**").hasAnyRole("APPLICANT", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/applications/**")
                        .hasAnyRole("APPLICANT", "RECRUITER", "COMPANY", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/applications/**")
                        .hasAnyRole("APPLICANT", "RECRUITER", "ADMIN")

                        // ================= REVIEW ENDPOINTS =================
                        .requestMatchers(HttpMethod.POST, "/reviews/**").hasAnyRole("APPLICANT", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/reviews/**").hasAnyRole("APPLICANT", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/reviews/**").hasAnyRole("APPLICANT", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/reviews/**").permitAll()

                        // ================= ADMIN ENDPOINTS =================
                        .requestMatchers("/users/**").hasRole("ADMIN")

                        // ================= FALLBACK =================
                        .anyRequest().authenticated())

                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> form.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}