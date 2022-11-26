package com.micro.webservice.restfulwebservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfigure {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpd) throws Exception {
        httpd.authorizeHttpRequests(auth->auth.anyRequest().authenticated());
        httpd.httpBasic(Customizer.withDefaults());
        httpd.csrf().disable();
        return httpd.build();
    }
}
