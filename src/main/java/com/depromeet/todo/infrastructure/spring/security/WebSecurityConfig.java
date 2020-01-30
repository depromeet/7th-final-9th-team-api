package com.depromeet.todo.infrastructure.spring.security;

import com.depromeet.todo.application.member.LoginService;
import com.depromeet.todo.application.member.MemberService;
import com.depromeet.todo.application.security.TokenService;
import com.depromeet.todo.presentation.member.LoginResponseAssembler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@ConditionalOnWebApplication
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final ObjectMapper readObjectMapper;
    private final ObjectMapper writeObjectMapper;
    private final TokenService<Long> tokenService;
    private final LoginService loginService;
    private final MemberService memberService;
    private final LoginResponseAssembler loginResponseAssembler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/**")
                .authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/api/members/login").permitAll()
//                .mvcMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                .anyRequest().authenticated();

        http.formLogin().disable();
        http.httpBasic().disable();

        http.logout()
                .logoutSuccessHandler(this.jsonLogoutSuccessHandler())
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/members/logout", HttpMethod.POST.name()));

        http.addFilterAt(this.todoPreAuthenticatedProcessingFilter(), AbstractPreAuthenticatedProcessingFilter.class);

        http.addFilterBefore(this.httpPostAuthenticationProcessingFilter(), BasicAuthenticationFilter.class);

        http.exceptionHandling()
                .authenticationEntryPoint(this.jsonAuthenticationEntryPoint())
                .accessDeniedHandler(this.jsonAccessDeniedHandler());

        http.sessionManagement().disable();

        http.csrf().disable();

        http.cors();
    }

    @Bean
    public AbstractPreAuthenticatedProcessingFilter todoPreAuthenticatedProcessingFilter() {
        AbstractPreAuthenticatedProcessingFilter filter = new TodoPreAuthenticatedProcessingFilter();
        filter.setAuthenticationManager(new ProviderManager(Collections.singletonList(preAuthTokenAuthenticationProvider())));
        return filter;
    }

    @Bean
    public AbstractAuthenticationProcessingFilter httpPostAuthenticationProcessingFilter() {
        AbstractAuthenticationProcessingFilter filter = new HttpPostAuthenticationProcessingFilter(
                "/api/members/login",
                HttpMethod.POST,
                readObjectMapper
        );
        filter.setAuthenticationManager(new ProviderManager(Collections.singletonList(httpBodyAuthenticationProvider())));
        filter.setAuthenticationSuccessHandler(this.jsonAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(this.jsonAuthenticationFailureHandler());
        return filter;
    }

    @Bean
    public PreAuthTokenAuthenticationProvider preAuthTokenAuthenticationProvider() {
        return new PreAuthTokenAuthenticationProvider(tokenService);
    }

    @Bean
    public AuthenticationProvider httpBodyAuthenticationProvider() {
        return new HttpBodyAuthenticationProvider(
                this.todoUserDetailsService()
        );
    }

    @Bean
    public UserDetailsService todoUserDetailsService() {
        return new TodoUserDetailsService(loginService);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(Collections.singletonList(CorsConfiguration.ALL));
        configuration.setAllowedOrigins(Collections.singletonList(CorsConfiguration.ALL));
        configuration.setAllowedHeaders(Arrays.asList(
                "Accept",
                "Authorization",
                "Content-Type"
        ));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }

    private AuthenticationSuccessHandler jsonAuthenticationSuccessHandler() {
        return new JsonAuthenticationSuccessHandler(
                writeObjectMapper,
                tokenService,
                memberService,
                loginResponseAssembler
        );
    }

    private AuthenticationFailureHandler jsonAuthenticationFailureHandler() {
        return new JsonAuthenticationFailureHandler(writeObjectMapper);
    }

    private LogoutSuccessHandler jsonLogoutSuccessHandler() {
        return new JsonLogoutSuccessHandler();
    }

    private AuthenticationEntryPoint jsonAuthenticationEntryPoint() {
        return new JsonAuthenticationEntryPoint(writeObjectMapper);
    }

    private AccessDeniedHandler jsonAccessDeniedHandler() {
        return new JsonAccessDeniedHandler(writeObjectMapper);
    }
}
