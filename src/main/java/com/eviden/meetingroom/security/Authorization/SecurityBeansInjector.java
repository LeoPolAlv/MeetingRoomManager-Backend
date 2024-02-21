package com.eviden.meetingroom.security.Authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//import com.eviden.meetingroom.security.repository.IUserRepository;
import com.eviden.meetingroom.security.service.UserAuthService;

@Configuration
public class SecurityBeansInjector {

/*	@Autowired
    private IUserRepository userRepository;
	
	@Autowired
	private UserAuthService userAuthService;
*/
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();//Se crea el ProviderManager implements AuthenticationManager
    }

    //AuthenticationProvider es un mecanismo para hacer Login.
    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    UserDetailsService userDetailsService() {
      return new UserAuthService();
    };

    /*@Bean
    UserDetailsService userDetailsService(){
    	System.out.println("Bean de userDetailsService");
    	//return new UserAuthService();
        return username -> {
            return userRepository.findByEmail(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado en el sistema"));
        };
    }*/
}
