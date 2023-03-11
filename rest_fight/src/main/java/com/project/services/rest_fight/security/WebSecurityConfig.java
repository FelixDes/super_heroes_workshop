package com.project.services.rest_fight.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and()
                .authorizeHttpRequests(auths -> auths
                        .requestMatchers(HttpMethod.GET, "/api/fights/randomfighters")
                        .hasRole("APP_USER")

                        .requestMatchers(HttpMethod.POST, "/api/fights/**")
                        .hasRole("APP_USER")

                        .requestMatchers(HttpMethod.GET, "/api/fights/**")
                        .hasRole("APP_ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/fights/ping")
                        .permitAll())
                .oauth2ResourceServer().jwt();
        return http.build();
    }
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwtToAuthorityConverter());
        return converter;
    }

    @Bean
    public Converter<Jwt, Collection<GrantedAuthority>> jwtToAuthorityConverter() {
        return new Converter<>() {
            @Override
            public List<GrantedAuthority> convert(Jwt jwt) {
                Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
                if (realmAccess != null) {
                    @SuppressWarnings("unchecked")
                    List<String> roles = (List<String>) realmAccess.get("roles");
                    if (roles != null) {
                        return roles.stream()
                                .map(rn -> new SimpleGrantedAuthority("ROLE_" + rn))
                                .collect(Collectors.toList());
                    }
                }

                return Collections.emptyList();
            }
        };
    }
}
