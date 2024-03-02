package ca.myapp.config;

import ca.myapp.Security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@ComponentScan(basePackages = {"ca.myapp.utility", "ca.myapp.controllers", "ca.myapp.service"})
@SpringBootApplication
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
                .password("password")
                .roles("USER").build());
        return manager;
    }
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        System.out.println("asdas");
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeRequests(auth -> auth
//                        .requestMatchers("/login", "/register","/api/register","/api/login").permitAll()
//                        .anyRequest().authenticated())
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("asdas");
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(auth -> auth
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Expose AuthenticationManager as a Bean
    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }


    // ... (other beans like PasswordEncoder, UserDetailsService, etc.)
}


