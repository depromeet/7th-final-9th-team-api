package com.depromeet.todo.infrastructure.spring.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@ConditionalOnWebApplication
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final ObjectMapper readObjectMapper;
    private final ObjectMapper writeObjectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/**")
                .authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/api/members/login").permitAll()
                .anyRequest().authenticated();

        http.logout()
                .logoutSuccessHandler(this.jsonLogoutSuccessHandler())
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/members/logout", HttpMethod.POST.name()));

        http.addFilterBefore(this.httpPostAuthenticationProcessingFilter(), BasicAuthenticationFilter.class);

        http.exceptionHandling()
                .authenticationEntryPoint(this.jsonAuthenticationEntryPoint())
                .accessDeniedHandler(this.jsonAccessDeniedHandler());

        // TODO: token 발급 기능 추가되면 session 비활성화시켜야함
//        http.sessionManagement().disable();

        http.csrf().disable();
    }

    @Bean
    public AbstractAuthenticationProcessingFilter httpPostAuthenticationProcessingFilter() {
        AbstractAuthenticationProcessingFilter filter = new HttpPostAuthenticationProcessingFilter(
                "/api/members/login",
                HttpMethod.POST,
                readObjectMapper
        );
        filter.setAuthenticationManager(
                this.httpBodyAuthenticationProvider()::authenticate
        );
        filter.setAuthenticationSuccessHandler(this.jsonAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(this.jsonAuthenticationFailureHandler());
        return filter;
    }

    @Bean
    public AuthenticationProvider httpBodyAuthenticationProvider() {
        return new HttpBodyAuthenticationProvider(
                this.todoUserDetailsService()
        );
    }

    @Bean
    public UserDetailsService todoUserDetailsService() {
        // TODO: loginService 머지되면 수정
        return new TodoUserDetailsService();
    }

    @Bean
    public AuthenticationSuccessHandler jsonAuthenticationSuccessHandler() {
        return new JsonAuthenticationSuccessHandler(writeObjectMapper);
    }

    @Bean
    public AuthenticationFailureHandler jsonAuthenticationFailureHandler() {
        return new JsonAuthenticationFailureHandler(writeObjectMapper);
    }

    @Bean
    public LogoutSuccessHandler jsonLogoutSuccessHandler() {
        return new JsonLogoutSuccessHandler(writeObjectMapper);
    }

    @Bean
    public AuthenticationEntryPoint jsonAuthenticationEntryPoint() {
        return new JsonAuthenticationEntryPoint(writeObjectMapper);
    }

    @Bean
    public AccessDeniedHandler jsonAccessDeniedHandler() {
        return new JsonAccessDeniedHandler(writeObjectMapper);
    }
}
