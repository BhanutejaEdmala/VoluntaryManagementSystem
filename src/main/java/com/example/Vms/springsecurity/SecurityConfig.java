package com.example.Vms.springsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDataUserDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers(HttpMethod.GET,"/event/save").hasAnyAuthority("user","admin")
                        .requestMatchers(HttpMethod.POST,"/org/save").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PATCH,"/org/addevent").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PATCH,"/org/assignevent").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET,"/org/vieweventsinorg").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PATCH,"/org/sentmessage").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PATCH,"/org/groupmessage").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PATCH,"/vol/add").hasAuthority("user")
                        .requestMatchers(HttpMethod.GET,"/vol/totalorg").hasAuthority("user")
                        .requestMatchers(HttpMethod.GET,"/vol/vieworgbyloc").hasAuthority("user")
                        .requestMatchers(HttpMethod.GET,"/vol/viewvolunteersinevent").hasAuthority("user")
                        .requestMatchers(HttpMethod.GET,"/vol/viewmessages").hasAuthority("user")
                        .requestMatchers(HttpMethod.POST,"/user/add").permitAll()
                        );
        http.httpBasic(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
