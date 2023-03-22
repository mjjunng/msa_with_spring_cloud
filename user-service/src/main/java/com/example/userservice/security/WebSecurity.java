package com.example.userservice.security;

import com.example.userservice.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.Filter;

/**
 * 사용자의 인증 요청 -> 인증 처리 custom filter로 감 -> 인증 처리 filter는 사용자가 입력한 정보를 바탕으로
 * 인증 메니저에게 전달 -> 인증 메니저가 인증 provider를 호출하여 인증 처리를 수행한다. -> 이때 provider는 UserDetailsService
 * 서비스를 통해 요청한 정보가 db에 있는지 확인하고, 비밀번호가 일치하는지 확인하여 인증 처리를 수행한다.
 * -> 인증이 완로되면, 인증 처리가 된 객체 반환
 */
@Configuration  // 우선순위로 bean에 등록됨
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Environment env;

    public WebSecurity(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, Environment env) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.env = env;
    }

    // authorize
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
//        http.authorizeRequests().antMatchers("/users/**").permitAll();  // 인증작업없이 쓸 수 있는 범위
        http.authorizeRequests().antMatchers("/**")
                        .hasIpAddress("127.0.0.1")
        .and().addFilter(getAuthenticationFilter());    // when request go filter
        http.headers().frameOptions().disable();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter =
                new AuthenticationFilter(authenticationManager(),
                        userService, env);
        return authenticationFilter;
    }

    // select pwd from users where email=?
    // db_pwd(encrypted) == input_pwd(encrypted)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
