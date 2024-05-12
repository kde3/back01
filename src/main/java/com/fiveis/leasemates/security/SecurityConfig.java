package com.fiveis.leasemates.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
                            .requestMatchers("/community/UpdateCmt/**", "/user/info/**").hasRole(Role.USER.getKey())
                            .requestMatchers("/admin/**").hasRole(Role.ADMIN.getKey())
                            .anyRequest().permitAll()
            )
            .formLogin((formLogin) ->
                    formLogin.loginPage("/user/login")
                            .usernameParameter("id")
                            .passwordParameter("password")
                            .loginProcessingUrl("/user/login")
                            .defaultSuccessUrl("/community/")
            )
            .logout((logout) ->
                    logout.logoutUrl("/logout")
                            .logoutSuccessUrl("/community/")
            );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
