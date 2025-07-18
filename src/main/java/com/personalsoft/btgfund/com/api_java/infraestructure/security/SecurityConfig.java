package com.personalsoft.btgfund.com.api_java.infraestructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personalsoft.btgfund.com.api_java.domain.model.UsersModel;
import com.personalsoft.btgfund.com.api_java.domain.ports.output.IUserAdapter;
import com.personalsoft.btgfund.com.api_java.infraestructure.commons.constants.EndPointApi;
import com.personalsoft.btgfund.com.api_java.infraestructure.security.jwt.auth.AuthenticationFilter;
import com.personalsoft.btgfund.com.api_java.infraestructure.security.jwt.auth.ValidationFilter;
import com.personalsoft.btgfund.com.api_java.infraestructure.security.jwt.handler.CustomAccessDeniedHandler;
import com.personalsoft.btgfund.com.api_java.infraestructure.security.jwt.handler.CustomAuthenticationEntryPoint;
import com.personalsoft.btgfund.com.api_java.infraestructure.security.model.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

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
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationFilter authenticationFilter(AuthenticationManager authenticationManager) {
        return new AuthenticationFilter(authenticationManager);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           ObjectMapper objectMapper,
                                           IUserAdapter userAdapter,
                                           AuthenticationManager authManager,
                                           ObjectProvider<AuthenticationFilter> authenticationFilterProvider) throws Exception {

        return http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(WHITE_LIST_URL).permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPoint))
                .addFilter(authenticationFilterProvider.getObject())
                .addFilter(new ValidationFilter(authManager, objectMapper, userAdapter))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
