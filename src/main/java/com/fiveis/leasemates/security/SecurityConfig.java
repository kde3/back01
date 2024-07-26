package com.fiveis.leasemates.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrfConfig) -> csrfConfig.disable())
            .authorizeHttpRequests((authorize) ->
                    authorize
                            .requestMatchers("/user/join", "/user/login","/user/join_terms",
                                    "/community/", "/community/{postNo}",
                                    "/sharehouse/", "/sharehouse/{postNo}").permitAll()
                            .requestMatchers("/user/info/**").hasRole(Role.USER.getKey())
                            .requestMatchers("/admin/**").hasRole(Role.ADMIN.getKey())
                            .anyRequest().authenticated()
            )
            .formLogin((formLogin) ->
                    formLogin.loginPage("/user/login")
                            .usernameParameter("id")
                            .passwordParameter("password")
                            .loginProcessingUrl("/user/login")
                            .failureUrl("/user/login?error=true")
                            .defaultSuccessUrl("/community/", true)
            )
            .logout((logout) ->
                    logout.logoutUrl("/user/logout")
                            .logoutSuccessUrl("/community/")
            );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring().requestMatchers( "/img/**", "/css/**", "/js/**");
    }
}
