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

                        // ================= PUBLIC =================
                        .requestMatchers(HttpMethod.POST, "/users/create").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/companies/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/Job/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/reviews/**").permitAll()
                        .requestMatchers("/recruiters/jobs/**").permitAll()

                        // ================= USER (MANDATORY FIRST ROLE) =================
                        // .requestMatchers(HttpMethod.POST, "/users/upgrade-role")
                        // .hasRole("USER")

                        // USER can now create profile
                        .requestMatchers(HttpMethod.POST, "/applicants/")
                        .hasRole("USER")

                        .requestMatchers(HttpMethod.POST, "/companies/")
                        .hasRole("USER")

                        .requestMatchers(HttpMethod.POST, "/recruiters/")
                        .hasRole("USER")

                        // ================= APPLICANT =================
                        .requestMatchers("/applicants/**").hasAnyRole("APPLICANT", "ADMIN")

                        // ================= APPLICATION =================
                        .requestMatchers("/applications/applicant/**").hasAnyRole("APPLICANT", "ADMIN")
                        .requestMatchers("/applications/recruiter/**").hasAnyRole("RECRUITER", "ADMIN")
                        .requestMatchers("/applications/**").hasAnyRole("APPLICANT", "RECRUITER", "ADMIN")

                        // ================= COMPANY =================
                        .requestMatchers(HttpMethod.PUT, "/companies/**").hasAnyRole("COMPANY", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/companies/**").hasAnyRole("COMPANY", "ADMIN")

                        // ================= JOB =================
                        .requestMatchers(HttpMethod.POST, "/Job/Jobs/**").hasAnyRole("RECRUITER", "COMPANY", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/Job/Jobs/**").hasAnyRole("RECRUITER", "COMPANY", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/Job/Jobs/**").hasAnyRole("RECRUITER", "COMPANY", "ADMIN")

                        // ================= RECRUITER =================
                        .requestMatchers(HttpMethod.GET, "/recruiters/").hasAnyRole("COMPANY", "ADMIN")
                        .requestMatchers("/recruiters/recruiter/**").hasAnyRole("COMPANY", "ADMIN")
                        .requestMatchers("/recruiters/**").hasAnyRole("RECRUITER", "COMPANY", "ADMIN")

                        // ================= REVIEW =================
                        .requestMatchers(HttpMethod.POST, "/reviews/**").hasAnyRole("APPLICANT", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/reviews/**").hasAnyRole("APPLICANT", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/reviews/**").hasAnyRole("APPLICANT", "ADMIN")

                        // ================= USER =================
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