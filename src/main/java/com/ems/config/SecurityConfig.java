package com.ems.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.ems.model.Role.ERole.ADMIN;
import static com.ems.model.Role.ERole.USER;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    private static final String[] PERMIT_ALL_URL = {
            "api/rest/auth/login",
            "api/rest/auth/logout",
            "api/rest/auth/change_password",
            "api/rest/officialHolidays"
    };

    private static final String[] PERMIT_ADMIN_URL = {
            "api/rest/auth/register",
            "api/rest/officialHolidays/new",
            "api/rest/employees/**",
            "api/rest/employee_vacations/status/**",
            "api/rest/month_end_closing/new"

    };

    private static final String[] PERMIT_USER_URL = {
            "api/rest/employee_vacations/new",
            "api/rest/employee_vacations/update/**",
            "api/rest/employee_vacations/employee/**",
            "api/rest/workdays/**",
            "api/rest/month_end_closing/**"
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        requests -> requests
                                .requestMatchers(PERMIT_ALL_URL)
                                .permitAll()
                                .requestMatchers(PERMIT_ADMIN_URL).hasAuthority(ADMIN.name())
                                .requestMatchers(PERMIT_USER_URL).hasAuthority(USER.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
