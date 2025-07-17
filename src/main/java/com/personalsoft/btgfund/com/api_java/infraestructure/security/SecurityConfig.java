package com.personalsoft.btgfund.com.api_java.infraestructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personalsoft.btgfund.com.api_java.domain.ports.output.IUserAdapter;
import com.personalsoft.btgfund.com.api_java.infraestructure.commons.constants.EndPointApi;
import com.personalsoft.btgfund.com.api_java.infraestructure.security.jwt.auth.AuthenticationFilter;
import com.personalsoft.btgfund.com.api_java.infraestructure.security.jwt.auth.ValidationFilter;
import com.personalsoft.btgfund.com.api_java.infraestructure.security.jwt.handler.CustomAccessDeniedHandler;
import com.personalsoft.btgfund.com.api_java.infraestructure.security.jwt.handler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;


    private static final String[] WHITE_LIST_URL = {
            EndPointApi.LOGIN,
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/configuration/**",
            EndPointApi.BASE_URL + "/**"
    };


    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, ObjectMapper objectMapper, IUserAdapter userAdapter) throws Exception {

        return http.authorizeHttpRequests((authz) -> authz
                        .requestMatchers(WHITE_LIST_URL).permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(ex -> ex.accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPoint))
                .addFilter(new AuthenticationFilter(authenticationManager()))
                .addFilter(new ValidationFilter(authenticationManager(), objectMapper, userAdapter))
                .csrf(config -> config.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
