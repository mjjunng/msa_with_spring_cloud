package com.example.userservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration  // 우선순위로 bean에 등록됨
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    // authorize
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/users/**").permitAll();  // 인증작업없이 쓸 수 있는 범위
        http.headers().frameOptions().disable();
    }
}
