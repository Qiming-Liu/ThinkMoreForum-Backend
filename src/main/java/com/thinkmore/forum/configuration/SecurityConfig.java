package com.thinkmore.forum.configuration;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import com.thinkmore.forum.filter.JwtGenerateFilter;
import com.thinkmore.forum.filter.JwtCheckFilter;
import com.thinkmore.forum.service.JwtRouterService;
import com.thinkmore.forum.service.UsersService;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UsersService usersService;
    private final JwtRouterService jwtRouterService;

    public final static SecretKey secretKey = Keys.hmacShaKeyFor(StaticConfig.JwtSecretKey.getBytes(StandardCharsets.UTF_8));

    @SneakyThrows
    protected void configure(HttpSecurity http) {
        http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtGenerateFilter(usersService, jwtRouterService, authenticationManager()))
                .addFilterAfter(new JwtCheckFilter(jwtRouterService), JwtGenerateFilter.class)
                .authorizeRequests()
                .antMatchers(StaticConfig.ignoreUrl).permitAll()
                .anyRequest().authenticated();
    }

    @Override
    @SneakyThrows
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(usersService);
        return provider;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }
}
