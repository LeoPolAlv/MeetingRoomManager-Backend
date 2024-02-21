package com.eviden.meetingroom.security.Authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class HttpSecurityConfig {

	@Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthorizationFilter authenticationFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http .csrf( csrfConfig -> csrfConfig.disable() ) //Al utilizar la seguridad mediante tokens, este filtro ya no lo usamos
             .sessionManagement( sessionMangConfig -> sessionMangConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS) ) //Sesiones sin estados
             .authenticationProvider(authenticationProvider)//Proveedor de autenticacion
              //el filtro del primer parametro se va a ejcutar antes del fitro del segundo parametro.
             .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
 //               .authorizeHttpRequests(builderRequestMatchers())
        ;

        return http.build();
    }
}
