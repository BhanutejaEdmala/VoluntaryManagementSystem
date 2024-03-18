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
                        .requestMatchers(HttpMethod.POST,"/event/saveevent").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/event/deleteevent").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PATCH,"/event/updatevent").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET,"/event/getevent").hasAnyAuthority("admin","user")
                        .requestMatchers(HttpMethod.POST,"/org/saveorg").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PATCH,"/org/addevent").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PATCH,"/org/assignevent").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET,"/org/vieweventsinorg").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PATCH,"/org/sentmessage").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PATCH,"/org/groupmessage").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PATCH,"/org/suggest").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE,"/org/delete").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET,"/org/viewmessages").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE,"/org/removevolunteer").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET,"/org/getorg").hasAnyAuthority("admin","user")
                        .requestMatchers(HttpMethod.PATCH,"/org/closeeventfororg").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PATCH,"/vol/add").hasAuthority("user")
                        .requestMatchers(HttpMethod.GET,"/vol/totalorg").hasAuthority("user")
                        .requestMatchers(HttpMethod.GET,"/vol/vieworgbyloc").hasAuthority("user")
                        .requestMatchers(HttpMethod.GET,"/vol/viewvolunteersinevent").hasAnyAuthority("user","admin")
                        .requestMatchers(HttpMethod.GET,"/vol/viewmessages").hasAuthority("user")
                        .requestMatchers(HttpMethod.DELETE,"/vol/delete").hasAuthority("user")
                        .requestMatchers(HttpMethod.PATCH,"/vol/sentmessage").hasAuthority("user")
                        .requestMatchers(HttpMethod.GET,"/vol/search").hasAuthority("user")
                        .requestMatchers(HttpMethod.GET,"/vol/getorgbyaddress").hasAuthority("user")
                        .requestMatchers(HttpMethod.GET,"/vol/regevents").hasAuthority("user")
                        .requestMatchers(HttpMethod.PATCH,"/vol/completeevent").hasAuthority("user")
                        .requestMatchers(HttpMethod.GET,"/vol/getvolunteer").hasAuthority("user")
                        .requestMatchers(HttpMethod.POST,"/user/add").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/user/delete").hasAuthority("user")
                        .requestMatchers(HttpMethod.PATCH,"/user/update").hasAuthority("user")
                        .requestMatchers(HttpMethod.GET,"/user/regorganisations").hasAuthority("user")
                        .requestMatchers(HttpMethod.DELETE,"/user/leaveorganisation").hasAuthority("user")
                        .requestMatchers(HttpMethod.GET,"/user/viewcertificates").hasAuthority("user")
                        .requestMatchers(HttpMethod.GET,"/user/getuser").hasAuthority("user")
                        .requestMatchers(HttpMethod.PATCH,"/user/leaveevent").hasAuthority("user")
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
